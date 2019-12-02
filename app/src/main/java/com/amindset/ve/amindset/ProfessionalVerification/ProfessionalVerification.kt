package com.amindset.ve.amindset.ProfessionalVerification

import android.Manifest
import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.OTP.ModelLang.DatumLang
import com.amindset.ve.amindset.OTP.ModelLang.ModelLangList
import com.amindset.ve.amindset.ProfessionalVerification.ModelCountryList.Datum
import com.amindset.ve.amindset.ProfessionalVerification.ModelCountryList.ModelCountryList
import com.amindset.ve.amindset.ProfessionalVerification.ModelState.ModelStateList
import com.amindset.ve.amindset.ProfessionalVerification.ModelState.State
import com.amindset.ve.amindset.ProfessionalVerification.ModelUpdateDocument.ModelProvideDocumentUpload
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Signin.SignIn
import com.amindset.ve.amindset.Utils.CommonDialog
import com.amindset.ve.amindset.Utils.CommonUtils
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
import com.amindset.ve.amindset.databinding.ActivityProfessionalVerificationBinding
import com.google.android.gms.common.internal.Constants
import com.squareup.picasso.Picasso
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.text.SimpleDateFormat
import java.util.*

class ProfessionalVerification : BaseActivity(), View.OnClickListener , AdapterView.OnItemSelectedListener{

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        showMessage(parent!!.getItemAtPosition(p2).toString());
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.rl_origianl_diploma -> {
                selectImage(3)
            }

