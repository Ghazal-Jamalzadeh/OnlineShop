package com.jmzd.ghazal.onlineshop.api

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.jmzd.ghazal.onlineshop.Userid
import com.jmzd.ghazal.onlineshop.dataModel.Datamodel_pay
import org.json.JSONObject


class Api_pay(val content: Context):Config {

    fun Get_list(face:Getlist){
        var list=ArrayList<Datamodel_pay>()
        val user= Userid(content)

        val Jsarry= JsonArrayRequest(Baseurl+"pay.php", { response ->
            for(i in 0 until response.length()){
                val jsonObject: JSONObject =response.getJSONObject(i)
                var data=Datamodel_pay(jsonObject.getString("title"),
                    jsonObject.getString("image"),jsonObject.getString("link"))
                list.add(data)
            }
            face.list(list)

        }, Response.ErrorListener { error ->
        })
        Jsarry.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
        Volley.newRequestQueue(content).add(Jsarry)
    }

    interface Getlist{
        fun list(list:ArrayList<Datamodel_pay>)
    }
}