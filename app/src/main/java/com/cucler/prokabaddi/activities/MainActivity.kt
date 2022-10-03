package com.cucler.prokabaddi.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.cucler.prokabaddi.R
import com.cucler.prokabaddi.databinding.ActivityMainBinding
import com.cucler.prokabaddi.extras.loadVideoInterestitial
import com.cucler.prokabaddi.extras.shareUs
import com.cucler.prokabaddi.extras.showVideoInterstitial
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        btn_back.setOnClickListener {
            onBackPressed()
            btn_back.visibility = GONE
        }

        btnToolbarGreetUs.setOnClickListener {
            loadVideoInterestitial(this, R.string.videoInterstitialGreetUsAdId.toString())
            showVideoInterstitial(this)
        }
        btnToolbarRateUs.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.cucler.prokabaddi")
                )
            )
        }
        btnToolbarShareUs.setOnClickListener {
            shareUs(this)
        }
        btnToolbarMoreApps.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/dev?id=5618520249899005145")
                )
            )
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navBottom)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        setContentView(binding.root)
    }

    fun customExitDialog() {
        val dialog = Dialog(this)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.custom_exit_dialog)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = android.R.style.Animation_Dialog
        val dialogButtonYes = dialog.findViewById(R.id.imgYes) as ImageView
        val dialogButtonNo = dialog.findViewById(R.id.imgNo) as ImageView
        dialogButtonNo.setOnClickListener {
            dialog.dismiss()
        }
        dialogButtonYes.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }

    override fun onBackPressed() {
        if (btn_back.visibility == GONE) {
            customExitDialog()
        } else {
            btn_back.visibility = GONE
            super.onBackPressed()
        }
    }

}