            R.id.rl_profrssional_license -> {
                selectImage(2)
            }
            R.id.rl_photo_identification -> {
                selectImage(1)
            }
            R.id.tv_alreadyy_signin -> {

                val intent = Intent(this@ProfessionalVerification, SignIn::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("type","provider")
                startActivity(intent)
            }

            R.id.ic_back_arrow -> {
                finish()
            }
            R.id.tv_upload_resume -> {
                UploadDocumentFromGallery(111);
            }

            R.id.rl_w8ben -> {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("W8-Ben")
                builder.setMessage("Do you have W8-Ben form in your device?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id -> dialog.cancel()
                        UploadDocumentFromGallery(112);}
                    .setNegativeButton("No") {

                            dialog, id -> dialog.cancel()
                        val browserIntent : Intent =  Intent(Intent.ACTION_VIEW, Uri.parse("https://www.irs.gov/pub/irs-pdf/fw8ben.pdf"));
                        startActivity(browserIntent);
                    }
                val alert = builder.create()
                alert.show()

//
            }
            R.id.rl_w9 -> {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("W9 form")
                builder.setMessage("Do you have W9 form in your device?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id -> dialog.cancel()
                        UploadDocumentFromGallery(113);}
                    .setNegativeButton("No") {

                            dialog, id -> dialog.cancel()
                        val browserIntent : Intent =  Intent(Intent.ACTION_VIEW, Uri.parse("https://www.irs.gov/pub/irs-pdf/fw9.pdf"));
                        startActivity(browserIntent);


//                        val browserIntent = Intent(Intent.ACTION_VIEW)
//                        browserIntent.setDataAndType(Uri.parse("https://www.irs.gov/pub/irs-pdf/fw9.pdf"),"application/pdf")
//                        val chooser = Intent.createChooser(browserIntent, getString(R.string.app_name))
//                        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // optional
//                        startActivity(chooser)

                    }
                val alert = builder.create()
                alert.show()



            }
            R.id.next -> {

                if (!TextUtils.isEmpty(bindingProfessionalVerification!!.etLegalName.text.toString()) &&
                    !TextUtils.isEmpty(bindingProfessionalVerification!!.etIdentification.text.toString()) &&
                    !TextUtils.isEmpty(countryId) &&
                    !TextUtils.isEmpty(stateId) &&
                    !TextUtils.isEmpty(bindingProfessionalVerification!!.tvSubLanguage.text.toString()) ) {

                    if(integerFileHashMap !=null && integerFileHashMap.size>=3)
                    {
                        if(::resumeFile.isInitialized )
                        {

                                APIforUploadDocument();


                        }
                        else{
                            showSnackBar("Please select resume to upload")
                        }
                    }

                    else
                    {
                        showSnackBar("Please select all document image to upload")
                    }

                }

                else
                {
                    showSnackBar(getString(R.string.msg_fill_login_data))
                }
            }
        }
    }


    private fun APIforUploadDocument() {
        val image1Body = ArrayList<MultipartBody.Part>()
        val image2Body = ArrayList<MultipartBody.Part>()
        val image3Body = ArrayList<MultipartBody.Part>()
        val resumeBody = ArrayList<MultipartBody.Part>()
        val w8w9Body = ArrayList<MultipartBody.Part>()
        val w9Body = ArrayList<MultipartBody.Part>()

        showLoading()
        val apiService = ApiClient.getClient().create(WebService::class.java)


        if (integerFileHashMap.size > 0) {

            for (i in 0..integerFileHashMap.size-1) {


                when (i) {
                    0 -> {
                        val requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), integerFileHashMap.get(i))
                        val body = MultipartBody.Part.createFormData(
                            "doc_img1",
                            integerFileHashMap.get(i)!!.getName(),
                            requestFile
                        )
                        image1Body.add(body)
                    }

                    1 -> {
                        val requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), integerFileHashMap.get(i))
                        val body = MultipartBody.Part.createFormData(
                            "doc_img2",
                            integerFileHashMap.get(i)!!.getName(),
                            requestFile
                        )
                        image2Body.add(body)
                    }

                    2 -> {
                        val requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), integerFileHashMap.get(i))
                        val body = MultipartBody.Part.createFormData(
                            "doc_img3",
                            integerFileHashMap.get(i)!!.getName(),
                            requestFile
                        )
                        image3Body.add(body)
                    }
                }

            }

        }

        val requestFileResume = RequestBody.create(MediaType.parse("application/pdf"), resumeFile.absolutePath)
        val bodyResume = MultipartBody.Part.createFormData("resume_doc", resumeFile!!.getName(), requestFileResume)
        resumeBody.add(bodyResume)

        val requestFileW8W9Form = RequestBody.create(MediaType.parse("application/pdf"), w8File.absolutePath)
        val bodyW8W9Form = MultipartBody.Part.createFormData("w8w9_form", w8File!!.name, requestFileW8W9Form)
        w8w9Body.add(bodyW8W9Form)

        val requestFileW9Form = RequestBody.create(MediaType.parse("application/pdf"), w9File.absolutePath)
        val bodyW9Form = MultipartBody.Part.createFormData("w9_form", w9File!!.name, requestFileW9Form)
        w9Body.add(bodyW9Form)

        val doc_id1 = RequestBody.create(MediaType.parse("text/plain"), bindingProfessionalVerification!!.etIdentification.text.toString())
        val doc_name1 = RequestBody.create(MediaType.parse("text/plain"), bindingProfessionalVerification!!.etLegalName.text.toString())
        val doc_type1 = RequestBody.create(MediaType.parse("text/plain"), "1")
        val doc_id2 = RequestBody.create(MediaType.parse("text/plain"), "anonymus")
        val doc_name2 = RequestBody.create(MediaType.parse("text/plain"), "anonymus")
        val doc_type2 = RequestBody.create(MediaType.parse("text/plain"), "2")
        val doc_type3 = RequestBody.create(MediaType.parse("text/plain"), "3")
        val doc_type4 = RequestBody.create(MediaType.parse("text/plain"), "4")
        val doc_type5 = RequestBody.create(MediaType.parse("text/plain"), "6")
        val doc_type6 = RequestBody.create(MediaType.parse("text/plain"), "3")
        val doc_countryId = RequestBody.create(MediaType.parse("text/plain"), countryId)
        val doc_stateid = RequestBody.create(MediaType.parse("text/plain"), stateId)
        val lang = RequestBody.create(MediaType.parse("text/plain"), bindingProfessionalVerification!!.tvSubLanguage.text.toString())



        apiService.postDriverDocument(doc_countryId,doc_stateid,
            image1Body, image2Body, image3Body, resumeBody, w8w9Body,
            doc_id1, doc_name1, doc_type1, doc_id2, doc_name2, doc_type2, doc_type3
            , doc_type4, doc_type5, doc_type6,lang, "Bearer " + pref!!.providerToken,w9Body
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ModelProvideDocumentUpload> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(updateAvtar: ModelProvideDocumentUpload) {


                    if(updateAvtar.status.equals(Constant.RESPONSE_SUCCESSFULLY)) {
                        image1Body.clear()
                        image2Body.clear()
                        image3Body.clear()
                        resumeBody.clear()
                        w8w9Body.clear()
                        hideLoading()
                        showMessage(updateAvtar.message)
                        hideLoading()


                        val builder = AlertDialog.Builder(this@ProfessionalVerification)
                        builder.setTitle("Documents Submitted")
                        builder.setMessage("Please give us 5-15 days to verified your information. ")
                            .setCancelable(false)
                            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                                dialog.dismiss()

                                if (intent.getStringExtra("type") != null) {
                                    val intent = Intent(this@ProfessionalVerification, SignIn::class.java)
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("type", "provider")
                                    startActivity(intent)
                                finish()
                            }
                                else
                                {
                                    finish()
                                }

                            })

                        val alert = builder.create()
                        alert.show()

                    }

                    else{
                        showMessage(updateAvtar.message)
                    }
                  }

                override fun onError(e: Throwable) {
                    image1Body.clear()
                    image2Body.clear()
                    image3Body.clear()
                    resumeBody.clear()
                    w8w9Body.clear()
                    hideLoading()
                    handleError(e)
                }
            })

    }

    private fun handleError(t: Throwable?) {
        if (t != null)
            showSnackBar(t.message!!)
    }

    private fun UploadDocumentFromGallery(i: Int) {


        val mimeTypes = arrayOf<String>(
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
            "application/vnd.ms-powerpoint",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
            "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
            "text/plain",
            "application/pdf",
            "application/zip"
        )
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(if (mimeTypes.size == 1) mimeTypes[0] else "*/*")
            if (mimeTypes.size > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }
        } else {
            var mimeTypesStr = ""
            for (mimeType in mimeTypes) {
                mimeTypesStr += mimeType + "|"
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length - 1))
        }
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), i)
    }





    private val integerFileHashMap = HashMap<Int, File>()
    private val integerURIHashMap = HashMap<Int, Uri>()
    private val captureImageUri: Uri? = null
    internal var photoFile: File? = null
    internal var photoURI: Uri? = null
    var filepath: String = ""

    lateinit var w8File: File
    lateinit var w9File: File

    var pref: AppPreferencesHelper? = null
    lateinit var resumeFile: File
     var stateId: String?=null
     var countryId: String?=null
