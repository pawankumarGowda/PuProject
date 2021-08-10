package cargill.com.purina.dashboard.View.FeedProgram

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cargill.com.purina.Database.PurinaDataBase
import cargill.com.purina.R
import cargill.com.purina.Service.Network
import cargill.com.purina.Service.PurinaService
import cargill.com.purina.dashboard.Model.FeedingProgram.FeedProgramStages
import cargill.com.purina.dashboard.Model.FeedingProgram.FeedprogramRow
import cargill.com.purina.dashboard.Model.Products.Product
import cargill.com.purina.dashboard.Repository.FeedProgramRepository
import cargill.com.purina.dashboard.viewModel.FeedProgramViewModel
import cargill.com.purina.dashboard.viewModel.SharedViewModel
import cargill.com.purina.dashboard.viewModel.viewModelFactory.FeedProgramViewModelFactory
import cargill.com.purina.databinding.FragmentFeedingProgramsBinding
import cargill.com.purina.utils.AppPreference
import cargill.com.purina.utils.Constants

class FragmentFeedingPrograms : Fragment() {
  var binding:FragmentFeedingProgramsBinding? = null
  private val _binding get() = binding!!
  private var feedProgramViewModel: FeedProgramViewModel? = null
  private lateinit var adapter:FeedProgramStagesAdapter
  lateinit var myPreference: AppPreference
  private var dataLoaded:Boolean = false
  var sharedViewmodel: SharedViewModel? = null
  private var programId:String = ""
  private var programName:String = ""
  private var animalsInNumber:String = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentFeedingProgramsBinding.inflate(inflater, container, false)
    val view = binding!!.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if(arguments != null){
      if(requireArguments().containsKey(Constants.PROGRAM_ID)){
        programId = arguments?.getString(Constants.PROGRAM_ID).toString()
      }
      if(requireArguments().containsKey(Constants.NUMBER_ANIMALS)){
        animalsInNumber = arguments?.getString(Constants.NUMBER_ANIMALS).toString()
      }
      if(requireArguments().containsKey(Constants.PROGRAM_NAME)){
        programName = arguments?.getString(Constants.PROGRAM_NAME).toString()
      }
    }
    init()
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    myPreference = AppPreference(context)
  }
  private fun init(){
    val dao = PurinaDataBase.invoke(requireActivity().applicationContext).dao
    val repository = FeedProgramRepository(dao, PurinaService.getDevInstance(),requireActivity())
    val factory = FeedProgramViewModelFactory(repository)
    feedProgramViewModel = ViewModelProvider(this,factory).get(FeedProgramViewModel::class.java)
    binding!!.feedProgramViewModel = feedProgramViewModel
    binding!!.lifecycleOwner = this
    initRecyclerView()
  }
  private fun initRecyclerView(){
    _binding.feedProgramStageList.layoutManager = LinearLayoutManager(requireContext())
    adapter = FeedProgramStagesAdapter ({stage: FeedprogramRow ->onItemClick(stage)},{saveStage: FeedprogramRow ->saveData(saveStage)})
    _binding.feedProgramStageList.adapter = adapter
    getData()
  }

  private fun getData(){
    if(Network.isAvailable(requireActivity())){
      feedProgramViewModel!!.getFeedProgramStageDetails(programId.toInt())
    }else{
      feedProgramViewModel!!.getStageCacheData(programId.toInt())
    }
    observerResponse()
  }

  private fun observerResponse(){
    feedProgramViewModel!!.stageResponse.observe(_binding.lifecycleOwner!!, Observer {
      Log.i("getting data", it.toString())
      adapter.setList(FeedProgramStages(it as ArrayList<FeedprogramRow>, true, programName, emptyList(), animalsInNumber.toInt()))
      adapter.notifyDataSetChanged()
      _binding.FeedingProgramName.text = programName
      _binding.animalNumber.text = animalsInNumber.plus(" "+myPreference.getStringValue(Constants.USER_ANIMAL).toString())
    })
  }
  private fun onItemClick(program:FeedprogramRow){
    val bundle = bundleOf(Constants.FEED_PROGRAM_STAGE to program)
    findNavController().navigate(R.id.action_fragmentFeedingProgram_to_fragmentDetailFeedProgram, bundle)
  }
  private fun saveData(program:FeedprogramRow){
    feedProgramViewModel!!.updateFeedProgramStageUnits(animalsInNumber.toInt(),program)
  }
}