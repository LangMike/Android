package com.amindset.ve.amindset.Fragment.user.CounselorProfile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.amindset.ve.amindset.BaseActivity.BaseActivity
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelAvtar.ModelUpdateAvtar
import com.amindset.ve.amindset.Fragment.user.CounselorProfile.ModelLiability.ModelLiabilityDoc
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Utils.CommonUtils
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Professional_liability : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.rl_professional_liability->
            {
                selectImage(1)

            }

            R.id.ic_bac->
            {
               finish()

            }


            R.id.tv_save->
            {
                HitServerToUpdateProfessionalLicense()

            }
        }
    }

    lateinit var ic_add_photo_professional : ImageView
    lateinit var iv_photo_professional : ImageView
    lateinit var rl_professional_liability : RelativeLayout
    lateinit var tv_save : TextView
    internal  var photoFile: File?=null
    internal  var photoURI: Uri?=null
    var activity = this;
    var pref : AppPreferencesHelper? = null
    lateinit var ic_bac : ImageView



    private val integerFileHashMap = HashMap<Int, File>()
    private val integerURIHashMap = HashMap<Int, Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professional_liability)

         activity = this;
        pref = AppPreferencesHelper(activity)

        rl_professional_liability = findViewById(R.id.rl_professional_liability);
        ic_add_photo_professional = findViewById(R.id.ic_add_photo_professional);
        ic_add_photo_professional = findViewById(R.id.ic_add_photo_professional);
        iv_photo_professional = findViewById(R.id.iv_photo_professional);
        ic_bac = findViewById(R.id.ic_bac)
        ic_bac.setOnClickListener(this)


        rl_professional_liability
            .setOnClickListener(this)


        tv_save = findViewById(R.id.tv_save);

        tv_save
            .setOnClickListener(this)
    }




    private fun HitServerToUpdateProfessionalLicense() {

        val a = ArrayList<MultipartBody.Part>()

        showLoading()
        val apiService = ApiClient.getClient().create(WebService::class.java)

        if (integerFileHashMap.size > 0) {

            for (i in 1..integerFileHashMap.size) {

                val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), integerFileHashMap.get(i))
                val body = MultipartBody.Part.createFormData("liability_doc",integerFileHashMap.get(i)!!.getName(), requestFile)
                a.add(body)
            }

        }
        apiService.updateProvideLiabilityDoc(a , "Bearer "+pref!!.providerToken).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ModelLiabilityDoc> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(updateAvtar: ModelLiabilityDoc) {
                    a.clear()
                    hideLoading()
                    showMessage(updateAvtar.message)
                    hideLoading()
                    finish()

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
    private fun captureProfilePhoto(imagePosition: Int) {


        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(activity!!.getPackageManager()) != null) {
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
                    activity!!,
                    "com.amindset.ve.amindset.provider",
                    photoFile!!
                )

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureIntent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                startActivityForResult(takePictureIntent, imagePosition )
            }
        }
//                           val pictureIntent =  Intent(
//                              MediaStore.ACTION_IMAGE_CAPTURE
//                           );
//    if(pictureIntent.resolveActivity(activity!!.getPackageManager()) != null) {
//           startActivityForResult(pictureIntent,
//                              imagePosition);
    }
    private fun browseProfilePhoto(imagePosition: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("imageNumber", imagePosition)
        startActivityForResult(Intent.createChooser(intent, "Select picture"), imagePosition)
    }
    lateinit var  imageFilePath : String ;

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format( Date());
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            1->
            {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        handleGalleryIntent(data,1)
                    }
                    else {
                        showMessage("Fail to upload image ")
                    }
                }
            }
            2->
            {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        grabImage(iv_photo_professional, 2)
                    }
                    else {
                        showMessage("Fail to upload image ")
                    }
                }
            }
        }

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
            integerFileHashMap[1] = file
            imageView.setImageBitmap(bitmap)
            ic_add_photo_professional.visibility= View.GONE



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
                Picasso.get().load(data.data).into(iv_photo_professional)
            ic_add_photo_professional.visibility = View.GONE


        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    fun getRealPathForGalley(activity: FragmentActivity?, captureImageUri: Uri): String {
        var wholeID: String? = null
        try {
            wholeID = DocumentsContract.getDocumentId(captureImageUri)
        } catch (e: Exception) {
            return getRealPathFromURI(activity, captureImageUri)
        }

        // Split at colon, use second item in the array
        val id = wholeID!!.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

        val column = arrayOf(MediaStore.Images.Media.DATA)

        // where id is equal to
        val sel = MediaStore.Images.Media._ID + "=?"

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            column, sel, arrayOf(id), null
        )

        var filePath = ""

        val columnIndex = cursor!!.getColumnIndex(column[0])

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex)
        }

        cursor.close()
        return CommonUtils.compressImage(filePath, activity)
    }
    fun getImageUri(inContext: Context?, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()

        val path = MediaStore.Images.Media.insertImage(inContext!!.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
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

}
