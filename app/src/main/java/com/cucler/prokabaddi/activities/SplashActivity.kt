package com.cucler.prokabaddi.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.cucler.prokabaddi.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        chngActivity()
    }

    fun chngActivity() {
        if(CheckNetwork.isInternetAvailable(this)) {
            Handler(Looper.myLooper()!!).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 300)
        }
        else {
            customExitDialog()
        }
    }

    fun customExitDialog() {
        val dialog = Dialog(this)
        dialog.setOnDismissListener {
            finish()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.custom_internet_dialog)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = android.R.style.Animation_Dialog
        val dialogButtonYes = dialog.findViewById(R.id.imgYes) as ImageView
        val dialogButtonNo = dialog.findViewById(R.id.imgNo) as ImageView
        dialogButtonNo.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialogButtonYes.setOnClickListener {
            chngActivity()
        }
        dialog.show()
    }

}

object CheckNetwork {
    private val TAG = CheckNetwork::class.java.simpleName
    fun isInternetAvailable(context: Context): Boolean {
        val info =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return if (info == null) {
            Log.d(TAG, "no internet connection")
            false
        } else {
            if (info.isConnected) {
                Log.d(TAG, " internet connection available...")
                true
            } else {
                Log.d(TAG, " internet connection")
                true
            }
        }
    }
}