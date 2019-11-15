package com.ukrim.ppbl.posapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.preference.PreferenceManager
import android.util.Log
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage

object Preferences{
    fun saveToken(token: String, context: Context): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        prefsEditor.putString(Const.KEY_TOKEN, token)
        prefsEditor.apply()
        return true
    }
    fun getToken(context: Context): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(Const.KEY_TOKEN, "")
    }
    fun saveUsername(token: String, context: Context): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        prefsEditor.putString(Const.KEY_USERNAME, token)
        prefsEditor.apply()
        return true
    }
    fun getUsername(context: Context): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(Const.KEY_USERNAME, "")
    }
    fun savePassword(token: String, context: Context): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        prefsEditor.putString(Const.KEY_PASSWORD, token)
        prefsEditor.apply()
        return true
    }
    fun getPassword(context: Context): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(Const.KEY_PASSWORD, "")
    }
    fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap {

        Log.e("TAG ORIET", orientation.toString());
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                return rotateImage(bitmap, 90);
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                return rotateImage(bitmap, 180);
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                return rotateImage(bitmap, 270);
            }
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> {
                return flip(bitmap, (-1).toFloat(), 1.toFloat());
            }
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                return flip(bitmap, 1.toFloat(), (-1).toFloat());
            }
            else -> {
                return bitmap
            }
        }
    }

    fun rotateImage(img: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix();
        matrix.postRotate(degree);
        val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    fun flip(bitmap: Bitmap, horizontal: Float, vertical: Float): Bitmap {
        val matrix = Matrix();
        matrix.preScale(horizontal, vertical)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true)
    }

}