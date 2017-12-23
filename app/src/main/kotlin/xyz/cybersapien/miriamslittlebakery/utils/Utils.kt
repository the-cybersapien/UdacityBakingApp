package xyz.cybersapien.miriamslittlebakery.utils

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import xyz.cybersapien.miriamslittlebakery.BakeryApp
import java.io.IOException

@Throws(IOException::class)
fun getRecipeData(context: Context, dataListener: Response.Listener<String>) {

    val queue = BakeryApp.getRequestQueue(context)
    val dataUrl = "https://go.udacity.com/android-baking-app-json"

    val stringRequest = StringRequest(Request.Method.GET, dataUrl,
            dataListener,
            Response.ErrorListener { error ->
                throw IOException(error.message)
            }
    )
    queue.add(stringRequest)
}

inline fun <reified T> Gson.fromJson(json: String): T = this.fromJson<T>(json, object : TypeToken<T>() {}.type)
