package com.jmzd.ghazal.onlineshop.api

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.jmzd.ghazal.onlineshop.Userid
import com.jmzd.ghazal.onlineshop.dataModel.Datamodel_address
import org.json.JSONObject

class Api_address (val content: Context):Config {

    fun Get_list(face:Getlist){
        var list=ArrayList<Datamodel_address>()
        val user= Userid(content)

        val Jsarry= JsonArrayRequest(Baseurl+"get_address.php?user="+user.Getuser(), { response ->
            Log.d("shopError", response.toString())

            for(i in 0 until response.length()){
                val jsonObject: JSONObject =response.getJSONObject(i)
                var data=Datamodel_address(jsonObject.getString("id"),jsonObject.getString("iduser"),
                    jsonObject.getString("city"),jsonObject.getString("meli"),jsonObject.getString("code"),
                    jsonObject.getString("address"),jsonObject.getString("phone"),jsonObject.getString("tell"))
                list.add(data)
            }
            face.list(list)

        }, { error ->

            Log.d("shopError", error.toString())
        })
        //Jsarry.retryPolicy = DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE)
        Jsarry.retryPolicy = DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE)
        // ۱۰ ثانیه - هندل کردن اینترنت ضعیف و... که اگر پاسخ را از سرور دیر دریافت کردیم و... . مقادیر پیش فرض است
        Volley.newRequestQueue(content).add(Jsarry)
    }

    interface Getlist{
        fun list(list:ArrayList<Datamodel_address>)
    }
    //چرا به این اینترفیس احتیاج داریم؟
    // چون داده ای که از سمت سرور دریافت می کنیم ممکن است کمی زمان بر باشد.
    // داگر متد باشد ممکن است به مشکل بخوریم. مثلا داده را زودتر دریافت کنیم و null اتفاق بیفتد.
    // به همین خاطر اینترفبیسی این وسط است که منتظر می ماند تا داده را دریافت کند و به محض دریافت داده آن را به main برمی گرداند.
}