//    lateinit var iv_photo_for_passport_driving: ImageView
//    lateinit var iv_photo_for_certification: ImageView
//    lateinit var iv_photo_for_education: ImageView
//
//    lateinit var ic_add_education_identification: ImageView
//    lateinit var ic_add_photo_identification: ImageView
//    lateinit var ic_add_certification_identification: ImageView
//
//
//    lateinit var et_certification_title_name: Spinner
//    lateinit var et_certification_identification: Spinner
//    lateinit var tv_upload_w8form: TextView
//
//    lateinit var et_legal_name: EditText
//    lateinit var et_identification: EditText
    var infoList = ArrayList<Datum>()
    var infoListLang = ArrayList<DatumLang>()
    var infoListState = ArrayList<State>()
//    var infoStr = ArrayList<String>()


    lateinit var back: ImageView


    var bindingProfessionalVerification: ActivityProfessionalVerificationBinding? = null

    var viewModelProfessionalVerification: ViewModelProfessionalVerification? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pref = AppPreferencesHelper(this)

        bindingProfessionalVerification =
            DataBindingUtil.setContentView(this, R.layout.activity_professional_verification)

        viewModelProfessionalVerification =
            ViewModelProviders.of(this).get(ViewModelProfessionalVerification::class.java)


        back = findViewById(R.id.ic_back_arrow);

        bindingProfessionalVerification!!.rlPhotoIdentification.setOnClickListener(this)
        bindingProfessionalVerification!!.rlOrigianlDiploma.setOnClickListener(this)
        bindingProfessionalVerification!!.rlProfrssionalLicense.setOnClickListener(this)
        bindingProfessionalVerification!!.next.setOnClickListener(this)
        bindingProfessionalVerification!!.rlW9.setOnClickListener(this)
        bindingProfessionalVerification!!.rlW8ben.setOnClickListener(this)



        back.setOnClickListener(this)

        bindingProfessionalVerification!!.icAddPhotoIdentification.setOnClickListener(this)
        bindingProfessionalVerification!!.icAddCertificationIdentification.setOnClickListener(this)
        bindingProfessionalVerification!!.icAddEducationIdentification.setOnClickListener(this)
        bindingProfessionalVerification!!.tvUploadResume.setOnClickListener(this)
        bindingProfessionalVerification!!.tvUploadW8form.setOnClickListener(this)

        getCountryList();

        getLanguageList();


        bindingProfessionalVerification!!.etCertificationTitleName.setOnItemSelectedListener(object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent:AdapterView<*>, view:View, position:Int, id:Long) {

                bindingProfessionalVerification!!.country.setText("Country/Region " + infoList[position].name)
//                    showMessage(""+infoList[position].name +infoList[position].id)
                countryId = infoList[position].id
                getStateList(infoList[position].id);
            } // to close the onItemSelected
            override fun onNothingSelected(parent:AdapterView<*>) {
            }
        })


        bindingProfessionalVerification!!.languageList.setOnItemSelectedListener(object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent:AdapterView<*>, view:View, position:Int, id:Long) {


                bindingProfessionalVerification!!.tvSubLanguage.setText(""+bindingProfessionalVerification!!.tvSubLanguage.text + " " + infoListLang[position].lanuage)
//                countryId = infoList[position].id
//                getStateList(infoList[position].id);
            }
            override fun onNothingSelected(parent:AdapterView<*>) {
            }
        })


        bindingProfessionalVerification!!.etCertificationIdentification.setOnItemSelectedListener(object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent:AdapterView<*>, view:View, position:Int, id:Long) {

                bindingProfessionalVerification!!.state.setText("State/Province " + infoListState[position].state)

                stateId = infoListState[position].id


            } // to close the onItemSelected
            override fun onNothingSelected(parent:AdapterView<*>) {
            }
        })




    }




    private fun getStateList(id :String) {

        var call : Call<ModelStateList>? = null


        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["Country_id"] = id

        call = apiService.getStateList(paramObject , "Bearer " + pref!!.providerToken)
        showLoading()
        call.enqueue(object : retrofit2.Callback<ModelStateList> {

            override fun onResponse(call: Call<ModelStateList>, response: Response<ModelStateList>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        infoListState.clear()
                        if(response.body().status.equals(Constant.RESPONSE_SUCCESSFULLY) && response.body().state.size>0) {
                            var info : State? = State()
                            info!!.id = ""
                            info!!.state = "Please select "
                            infoListState.add(info)

                            for(i in 0..response.body().state.size - 1) {
                                var info : State? = State()
                                info!!.id = response.body().state[i].id
                                info!!.state = response.body().state[i].state
                                infoListState.add(info)
                            }
                            val dataAdapter = ArrayAdapter<State>(this@ProfessionalVerification, android.R.layout.simple_spinner_item, infoListState)
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            bindingProfessionalVerification!!.etCertificationIdentification.setAdapter(dataAdapter)
                            hideLoading()

                        }
                        else
                        {
                            hideLoading()
                        }
                    }else {
                        hideLoading()
                    }
                }
            }



            override fun onFailure(call: Call<ModelStateList>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }


    private fun getLanguageList() {

        var call : Call<ModelLangList>? = null


        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()


        call = apiService.getLangList(paramObject , "Bearer " + pref!!.providerToken)


        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelLangList> {

            override fun onResponse(call: Call<ModelLangList>, response: Response<ModelLangList>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        if(response.body().status.equals(Constant.RESPONSE_SUCCESSFULLY)) {
                            showMessage(response.body().message)


                            var info : DatumLang? = DatumLang()
                            info!!.id = "0"
                            info!!.lanuage = ""
                            infoListLang.add(info)
                            for(i in 0..response.body().data.size - 1) {
                                var info : DatumLang? = DatumLang()
                                info!!.id = response.body().data[i].id
                                info!!.lanuage = response.body().data[i].lanuage
                                infoListLang.add(info)
//                                    infoStr.add(response.body().data[i].name);
                            }

                            val dataAdapter = ArrayAdapter<DatumLang>(this@ProfessionalVerification, android.R.layout.simple_spinner_item, infoListLang)
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            bindingProfessionalVerification!!.languageList.setAdapter(dataAdapter)
                            hideLoading()


                        }
                        else
                        {
                            showMessage(response.body().message)
                            hideLoading()
                        }
                    }else {
                        hideLoading()
                    }
                }
            }



            override fun onFailure(call: Call<ModelLangList>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }


    private fun getCountryList() {

            var call : Call<ModelCountryList>? = null


            val apiService = ApiClient.getClient().create(WebService::class.java)

            val paramObject = HashMap<String, String>()


           call = apiService.getCountryList(paramObject , "Bearer " + pref!!.providerToken)


            showLoading()

            call.enqueue(object : retrofit2.Callback<ModelCountryList> {

                override fun onResponse(call: Call<ModelCountryList>, response: Response<ModelCountryList>?) {

                    if (response != null) {
                        if (response.isSuccessful)
                        {
                            if(response.body().status.equals(Constant.RESPONSE_SUCCESSFULLY)) {
                                showMessage(response.body().message)


                                var info : Datum? = Datum()
                                info!!.code = "0"
                                info!!.id = "0"
                                info!!.name = "Please select "
                                infoList.add(info)
                                for(i in 0..response.body().data.size - 1) {
                                    var info : Datum? = Datum()
                                    info!!.code = response.body().data[i].code
                                    info!!.id = response.body().data[i].id
                                    info!!.name = response.body().data[i].name
                                    infoList.add(info)
//                                    infoStr.add(response.body().data[i].name);
                                }

                                val dataAdapter = ArrayAdapter<Datum>(this@ProfessionalVerification, android.R.layout.simple_spinner_item, infoList)
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                bindingProfessionalVerification!!.etCertificationTitleName.setAdapter(dataAdapter)
                                hideLoading()


                            }
                            else
                            {
                                showMessage(response.body().message)
                                hideLoading()
                            }
                        }else {
                            hideLoading()
                        }
                    }
                }



                override fun onFailure(call: Call<ModelCountryList>, t: Throwable) {
                    // Log error here since request failed
                    hideLoading()


                }


            })
        }

    fun selectImage(imagePosition: Int) {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Library", "Cancel")
        val title = TextView(this)
        title.text = "Add Photo!"
        title.setBackgroundColor(Color.BLACK)
        title.setPadding(10, 15, 15, 10)
        title.gravity = Gravity.CENTER
        title.setTextColor(Color.WHITE)
        title.textSize = 22f


        val builder = AlertDialog.Builder(
            this
        )


        builder.setCustomTitle(title)

        // builder.setTitle("Add Photo!");
        builder.setItems(items) { dialog, item ->
            if (items[item] == "Take Photo") {


                if (ContextCompat.checkSelfPermission(
                        this!!,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this!!,
                            Manifest.permission.CAMERA
                        )
                    ) {

                    } else {
                        ActivityCompat.requestPermissions(
                            this!!,
                            arrayOf(Manifest.permission.CAMERA),
                            0
                        )
                    }

                } else {
                    captureProfilePhoto(imagePosition)

                }

            } else if (items[item] == "Choose from Library") {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    ) {

                    } else {
                        ActivityCompat.requestPermissions(
                            this!!,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            0
                        )
                    }

                }
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this!!,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {

                    } else {
                        ActivityCompat.requestPermissions(
                            this!!,
                            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            0
                        )
                    }

                } else {
                    browseProfilePhoto(imagePosition)


                }

            } else if (items[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }


    private fun browseProfilePhoto(imagePosition: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("imageNumber", imagePosition)
        startActivityForResult(Intent.createChooser(intent, "Select picture"), imagePosition)
    }


    private fun captureProfilePhoto(imagePosition: Int) {


        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(this!!.getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
            }
//            mCurrentPhotoPath = fileUri.toString()
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(
                    this!!,
                    "com.amindset.ve.amindset.provider",
                    photoFile!!
                )

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureIntent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                startActivityForResult(takePictureIntent, imagePosition + 3)
            }
        }
