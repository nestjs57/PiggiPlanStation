package com.arnoract.piggiplanstation.core

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.pm.PackageInfoCompat
import com.arnoract.piggiplanstation.R

fun Context.toast(
    message: CharSequence,
    length: Int = Toast.LENGTH_LONG
) {
    val inflater = LayoutInflater.from(this)
    val layout = inflater.inflate(R.layout.toast_alert, null, false)
    val text: TextView = layout.findViewById(R.id.toastTextView)
    text.text = message
    with(Toast(applicationContext)) {
        duration = length
        view = layout
        show()
    }
}

fun Context.appVersionCode(): Long {
    return try {
        val packageInfo = applicationContext.packageManager.getPackageInfo(
            applicationContext.packageName,
            0
        )
        PackageInfoCompat.getLongVersionCode(packageInfo)
    } catch (e: PackageManager.NameNotFoundException) {
        throw RuntimeException("Could not get package name: $e")
    }
}

fun Context.appVersionName(): String {
    return try {
        val packageInfo = applicationContext.packageManager.getPackageInfo(
            applicationContext.packageName,
            0
        )
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        throw RuntimeException("Could not get package name: $e")
    }
}

fun Context.isDebug(): Boolean {
    return 0 != this.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
}
