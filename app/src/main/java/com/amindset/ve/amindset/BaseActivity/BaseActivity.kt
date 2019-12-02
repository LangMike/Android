package com.amindset.ve.amindset.BaseActivity

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.amindset.ve.amindset.AmidApp
import com.amindset.ve.amindset.R
import com.amindset.ve.amindset.Utils.CommonUtils
import com.amindset.ve.amindset.Utils.NetworkUtils

 open class BaseActivity : AppCompatActivity(), MvpView {

    private var mProgressDialog: ProgressDialog? = null

    override fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    override fun hideLoading() {
        try {
            if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
                mProgressDialog!!.cancel()
            }

        }catch (e : Exception)
        {}
    }

    fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(
            findViewById<View>(android.R.id.content),
            message, Snackbar.LENGTH_LONG
        )
        val sbView = snackbar.getView()
        val textView = sbView
            .findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.show()
    }
    override fun openActivityOnTokenExpire() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(resId: Int) {
        onError(getString(resId))
    }

    override fun onError(message: String?) {
        if (message != null) {
            showSnackBar(message)
        } else {
            showSnackBar(getString(R.string.some_error))
        }
    }

    override fun showMessage(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_LONG).show()
        }
    }

    override fun showMessage(resId: Int) {
        showMessage(getString(resId))
    }

    override fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext)
    }

    override fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)


    }
}
