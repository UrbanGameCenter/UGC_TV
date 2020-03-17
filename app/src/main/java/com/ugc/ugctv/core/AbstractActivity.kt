package com.ugc.ugctv.core

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.ugc.ugctv.common.ui.ErrorDialogFragment
import com.ugc.ugctv.common.ui.ProgressDialog

abstract class AbstractActivity : FragmentActivity() {

    val ERROR_DIALOG = "errorDialog"
    val SUCCESS_DIALOG = "successMessage"

    lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeProgressDialog()
    }

    open fun setTranslucideStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    protected open fun makeProgressDialog() {
        progressDialog = ProgressDialog(this)
    }

    open fun showError(message: String?) {
        showError(null, message)
    }

    open fun showError(title: String?, message: String?) {
        val errorDialogFragment: ErrorDialogFragment = ErrorDialogFragment()
            .setTitle(title)
            .setMessage(message)
        errorDialogFragment.show(supportFragmentManager, ERROR_DIALOG)
    }
}