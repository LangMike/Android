package com.amindset.ve.amindset.Fragment.user.CounselorProfile
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import com.amindset.ve.amindset.BaseActivity.BaseFragment
import com.amindset.ve.amindset.Constant
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelAddProviderFavList.ModelUserFavListAdd
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelAvtar.ModelUpdateAvtar
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelCallRate.ModelCounselorCallRate
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelDetails.Info
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelDetails.ModelCounselorDetails
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.TextChat.MainActivity
import com.amindset.ve.amindset.Utils.CommonUtils
import com.amindset.ve.amindset.Utils.NetworkUtils
import com.amindset.ve.amindset.VdoCall.VideoActivity
import com.amindset.ve.amindset.VoiceCAll.VoiceCallActivity
import com.amindset.ve.amindset.Web.ApiClient
import com.amindset.ve.amindset.Web.WebService
import com.amindset.ve.amindset.data.AppPreferencesHelper
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
import java.util.ArrayList
import java.util.HashMap

class CounselorProfileDetails : BaseFragment() , View.OnClickListener {
    override fun onClick(p0: View?) {
        when(p0!!.id)
        {
            R.id.iv_toobar_back->
            {
                Onbacktoolbarsetting()
                activity!!.supportFragmentManager.popBackStackImmediate()
            }

            R.id.tv_exp->
            {
               showSnackBar("It's is total experience")
            }

            R.id.profile_image->
            {
             selectImage(1)
            }

            R.id.ll_chat->
            {

//                showMessage("Comming soon")
                val intent = Intent(activity, MainActivity::class.java)
                intent.putExtra("providerName", providerNameForVideo)
                intent.putExtra("roomNameprovider", roomNameForVideo)
                intent.putExtra("providerImage", providerImageForVDO)
                intent.putExtra("type", "vdo")
                intent.putExtra("t_id", t_id)
                intent.putExtra("rate",vdoRatePerMin)
                startActivity(intent)
            }

            R.id.ll_video_call->
            {

                if(!TextUtils.isEmpty(roomNameForVideo)) {
                    val intent = Intent(activity, VideoActivity::class.java)
                    intent.putExtra("providerName", providerNameForVideo)
                    intent.putExtra("roomNameprovider", roomNameForVideo)
                    intent.putExtra("providerImage", providerImageForVDO)
                    intent.putExtra("type", "vdo")
                    intent.putExtra("t_id", t_id)
                    intent.putExtra("rate",vdoRatePerMin)
                    intent.putExtra("proficiency_type",proficiency_type)

                    startActivity(intent)

                    if(dialog!=null && dialog.isShowing)
                        dialog.dismiss()
                }
                else{
                    showMessage("Contact number is not available for video call.")
                }
            }

            R.id.ll_voice_call->
            {
                if(!TextUtils.isEmpty(roomNameForVideo)) {
                    val intent = Intent(activity, VoiceCallActivity::class.java)
                    intent.putExtra("providerName", providerNameForVideo)
                    intent.putExtra("roomNameprovider", roomNameForVideo)
                    intent.putExtra("providerImage", providerImageForVDO)
                    intent.putExtra("callerId", pref!!.userP_phone)
                    intent.putExtra("type", "audio")
                    intent.putExtra("rate", voiceRatePerMin)
                    intent.putExtra("t_id", t_id)


                    startActivity(intent)

                    if(dialog!=null && dialog.isShowing)
                        dialog.dismiss()
                }
                else{
                    showMessage("Contact number is not available for AUDIO call.")
                }


            }

            R.id.iv_like->
            {
                if(NetworkUtils.isNetworkConnected(activity)) {
                    if ( iv_like.getBackground().getConstantState()==getResources().getDrawable(R.mipmap.ic_favourite_off).getConstantState()) {

                        APIForLikeAndDislikeOfProvider(arguments!!.getString("id") , 1)
                    }
                    else
                    {
                        APIForLikeAndDislikeOfProvider(arguments!!.getString("id") , 0)

                    }
                }

                else

                    showSnackBar(getString(R.string.msg_check_internet))
            }

            R.id.contactnow->
            {
                if(!TextUtils.isEmpty(video_rate.text.toString())
                    && !TextUtils.isEmpty(audio_rate.text.toString()) &&
                    !TextUtils.isEmpty(msg_rate.text.toString())) {


                    if(!TextUtils.isEmpty(pref!!.usercard_id)) {
                        dialog = Dialog(activity);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_contact_now);
                        ll_video_call = dialog.findViewById(R.id.ll_video_call);
                        ll_voice_call = dialog.findViewById(R.id.ll_voice_call);
                        ll_chat = dialog.findViewById(R.id.ll_chat);

                        tv_video = dialog.findViewById(R.id.tv_video);
                        tv_voice = dialog.findViewById(R.id.tv_voice);
                        tv_chat_rate = dialog.findViewById(R.id.tv_chat_rate);

                        tv_video.setText(video_rate.text.toString())
                        tv_voice.setText(audio_rate.text.toString())
                        tv_chat_rate.setText(msg_rate.text.toString())

                        ll_video_call.setOnClickListener(this)
                        ll_voice_call.setOnClickListener(this)
                        ll_chat.setOnClickListener(this)
                        dialog.show();
                    }
                    else
                    {
                     showSnackBar("Please first add your card details!")
                    }
                }

                else
                {
                    showMessage("Contact rate is not defined yet. so you can't contact...")
                }

            }
        }
    }
    private fun APIForLikeAndDislikeOfProvider(fav_userid : String , bool : Int) {
     var call : Call<ModelUserFavListAdd>? = null
        val apiService = ApiClient.getClient().create(WebService::class.java)
        val paramObject = HashMap<String, String>()
        paramObject["usertype"] = "1"
        paramObject["fav_userid"] = fav_userid
        if(bool==1)
        {
           call = apiService.AddProviderInUserFavList(paramObject, "Bearer " + pref!!.userBToken)
        }
                else
        {
         call = apiService.RemoveProviderInUserFavList(paramObject , "Bearer " + pref!!.userBToken)
        }
                showLoading()
        call.enqueue(object : retrofit2.Callback<ModelUserFavListAdd> {

            override fun onResponse(call: Call<ModelUserFavListAdd>, response: Response<ModelUserFavListAdd>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        if(response.body().status.equals(Constant.RESPONSE_SUCCESSFULLY)) {
                            showMessage(response.body().message)

                            if ( iv_like.getBackground().getConstantState()==
                                getResources().getDrawable(R.mipmap.ic_favourite_off).getConstantState()) {

                                iv_like.setBackgroundResource(R.mipmap.ic_favourite_on)
                            }
                            else
                            {
                                iv_like.setBackgroundResource(R.mipmap.ic_favourite_off)

                            }
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
            override fun onFailure(call: Call<ModelUserFavListAdd>, t: Throwable) {
                // Log error here since request failed
                hideLoading()
            }
        })
    }
    var pref : AppPreferencesHelper? = null
    private var providerNameForVideo : String = ""
    private var roomNameForVideo : String = ""
    private var providerImageForVDO : String = ""
    private var vdoRatePerMin : String = ""
    private var voiceRatePerMin : String = ""
    lateinit var user_name : TextView
    lateinit var profile_image : ImageView
    lateinit var iv_like : ImageView
    lateinit var tv_profiecency : TextView
    lateinit var tv_exp : TextView
    lateinit var tv_rating : TextView
    lateinit var et_aboutYou : EditText
    lateinit var msg_rate : TextView
    lateinit var audio_rate : TextView
    lateinit var video_rate : TextView
    lateinit var tv_whom_Details : TextView
    lateinit var contactnow : TextView
    lateinit var dialog : Dialog
    internal  var photoFile: File?=null
    internal  var photoURI: Uri?=null
    lateinit var ll_video_call : LinearLayout
    lateinit var ll_voice_call : LinearLayout
    lateinit var ll_chat : LinearLayout
    lateinit var tv_video : TextView
    lateinit var tv_voice : TextView
    lateinit var tv_chat_rate : TextView
    private val integerFileHashMap = HashMap<Int, File>()
    private val integerURIHashMap = HashMap<Int, Uri>()
    var title : String = ""
    var t_id : String = ""

    lateinit var proficiency_type : String

    companion object {
        fun newInstance() = CounselorProfileDetails()
    }

    private lateinit var viewModel: CounselorProfileDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        pref = AppPreferencesHelper(activity)

        if(NetworkUtils.isNetworkConnected(activity)) {

            if(arguments!!.getString("id")!=null) {
                t_id = arguments!!.getString("id")
                APIforGetCunselorDeatils(arguments!!.getString("id"));
                APIforGetCunselorCallRate(arguments!!.getString("id"));
            }
            else
            {
                showSnackBar("Server error we are not able to get the ID of the current service")
            }

            if(arguments!!.getString("title")!=null)
            title= arguments!!.getString("title")
        }
        else
        {
            showSnackBar(getString(R.string.msg_check_internet))
            Onbacktoolbarsetting()
            activity!!.supportFragmentManager.popBackStackImmediate()
        }


        return inflater.inflate(R.layout.counselor_profile_details_fragment, container, false)
    }



    private fun APIforGetCunselorCallRate(proficiency_id: String) {

        val apiService = ApiClient.getClient().create(WebService::class.java)


        val paramObject = HashMap<String, String>()

        paramObject["therap_id"] = proficiency_id

        val call = apiService.getCounselorCallRate(paramObject,"Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelCounselorCallRate> {

            override fun onResponse(call: Call<ModelCounselorCallRate>, response: Response<ModelCounselorCallRate>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        if(response.body().status==Constant.RESPONSE_SUCCESSFULLY) {

                            if(!TextUtils.isEmpty(response.body().info[0].messagingRate))
                                msg_rate.setText("$ "+response.body().info[0].messagingRate +" / min")

                            if(!TextUtils.isEmpty(response.body().info[0].voiceCallingRate)) {
                                voiceRatePerMin = response.body().info[0].voiceCallingRate
                                video_rate.setText("$ " + response.body().info[0].voiceCallingRate + " / min")
                            }

                            if(!TextUtils.isEmpty(response.body().info[0].videoCallingRate)) {
                                audio_rate.setText("$ " + response.body().info[0].videoCallingRate + " / min")
                                vdoRatePerMin =  response.body().info[0].videoCallingRate
                            }

                            hideLoading()
                        }
                        else
                        {
                            showMessage(R.string.error_some_problem_occur)
                            hideLoading()

                        }
                    }else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                    }
                }
            }

            override fun onFailure(call: Call<ModelCounselorCallRate>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }

    private fun APIforGetCunselorDeatils(proficiency_id: String) {

        val apiService = ApiClient.getClient().create(WebService::class.java)

        val paramObject = HashMap<String, String>()

        paramObject["councelor_id"] = proficiency_id

        val call = apiService.getCounselorDetails(paramObject,"Bearer " + pref!!.userBToken)

        showLoading()

        call.enqueue(object : retrofit2.Callback<ModelCounselorDetails> {

            override fun onResponse(call: Call<ModelCounselorDetails>, response: Response<ModelCounselorDetails>?) {

                if (response != null) {
                    if (response.isSuccessful)
                    {
                        if(response.body().status==Constant.RESPONSE_SUCCESSFULLY) {
                            setDataOnView(response.body().info);
                            hideLoading()
                        }
                        else
                        {
                            showMessage(R.string.error_some_problem_occur)
                            hideLoading()
                            Onbacktoolbarsetting()
                            activity!!.supportFragmentManager.popBackStackImmediate()

                        }
                    }else {
                        hideLoading()
                        showMessage(R.string.error_some_problem_occur)
                        Onbacktoolbarsetting()
                        activity!!.supportFragmentManager.popBackStackImmediate()
                    }
                }
            }
            override fun onFailure(call: Call<ModelCounselorDetails>, t: Throwable) {
                // Log error here since request failed
                hideLoading()


            }


        })
    }


    private fun setDataOnView(info: Info) {

       if(!TextUtils.isEmpty(info.name)) {
           user_name.setText(info.name)
           providerNameForVideo = info.name
       }
        if(!TextUtils.isEmpty(info.tMobile))
            roomNameForVideo = info.tMobile


        if(info.likeStatus!=null && info.likeStatus==1)
            iv_like.setBackgroundResource(R.mipmap.ic_favourite_on)


        if(!TextUtils.isEmpty(info.professionTitle)) {
            tv_profiecency.setText(info.professionTitle)

            if(info.professionTitle.equals("Counselor"))
            {
                proficiency_type="1"

            }  else if(info.professionTitle.equals("Pharmacist"))
            {
                proficiency_type="3"

            }else if(info.professionTitle.equals("Medical"))
            {
                proficiency_type="2"

            }
            if(info.professionTitle.contains("Counselor") || info.professionTitle.contains("Pharmacist"))
            {

                tv_profiecency.setCompoundDrawablesWithIntrinsicBounds(getContext()!!.getResources().getDrawable( R.mipmap.ic_pharma_profile_provider ),null,null,null)

            }  else if(info.professionTitle.contains("Medical"))
            {
                tv_profiecency.setCompoundDrawablesWithIntrinsicBounds(getContext()!!.getResources().getDrawable( R.mipmap.ic_pharma_profile_provider ),null,null,null)
            }

        }

        if(!TextUtils.isEmpty(info.experience))
        tv_exp.setText(info.experience)


        if(!TextUtils.isEmpty(info.ratings as CharSequence?))
        tv_rating.setText(info.ratings as CharSequence?)


        if(!TextUtils.isEmpty(info.proPic)) {
            Picasso.get().load(info.proPic).into(profile_image)
            providerImageForVDO = info.proPic
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CounselorProfileDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user_name = view.findViewById(R.id.user_name) as TextView
        tv_profiecency = view.findViewById(R.id.tv_profiecency) as TextView
        tv_exp = view.findViewById(R.id.tv_exp) as TextView
        tv_exp.setOnClickListener(this)
        tv_rating = view.findViewById(R.id.tv_rating) as TextView
        msg_rate = view.findViewById(R.id.msg_rate) as TextView
        audio_rate = view.findViewById(R.id.audio_rate) as TextView
        video_rate = view.findViewById(R.id.video_rate) as TextView
        tv_whom_Details = view.findViewById(R.id.tv_whom_Details) as TextView
        contactnow = view.findViewById(R.id.contactnow) as TextView
        et_aboutYou = view.findViewById(R.id.et_aboutYou) as EditText
        profile_image = view.findViewById(R.id.profile_image) as ImageView
        iv_like = view.findViewById(R.id.iv_like) as ImageView
        iv_like.setOnClickListener(this)


        if(title!=null && title.equals("Medical"))
        {
            tv_profiecency.setCompoundDrawablesWithIntrinsicBounds(getContext()!!.getResources().getDrawable( R.mipmap.ic_pharma_profile_provider ),null,null,null)
        }


        contactnow.setOnClickListener(this)
        toolbarsetting()
    }

    private fun toolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundResource(0)
        tv_tolbar_center_text.setText(""+title)
        tv_tolbar_center_text.setTextColor(resources.getColor(R.color.black))
        tv_tolbar_center_text.setTextSize(16.0f)
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.VISIBLE
        iv_toolbar_back.setOnClickListener(this)
    }
    private fun Onbacktoolbarsetting() {
        val tv_tolbar_center_text =activity!!.findViewById(R.id.iv_amindset) as TextView
        tv_tolbar_center_text.setBackgroundDrawable(ContextCompat.getDrawable(activity!!, R.mipmap.amindset));
        tv_tolbar_center_text.setText("")
        val iv_toolbar_back =activity!!.findViewById(R.id.iv_toobar_back) as ImageView
        iv_toolbar_back.visibility = View.GONE
    }

    fun selectImage(imagePosition: Int) {
        val items = arrayOf<CharSequence>("Take Photo", "Choose from Library", "Cancel")
        val title = TextView(activity)
        title.text = "Add Photo!"
        title.setBackgroundColor(Color.BLACK)
        title.setPadding(10, 15, 15, 10)
        title.gravity = Gravity.CENTER
        title.setTextColor(Color.WHITE)
        title.textSize = 22f


        val builder = AlertDialog.Builder(        activity
        )


        builder.setCustomTitle(title)

        // builder.setTitle("Add Photo!");
        builder.setItems(items) { dialog, item ->
            if (items[item] == "Take Photo") {


                if (ContextCompat.checkSelfPermission(
                        activity!!,
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            Manifest.permission.CAMERA
                        )
                    ) {

                    } else {
                        ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf(Manifest.permission.CAMERA),
                            0
                        )
                    }

                } else {
                    captureProfilePhoto(2)

                }

            } else if (items[item] == "Choose from Library") {
                if (activity?.let {
                        ContextCompat.checkSelfPermission(
                            it,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    } != PackageManager.PERMISSION_GRANTED
                ) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    ) {

                    } else {
                        ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            0
                        )
                    }

                }
                if (ContextCompat.checkSelfPermission(
                        activity!!,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {

                    } else {
                        ActivityCompat.requestPermissions(
                            activity!!,
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

        if (takePictureIntent.resolveActivity(activity!!.getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile
            try {
                photoFile = createImageFile("picture", ".jpg")
            } catch (ex: IOException) {
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(
                    activity!!,
                    "com.amindset.ve.amindset.fileprovider",
                    photoFile!!
                )

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, imagePosition )
            }
        }



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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            1->
            {
                if (resultCode == Activity.RESULT_OK) {
                    handleGalleryIntent(data,1)
                }
            }
            2->
            {
                if (resultCode == Activity.RESULT_OK) {
                    grabImage(profile_image, 2)
                }
            }
        }

    }

    fun getImageUri(inContext: Context?, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()

        val path = MediaStore.Images.Media.insertImage(inContext!!.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun grabImage(imageView: ImageView, pos: Int) {
        activity!!.contentResolver.notifyChange(photoURI, null)
        val cr = activity!!.contentResolver
        val bitmap: Bitmap
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, photoURI)
            imageView.setImageBitmap(bitmap)

            val uri = getImageUri(activity!!.applicationContext, bitmap)
            val file = File(getRealPathFromURI(activity!!.applicationContext, uri))
                integerFileHashMap[0] = file
                imageView.setImageBitmap(bitmap)

        } catch (e: Exception) {
            Toast.makeText(activity, "Failed to load", Toast.LENGTH_SHORT).show()
        }

    }


    private fun handleGalleryIntent(data: Intent, imagePosition: Int) {
        try {
            val selectedImageUri = data.data
            val file = File(getRealPathForGalley(activity, selectedImageUri))

            integerFileHashMap.put(imagePosition, file)
            integerURIHashMap.put(imagePosition, selectedImageUri)

            val imageBitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, selectedImageUri)
            if (imagePosition == 1)
                Picasso.get().load(data.data).into(profile_image)

            HitServerToUpdateAvtar()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getRealPathForGalley(activity: FragmentActivity?, captureImageUri: Uri): String {
        var wholeID: String? = null
        try {
            wholeID = DocumentsContract.getDocumentId(captureImageUri)
        } catch (e: Exception) {
            return getRealPathFromURI(getActivity(), captureImageUri)
        }

        // Split at colon, use second item in the array
        val id = wholeID!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

        val column = arrayOf(MediaStore.Images.Media.DATA)

        // where id is equal to
        val sel = MediaStore.Images.Media._ID + "=?"

        val cursor = getActivity()!!.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            column, sel, arrayOf(id), null
        )

        var filePath = ""

        val columnIndex = cursor!!.getColumnIndex(column[0])

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex)
        }

        cursor.close()
        return CommonUtils.compressImage(filePath, getActivity())
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

    private fun HitServerToUpdateAvtar() {

        val a = ArrayList<MultipartBody.Part>()

        showLoading()
        val apiService = ApiClient.getClient().create(WebService::class.java)

        if (integerFileHashMap.size > 0) {

            for (i in 1..integerFileHashMap.size) {
                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), integerFileHashMap.get(i))
//
//                   val requestFile =
//            RequestBody.create(
//                         MediaType.parse(activity!!.getContentResolver().getType(integerURIHashMap.get(i))),
//                integerFileHashMap.get(i)
//             );


                val body = MultipartBody.Part.createFormData("prof_pic",integerFileHashMap.get(i)!!.getName(), requestFile)
                a.add(body)
            }

        }
        apiService.updateCounselorAvtar(a , "Bearer "+pref!!.userBToken).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ModelUpdateAvtar> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(updateAvtar: ModelUpdateAvtar) {
                    a.clear()
                    hideLoading()
                    showMessage(updateAvtar.message)
                    hideLoading()

                }

                override fun onError(e: Throwable) {
                    a.clear()
                    hideLoading()
                    handleError(e)
                }
            })


    }

    private fun handleError(t: Throwable?) {
        if (t != null)
          showMessage(t.message)
    }

}
