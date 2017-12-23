package xyz.cybersapien.miriamslittlebakery

import android.app.Application
import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class BakeryApp : Application() {

    companion object {
        private var volleyRequestQueue: RequestQueue? = null

        fun getRequestQueue(context: Context): RequestQueue {
            if (volleyRequestQueue == null)
                volleyRequestQueue = Volley.newRequestQueue(context)
            return volleyRequestQueue!!
        }

        fun getRequestQueue(): RequestQueue {
            if (volleyRequestQueue == null)
                throw IllegalAccessException("Uninitialized request")
            return volleyRequestQueue!!
        }
    }
}