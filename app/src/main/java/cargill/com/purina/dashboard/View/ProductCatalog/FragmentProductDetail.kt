package cargill.com.purina.dashboard.View.ProductCatalog

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cargill.com.purina.Database.PurinaDataBase
import cargill.com.purina.R
import cargill.com.purina.Service.PurinaService
import cargill.com.purina.dashboard.Model.ProductDetails.ProductDetail
import cargill.com.purina.dashboard.Repository.ProductCatalogueRepository
import cargill.com.purina.dashboard.viewModel.ProductCatalogueViewModel
import cargill.com.purina.dashboard.viewModel.viewModelFactory.ProductCatalogueViewModelFactory
import cargill.com.purina.databinding.FragmentDetailCatalogueBinding
import cargill.com.purina.utils.Constants
import cargill.com.purina.utils.PermissionCheck
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import java.io.File

class FragmentProductDetail : Fragment() {
    var binding: FragmentDetailCatalogueBinding? = null
    private lateinit var productDetailCatalogueViewModel: ProductCatalogueViewModel
    private val _binding get() = binding!!
    private var product_id:Int = 0
    lateinit var product:ProductDetail
    var downloadId:Long = 0
    var file:File? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailCatalogueBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao = PurinaDataBase.invoke(requireActivity().applicationContext).dao
        val repository = ProductCatalogueRepository(dao, PurinaService.getDevInstance(),requireActivity())
        val factory = ProductCatalogueViewModelFactory(repository)

        productDetailCatalogueViewModel = ViewModelProvider(this, factory).get(ProductCatalogueViewModel::class.java)
        binding?.catalogueDetailViewModel = productDetailCatalogueViewModel
        binding?.lifecycleOwner = this
        if(arguments != null){
            if(requireArguments().containsKey(Constants.PRODUCT_ID)){
                product_id = arguments?.getInt(Constants.PRODUCT_ID)!!
            }
        }
        Environment.getExternalStorageDirectory()
        product = productDetailCatalogueViewModel.getCacheProductDetail(7)
        if(product != null){
            file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                product.name
            )
            loadData(product)
        }else{
            Snackbar.make(_binding.root,R.string.no_data_found, Snackbar.LENGTH_LONG).show()
        }
        _binding.productPdf.setOnClickListener {
            if(PermissionCheck.readAndWriteExternalStorage(requireContext())){
                if(!file!!.exists()){
                    productDetailCatalogueViewModel.getProductPDF(product.pdf_link)
                    productDetailCatalogueViewModel.pathWithToken.observe(_binding.lifecycleOwner!!, Observer {
                        Log.i("path", it.body().toString())

                        var request = DownloadManager.Request(
                            Uri.parse(it.body().toString())
                        ).setTitle(product.name)
                            .setDescription(product.recipe_code)
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, product.name)
                            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverMetered(true)
                            .setMimeType("application/pdf")
                        downloadId = (requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
                    })
                }else{
                    Log.i("file Path", file!!.absolutePath)
                    launchPDF()
                }
            }else{
                //Take action if user did not provide permission
            }
        }
    }
    val br= object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            var id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if(id == downloadId){
                launchPDF()
                Snackbar.make(_binding.root,product.name + ".pdf"+ "!!!!Downloaded", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().unregisterReceiver(br)
        binding = null
    }
    fun launchPDF(){
        val uri:Uri = FileProvider.getUriForFile(requireContext(),"cargill.com.purina"+".provider",file!!)
        val i:Intent = Intent(Intent.ACTION_VIEW)
        i.setDataAndType(uri, "application/pdf")
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivity(i)
    }
    private fun loadData(product: ProductDetail){
        _binding.imageViewPager?.adapter = ImageViewPagerAdapter(product.images)
        _binding.imageTabLayout?.let {
            _binding.imageViewPager?.let { it1 ->
                TabLayoutMediator(it, it1){ tab, position->

                }.attach()
            }
        }
        _binding.catalogueName.text = product.name
        _binding.recipeCode.text = getString(R.string.recipe_code).plus( product.recipe_code)
        _binding.expandableDescription.text = product.product_details
        _binding.expandDescription.setOnClickListener {
            if(_binding.expandableDescription.maxLines > 3){
                _binding.expandDescription.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_down))
                _binding.expandableDescription.maxLines = 3
            }else{
                _binding.expandDescription.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_up))
                _binding.expandableDescription.maxLines = Int.MAX_VALUE
            }
        }
        _binding.expandableBenefits.text = Html.fromHtml(product.benefits)
        _binding.expandBenefits?.setOnClickListener {
            if(_binding.expandableBenefits.maxLines > 3){
                _binding.expandBenefits!!.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_down))
                _binding.expandableBenefits.maxLines = 3
            }else{
                _binding.expandBenefits!!.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_up))
                _binding.expandableBenefits.maxLines = Int.MAX_VALUE
            }
        }
        _binding.expandableIngredients.text = product.ingredients
        _binding.expandIngredients?.setOnClickListener {
            if(_binding.expandableIngredients.maxLines > 3){
                _binding.expandIngredients!!.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_down))
                _binding.expandableIngredients.maxLines = 3
            }else{
                _binding.expandIngredients!!.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_up))
                _binding.expandableIngredients.maxLines = Int.MAX_VALUE
            }
        }

        _binding.expandableMixingInstructions?.text = product.mixing_instructions
        _binding.expandMixingInstructions?.setOnClickListener {
            if(_binding.expandableMixingInstructions?.maxLines!! > 3){
                _binding.expandMixingInstructions!!.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_down))
                _binding.expandableMixingInstructions?.maxLines = 3
            }else{
                _binding.expandMixingInstructions!!.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_up))
                _binding.expandableMixingInstructions?.maxLines = Int.MAX_VALUE
            }
        }

        _binding.expandableFeedingInstructions?.text = product.feeding_instructions
        _binding.expandFeedingInstructions?.setOnClickListener {
            if(_binding.expandableFeedingInstructions?.maxLines!! > 3){
                _binding.expandFeedingInstructions!!.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_down))
                _binding.expandableFeedingInstructions?.maxLines = 3
            }else{
                _binding.expandFeedingInstructions!!.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_up))
                _binding.expandableFeedingInstructions?.maxLines = Int.MAX_VALUE
            }
        }
        _binding.expandableNutritionalData?.text = product.nutritional_data
        _binding.expandNutritionalData?.setOnClickListener {
            if(_binding.expandableNutritionalData?.maxLines!! > 3){
                _binding.expandNutritionalData!!.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_down))
                _binding.expandableNutritionalData?.maxLines = 3
            }else{
                _binding.expandNutritionalData!!.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_up))
                _binding.expandableNutritionalData?.maxLines = Int.MAX_VALUE
            }
        }
        _binding.expandableRecommendation?.text = product.recommendation_for_slaughter
        _binding.expandRecommendation?.setOnClickListener {
            if(_binding.expandableRecommendation?.maxLines!! > 3){
                _binding.expandRecommendation!!.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_down))
                _binding.expandableRecommendation?.maxLines = 3
            }else{
                _binding.expandRecommendation!!.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(),
                        R.drawable.ic_arrow_up))
                _binding.expandableRecommendation?.maxLines = Int.MAX_VALUE
            }
        }
        binding?.knowmoreData?.text = product.form
    }
}