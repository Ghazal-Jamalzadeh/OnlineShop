package com.jmzd.ghazal.onlineshop.api

import android.content.Context
import android.view.View
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
        val Jsarry= JsonArrayRequest(Baseurl,Response.Listener { response ->
            progressWheel.visibility= View.GONE
            for(i in 0 until response.length()){
                val jsonObject: JSONObject =response.getJSONObject(i)
                var data=Datamodel_shop(jsonObject.getString("id"),jsonObject.getString("title"),
                    jsonObject.getString("imageurl"),jsonObject.getString("date"),jsonObject.getString("view"),
                    jsonObject.getString("des"),jsonObject.getString("price"))
                list.add(data)
            }
            face.list(list)

        }, Response.ErrorListener { error ->
            progressWheel.visibility=View.GONE
        })
        Jsarry.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
        // ۱۰ ثانیه - هندل کردن اینترنت ضعیف و... که اگر پاسخ را از سرور دیر دریافت کردیم و... . مقادیر پیش فرض است
        Volley.newRequestQueue(content).add(Jsarry)
    }

    interface Getlist{
        fun list(list:List<Datamodel_shop>)
    }
}

//jsonArrayRequest -> 0 = get data from server / 1 = send data to server