//                           val pictureIntent =  Intent(
//                              MediaStore.ACTION_IMAGE_CAPTURE
//                           );
//    if(pictureIntent.resolveActivity(activity!!.getPackageManager()) != null) {
//           startActivityForResult(pictureIntent,
//                              imagePosition);
    }


    @Throws(IOException::class)
    private fun createImageFile(part: String, ext: String): File {
        // Create an image file name
        var tempDir = Environment.getExternalStorageDirectory()
        tempDir = File(tempDir.absolutePath + "/.temp/")
        if (!tempDir.exists()) {
            tempDir.mkdirs()
        }
        return File.createTempFile(part, ext, tempDir)
    }

    fun getImageUri(inContext: Context?, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()

        //        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        //        inImage.setHeight(500);
        //        inImage.setWidth(500);

        //        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000,true);
        val path = MediaStore.Images.Media.insertImage(inContext!!.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }


//    {
//
//        activity!!.contentResolver.notifyChange(photoURI, null)
//        val cr = activity!!.contentResolver
//        val bitmap: Bitmap
//        try {
//            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, photoURI)
//            imageView.setImageBitmap(bitmap)
//
//            val uri = getImageUri(activity!!.applicationContext, bitmap)
//            val file = File(getRealPathFromURI(activity!!.applicationContext, uri))
//            integerFileHashMap[1] = file
//            imageView.setImageBitmap(bitmap)
//
//            HitServerToUpdateAvtar()
//
//
//        } catch (e: Exception) {
//            Toast.makeText(activity, "Failed to load", Toast.LENGTH_SHORT).show()
//        }
//
//    }


    fun grabImage(imageView: ImageView, pos: Int) {
        this!!.getContentResolver().notifyChange(photoURI, null)
        val cr = this!!.getContentResolver()
        val bitmap: Bitmap
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, photoURI)
            imageView.setImageBitmap(bitmap)

            val uri = getImageUri(this!!.getApplicationContext(), bitmap)
            val file = File(getRealPathFromURI(this!!.getApplicationContext(), uri))


            if (pos == 4) {
                integerFileHashMap[0] = file
                bindingProfessionalVerification!!.ivPhotoForPassportDriving.setImageBitmap(bitmap)
                bindingProfessionalVerification!!.icAddPhotoIdentification.visibility = View.GONE
            } else if (pos == 5) {
                integerFileHashMap[1] = file
                bindingProfessionalVerification!!.ivPhotoForCertification.setImageBitmap(bitmap)
                bindingProfessionalVerification!!.icAddCertificationIdentification.visibility = View.GONE
            } else if (pos == 6) {
                integerFileHashMap[2] = file
                bindingProfessionalVerification!!.ivPhotoForEducation.setImageBitmap(bitmap)
                bindingProfessionalVerification!!.icAddEducationIdentification.visibility = View.GONE
            }
            //            HitServerToAddDocument(uri,file);


        } catch (e: Exception) {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show()
        }

    }


