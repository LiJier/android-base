package com.core.os

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.widget.Toast
import androidx.core.content.FileProvider
import com.core.base.CoreApp
import java.io.File
import java.security.MessageDigest
import java.util.*

/**
 * Create by LiJie at 2019-05-29
 * String相关扩展方法
 */

/**
 * 获取字符MD5
 */
fun String.getEncryptedStr(): String? {
    try {
        val instance: MessageDigest = MessageDigest.getInstance("MD5")
        val digest: ByteArray = instance.digest(this.toByteArray())
        val sb = StringBuffer()
        for (b in digest) {
            val i: Int = b.toInt() and 0xff
            var hexString = Integer.toHexString(i)
            if (hexString.length < 2) {
                hexString = "0$hexString"
            }
            sb.append(hexString)
        }
        return sb.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

/**
 * 显示一个String的toast
 */

var toast: Toast? = null

fun String.toast() {
    toast?.let {
        it.setText(this)
        it.show()

    } ?: run {
        toast = Toast.makeText(CoreApp.appContext, this, Toast.LENGTH_SHORT)
        toast?.show()
    }
}

/**
 * 安装指定路径APK
 */
fun String.install() {
    val intent = Intent(Intent.ACTION_VIEW)
    val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        FileProvider.getUriForFile(
            CoreApp.appContext,
            "${CoreApp.appContext.packageName}.core.fileprovider",
            File(this)
        )
    } else {
        Uri.fromFile(File(this))
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    intent.setDataAndType(uri, "application/vnd.android.package-archive")
    CoreApp.appContext.startActivity(intent)
}

/**
 * 安装指定路径APK
 */
fun String.openDoc() {
    val intent = Intent(Intent.ACTION_VIEW)
    val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        FileProvider.getUriForFile(
            CoreApp.appContext,
            "${CoreApp.appContext.packageName}.core.fileprovider",
            File(this)
        )
    } else {
        Uri.fromFile(File(this))
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    intent.setDataAndType(uri, "application/msword")
    try {
        CoreApp.appContext.startActivity(intent)
    } catch (e: Exception) {
        "没有可以打开的APP".toast()
    }
}

fun String.formatMac(): String {
    val ar = arrayOfNulls<String>(this.length / 2)
    for (i in ar.indices) {
        ar[i] = this.substring(i * 2, i * 2 + 2)
    }
    return TextUtils.join(":", ar).toUpperCase(Locale.getDefault())
}