package com.playground.storelauncher

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import kotlin.apply
import kotlin.collections.isNotEmpty
import kotlin.collections.toTypedArray

object AppStoreHelper {

    fun launchSupportedAppStore(
        packageName: String,
        context: Context,
        activityLauncher: ActivityResultLauncher<Intent>
    ) {
        val packageManager = context.applicationContext.packageManager
        val appStoreIntent = Intent()
        appStoreIntent.action = Intent.ACTION_VIEW
        appStoreIntent.data = Uri.parse(
            "market://details?id=${packageName}"
        )
        val resInfos: List<ResolveInfo> = packageManager.queryIntentActivities(appStoreIntent, 0)
        if (resInfos.isNotEmpty()) {
            launchAppStore(
                getAppStoresIntents(
                    resInfos,
                    packageName
                ), context, activityLauncher
            )
        } else {
            Toast.makeText(context, "No app store apps installed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAppStoresIntents(
        resInfos: List<ResolveInfo>,
        packageName: String
    ): MutableList<Intent> {
        var appStoreIntents: MutableList<Intent> = ArrayList()
        for (resInfo in resInfos) {
            val appStorePackageName = resInfo.activityInfo.packageName
            appStoreIntents.add(Intent(Intent.ACTION_VIEW).apply {
                component = ComponentName(appStorePackageName, resInfo.activityInfo.name)
                data = Uri.parse(
                    "market://details?id=${packageName}"
                )
                setPackage(appStorePackageName)
            })
        }
        return appStoreIntents
    }

    private fun launchAppStore(
        targetAppStoreIntents: MutableList<Intent>,
        context: Context,
        activityLauncher: ActivityResultLauncher<Intent>
    ) {
        if (targetAppStoreIntents.isNotEmpty()) {
            val chooserIntent =
                Intent.createChooser(
                    targetAppStoreIntents.removeAt(0),
                    context.getString(R.string.update_with)
                )
            chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS, targetAppStoreIntents.toTypedArray()
            )
            activityLauncher.launch(chooserIntent)
        } else {
            Toast.makeText(context, "No app store apps installed", Toast.LENGTH_SHORT).show()
        }
    }
}
