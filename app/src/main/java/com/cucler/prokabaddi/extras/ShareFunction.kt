package com.cucler.prokabaddi.extras

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

fun shareUs(context: Context) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    val shareBody =
        "Get Latest Updates of Vivo Pro Kabaddi 2022 Season 9 and Refresh Yourself! Download the App Now With the Link Given Below!\nhttps://play.google.com/store/apps/details?id=com.cucler.prokabaddi2022"
    val shareSubject =
        "Get Latest Updates of Vivo Pro Kabaddi 2022 Season 9 and Refresh Yourself! Download the App Now With the Link Given Below!"
    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
    startActivity(context, Intent.createChooser(sharingIntent, "Share Using"), null)
}