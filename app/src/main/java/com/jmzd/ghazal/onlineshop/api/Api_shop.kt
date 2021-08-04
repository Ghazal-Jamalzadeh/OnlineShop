package com.jmzd.ghazal.onlineshop.api

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.jmzd.ghazal.onlineshop.dataModel.Datamodel_shop
import com.pnikosis.materialishprogress.ProgressWheel
import org.json.JSONObject


class Api_shop(val content: Context, val progressWheel: ProgressWheel):Config {

    fun Get_list(face:Getlist){
        var list=ArrayList<Datamodel_shop>()
        val Jsarry= JsonArrayRequest(Baseurl, { response ->
            progressWheel.visibility= View.GONE
            Log.d("shopError", response.toString())

            for(i in 0 until response.length()){
                val jsonObject: JSONObject =response.getJSONObject(i)
                var data=Datamodel_shop(jsonObject.getString("id"),jsonObject.getString("title"),
                    jsonObject.getString("imageurl"),jsonObject.getString("date"),jsonObject.getString("view"),
                    jsonObject.getString("des"),jsonObject.getString("price"))
                list.add(data)
            }
            face.list(list)

        }, { error ->
            progressWheel.visibility=View.GONE
            Log.d("shopError", error.toString())
        })
        //Jsarry.retryPolicy = DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE)
        Jsarry.retryPolicy = DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE)
        // ۱۰ ثانیه - هندل کردن اینترنت ضعیف و... که اگر پاسخ را از سرور دیر دریافت کردیم و... . مقادیر پیش فرض است
        Volley.newRequestQueue(content).add(Jsarry)
    }

    interface Getlist{
        fun list(list:List<Datamodel_shop>)
    }
     //چرا به این اینترفیس احتیاج داریم؟
    // چون داده ای که از سمت سرور دریافت می کنیم ممکن است کمی زمان بر باشد.
    // داگر متد باشد ممکن است به مشکل بخوریم. مثلا داده را زودتر دریافت کنیم و null اتفاق بیفتد.
    // به همین خاطر اینترفبیسی این وسط است که منتظر می ماند تا داده را دریافت کند و به محض دریافت داده آن را به main برمی گرداند.
}