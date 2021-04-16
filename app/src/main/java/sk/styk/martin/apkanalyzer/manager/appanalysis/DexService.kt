package sk.styk.martin.apkanalyzer.manager.appanalysis

import android.content.pm.PackageInfo
import android.util.Log
import dalvik.system.DexFile
import sk.styk.martin.apkanalyzer.model.detail.ClassPathData
import java.io.IOException
import java.util.*
import javax.inject.Inject

@Suppress("DEPRECATION")
class DexService @Inject constructor(){

    fun get(packageInfo: PackageInfo): ClassPathData {

        val packageClasses = ArrayList<String>()
        val otherClasses = ArrayList<String>()
        var innerClasses = 0

        if (packageInfo.applicationInfo != null) {

            var dexFile: DexFile? = null
            try {
                dexFile = DexFile(packageInfo.applicationInfo.sourceDir)
                val iterator = dexFile.entries()
                while (iterator.hasMoreElements()) {
                    val className = iterator.nextElement()
                    if (className != null && className.startsWith(packageInfo.applicationInfo.packageName))
                        packageClasses.add(className)
                    else
                        otherClasses.add(className)

                    if (className != null && className.contains("$")) {
                        innerClasses++
                    }
                }
            } catch (e: IOException) {
                Log.e(DexService::class.java.simpleName, e.localizedMessage ?: "")
            } finally {
                try {
                    dexFile?.close()
                } catch (e: IOException) {
                    Log.e(DexService::class.java.simpleName, e.localizedMessage ?: "")
                }
            }
        }

        return ClassPathData(
                packageClasses = packageClasses,
                otherClasses = otherClasses,
                numberOfInnerClasses = innerClasses
        )
    }
}