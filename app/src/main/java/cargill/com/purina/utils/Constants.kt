package cargill.com.purina.utils

import android.location.Location
import cargill.com.purina.R
import cargill.com.purina.dashboard.Model.Campaign.Campaigns
import cargill.com.purina.dashboard.Model.Home.FAQs
import cargill.com.purina.dashboard.Model.Home.OnBoardingItem
import cargill.com.purina.dashboard.Model.IdentifyDisease.Symptoms
import kotlin.properties.Delegates

class Constants {
  companion object {
    const val DEBUG_API_KEY = "AIzaSyCJhCfsZ1Hu2TgqUMeDrQKnfcpXounMCp0"
    //Development URL
    //const val DEV_BASE_URL = "https://apipurina.dev.dev-cglcloud.com"

    //New Hosting only for Dev
    //const val DEV_BASE_URL = "https://api-dev.dev.dev-cglcloud.com/api/purinarussia/purinaserver/"

    //Staging URL
    //const val DEV_BASE_URL = "https://apipurina.stage.cglcloud.in/"  //Production URL
    const val DEV_BASE_URL = "https://api-stage.stage.cglcloud.in/api/purinarussia/purinaserver/"   //New Staging Running from v1.1.3


    //Production URL
    //const val DEV_BASE_URL = "https://api.cglcloud.com/api/purinarussia/purinaserver/" //New Prod URl

    //True only in Production
    const val IS_PROD: Boolean = false

    var IS_PROD_Triggered: Boolean = false
    var IS_TERMS: Boolean = false
    const val USER_TERMS_ACCEPTED: String = "isAccepted"
    var TERMS_VALUE: String = ""

    const val USER_LANGUAGE_CODE: String = "my_language"
    const val USER_LANGUAGE: String = "my_lang"
    const val USER_ANIMAL: String = "animal_selected"
    const val USER_ANIMAL_CODE: String = "species_code"
    const val USER_ANIMAL_ORDER: String = "species_orderid"
    const val SEARCH_QUERY_TEXT = "seachQuery"
    const val SEARCH_QUERY = "search"
    const val SEARCH_TEXT = "text"
    const val LANGUAGE = "lang"
    const val LANGUAGE_CODE = "lang_code"
    const val SPECIES_ID = "species_id"
    const val SUBSPECIES_ID = "subspecies_id"
    const val CATEGORY_ID = "category_id"
    const val STAGE_ID = "stage_id"
    const val PAGE = "page"
    const val PER_PAGE = "per_page"
    const val PRODUCT_ID = "product_id"
    const val IMAGES = "images"
    const val PROGRAM_ID: String = "program_id"
    const val PROGRAM_NAME: String = "program_name"
    const val NUMBER_ANIMALS: String = "animals"
    const val FEED_PROGRAM_STAGE = "stage"
    const val MIME_TYPE_PDF = "application/pdf"

    /*Store Location Constants Starts here*/
    const val LOCATION_PERMISSION_REQ_CODE = 1000
    const val LOCATION_LOADED: String = "location"
    const val STORE_ID = "store_id"
    var STORE_TEXT = "text"
    var location: Location = Location("")
    var locationCity: String? = null
    const val DEFAULT_STORE_IMG = "/aws/download/Option_2_stage.png"
    const val IS_LOCATION_LANGUAGE_KEYBOARD = "language_keyboard"
    const val LOCATE_STORE_COUNT = 10.toString()
    var locationTemp: Location = Location("")
    /*Store Location Constants Ends here*/

    /*Identify Disease Constants Starts here*/
    const val DISEASE_ID = "id"
    var IS_DISEASESS = "disease_id"
    var DISEASES_ID = ""
    var DISEASES_IDS = ""
    const val SYMPTOMS_ID = "symptoms_id"
    /*Identify Disease Constants Ends here*/

    val OnBoardingListEnglish = arrayOf(
      OnBoardingItem(R.drawable.productcatalogue, "Product Catalog", "Know about the range of all Purina® animal feed available for each animal."),
      OnBoardingItem(R.drawable.locatestore, "Locate Store", "Purina® store information is now available at a click of a button!"),
      OnBoardingItem(R.drawable.foodcalculator, "Feed Program Calculator", "Discover the combinations of feed the animals will require in different phases of its life."),
      OnBoardingItem(R.drawable.diseases, "Identify Diseases", "Here's an encyclopedia plus digital vet of the common diseases that affect animals."),
      OnBoardingItem(R.drawable.rearinganimals, "Animal Rearing Information", "Get best practices of your precious animals at your fingertips"),
      OnBoardingItem(R.drawable.productcampaign, "Product Campaign", "Follow the latest discounts and new Purina® products here."),
      OnBoardingItem(R.drawable.bestexperience, "Practices for Best App Experience", "Enable GPS\n" +
              "\n" +
              "Offline mode is supported, but try to be in an area with good internet to get the best experience.\n"+
              "\n" +
              "Please ensure that operating mobile language = location and language selected in app.")
    )

    val OnBoardingListRussian = arrayOf(
      OnBoardingItem(R.drawable.productcatalogue, "Каталог продуктов", "Каталог продуктов: ознакомьтесь с ассортиментом кормов Purina®, доступных для каждого вида с/х животных и птицы.  "),
      OnBoardingItem(R.drawable.locatestore, "Найти магазин", "Информация о магазинах Purina® теперь доступна одним нажатием кнопки!"),
      OnBoardingItem(R.drawable.foodcalculator, "Калькулятор программы кормления", "Калькулятор программы кормления: Узнайте, какие корма Purina® потребуются животным в течение разных фаз выращивания."),
      OnBoardingItem(R.drawable.diseases, "Определить болезнь", "Здесь вы найдете энциклопедию наиболее распространенных болезней, от которых страдают животные."),
      OnBoardingItem(R.drawable.rearinganimals, "Выращиваем правильно", "Лучший мировой опыт в кормлении и выращивании животных - в вашем смартфоне."),
      OnBoardingItem(R.drawable.productcampaign, "Новинки и акции", "Узнавайте первыми о новых продуктах Purina® и промо-акциях на нашу продукцию."),
      OnBoardingItem(R.drawable.bestexperience, "Как сделать работу в приложении еще удобнее?", "Включить GPS.\n" +
              "\n" +
              "Оффлайн-режим поддерживается, но для наилучшего пользовательского опыта рекомендуем работать с подключением к Интернет.\n" +
              "\n" +
              "Рабочий язык мобильного телефона = страна и язык в приложении."),
    )

    //Офлайн-режим поддерживается. Для наилучшего восприятия всегда включайте Интернет. (Offline Mode Message)

    val txtNext = "Далее"
    val txtSkip = "Пропустить"
    val txtGetStarted = "Начать"

    //Settings PDF FILE STARTS HERE
    val txtEnglighPDF = "Purina_Help_Manual.pdf"
    val txtRussianPDF = "help_manual_mobile_russian.pdf"
    val txtMagyarPDF = ""
    val txtPolskiePDF = ""
    val txtItalianaPDF = ""
    val txtRomanaPDF = ""
    //Settings PDF FILE ENDS HERE

    //Campaigns
    lateinit var campaignsObject: Campaigns
    //Campaigns

  }
}