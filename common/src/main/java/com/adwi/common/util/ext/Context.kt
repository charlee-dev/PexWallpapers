package com.adwi.common.util.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast


fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

tailrec fun Context?.activity(): Activity? = this as? Activity
    ?: (this as? ContextWrapper)?.baseContext?.activity()