//    lateinit var resumeFile: File

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            4 -> if (resultCode == RESULT_OK) {
                grabImage(bindingProfessionalVerification!!.ivPhotoForPassportDriving, 4)

            }

            // for upload resume
            111 -> if (resultCode == RESULT_OK) {
                val uri = data!!.getData()
                val uriString = uri.toString()
                resumeFile = File(uriString)
                val path = resumeFile.getAbsolutePath()
                var displayName: String? = null
                if (uriString.startsWith("content://")) {
                    var cursor: Cursor? = null
                    try {
                        cursor = this.getContentResolver().query(uri, null, null, null, null)
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))

                        }
                    } finally {
                        cursor!!.close()
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = resumeFile.getName()

                }

                shoeFileName(displayName)
            }

             // w8-ben
            112 -> if (resultCode == RESULT_OK) {
                val uri = data!!.getData()
                val uriString = uri.toString()
                w8File = File(uriString)
                val path = w8File.getAbsolutePath()
                var displayName: String? = null
                if (uriString.startsWith("content://")) {
                    var cursor: Cursor? = null
                    try {
                        cursor = this.getContentResolver().query(uri, null, null, null, null)
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))

                        }
                    } finally {
                        cursor!!.close()
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = w8File.getName()

                }

                shoeFileName(displayName)
            }

//w9 form
            113 -> if (resultCode == RESULT_OK) {
                val uri = data!!.getData()
                val uriString = uri.toString()
                w9File = File(uriString)
                val path = w9File.getAbsolutePath()
                var displayName: String? = null
                if (uriString.startsWith("content://")) {
                    var cursor: Cursor? = null
                    try {
                        cursor = this.getContentResolver().query(uri, null, null, null, null)
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))

                        }
                    } finally {
                        cursor!!.close()
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = w9File.getName()

                }

                shoeFileName(displayName)
            }

            5 -> if (resultCode == RESULT_OK) {
                grabImage(bindingProfessionalVerification!!.ivPhotoForCertification, 5)

            }

            6 -> if (resultCode == RESULT_OK) {
                grabImage(bindingProfessionalVerification!!.ivPhotoForEducation, 6)

            }
            1 -> if (resultCode == RESULT_OK && data != null && data.data != null) {
                handleGalleryIntent(data, 1)
            }

            2 -> if (resultCode == RESULT_OK && data != null && data.data != null) {
                handleGalleryIntent(data, 2)
            }

            3 -> if (resultCode == RESULT_OK && data != null && data.data != null) {
                handleGalleryIntent(data, 3)
            }
        }
    }

    private fun shoeFileName(displayName: String?) {

        bindingProfessionalVerification!!.tvResumeSubTitle.setText(displayName)

    }

    private fun getFileNameByUri(professionalVerification: ProfessionalVerification, fileuri: Uri): Any {


        val file: File

        if (fileuri.getScheme().toString().compareTo("content") == 0) {
            val cursor: Cursor = professionalVerification.getContentResolver().query(
                fileuri,
                arrayOf(android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION),
                null,
                null,
                null
            );


            val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();0

            val mImagePath: String = cursor.getString(column_index);
            cursor.close();
            filepath = mImagePath;

        } else
            if (fileuri.getScheme().compareTo("file") == 0) {
                try {

                    val file: File = File(URI(fileuri.toString()))
//                        file = new File(new URI(fileuri.toString()));
                    if (file!!.exists())
                        filepath = file.getAbsolutePath();

                } catch (e: URISyntaxException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                filepath = fileuri.getPath();
            }
        return filepath;
    }

    private fun handleGalleryIntent(data: Intent, imagePosition: Int) {
        try {
            val selectedImageUri = data.data
            val file = File(getRealPathForGalley(this, selectedImageUri))

            integerFileHashMap[imagePosition -1 ] = file
            integerURIHashMap[imagePosition - 1] = selectedImageUri

            val imageBitmap = MediaStore.Images.Media.getBitmap(this!!.getContentResolver(), selectedImageUri)
            if (imagePosition == 1) {
                Picasso.get().load(data.data).into(bindingProfessionalVerification!!.ivPhotoForPassportDriving)
                bindingProfessionalVerification!!.icAddPhotoIdentification.visibility = View.GONE
            } else if (imagePosition == 2) {
                Picasso.get().load(data.data).into(bindingProfessionalVerification!!.ivPhotoForCertification)
                bindingProfessionalVerification!!.icAddCertificationIdentification.visibility = View.GONE
            } else if (imagePosition == 3) {
                Picasso.get().load(data.data).into(bindingProfessionalVerification!!.ivPhotoForEducation)
                bindingProfessionalVerification!!.icAddEducationIdentification.visibility = View.GONE
            }
            //            Picasso.w(Jobdetails.this).load(data.getData()).noPlaceholder().centerCrop().fit().into((ImageView) findViewById(R.id.imageView1));
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getRealPathFromURI(context: Context?, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context!!.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    fun getRealPathForGalley(activity: Context, captureImageUri: Uri): String {
        var wholeID: String? = null
        try {
            wholeID = DocumentsContract.getDocumentId(captureImageUri)
        } catch (e: Exception) {
            return getRealPathFromURI(this, captureImageUri)
        }

        // Split at colon, use second item in the array
        val id = wholeID!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

        val column = arrayOf(MediaStore.Images.Media.DATA)

        // where id is equal to
        val sel = MediaStore.Images.Media._ID + "=?"

        val cursor = this!!.getContentResolver().query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            column, sel, arrayOf(id), null
        )

        var filePath = ""

        val columnIndex = cursor!!.getColumnIndex(column[0])

        if (cursor!!.moveToFirst()) {
            filePath = cursor!!.getString(columnIndex)
        }

        cursor!!.close()
        return CommonUtils.compressImage(filePath, this)
    }

    lateinit var imageFilePath: String;

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp =
            SimpleDateFormat(
                "yyyyMMdd_HHmmss",
                Locale.getDefault()
            ).format(Date());
        val imageFileName = "IMG_" + timeStamp + "_";
        val storageDir = Environment.getExternalStorageDirectory()
        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;

    }
}
