package com.jmzd.ghazal.onlineshop.api

import android.content.Context
import android.view.View
import android.widget.TextView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.jmzd.ghazal.onlineshop.Userid
import com.jmzd.ghazal.onlineshop.dataModel.Datamodel_cart
import com.pnikosis.materialishprogress.ProgressWheel
import org.json.JSONObject

class Api_cart (val content: Context, val progressWheel: ProgressWheel, val Tv_error: TextView):Config {

    fun Get_list(face:Getlist){
        val user= Userid(content)

        var list=ArrayList<Datamodel_cart>()
        val Jsarry=
            JsonArrayRequest(Baseurl+"Get_record_cart.php?user="+user.Getuser(), { response ->
            progressWheel.visibility= View.GONE
            if(response.length()>0){
                Tv_error.visibility=View.GONE
                for(i in 0 until response.length()){
                    val jsonObject: JSONObject =response.getJSONObject(i)
                    var data=Datamodel_cart(jsonObject.getString("id"),jsonObject.getString("title"),
                        jsonObject.getString("imageurl"),jsonObject.getString("date"),jsonObject.getString("view"),
                        jsonObject.getString("des"),jsonObject.getString("price"),jsonObject.getString("idproduct"),
                        jsonObject.getString("count"))
                    list.add(data)
                }
                face.list(list)
            }
            else
            {
                Tv_error.visibility=View.VISIBLE
                Tv_error.setText("سبد خرید شما خالی است...")
            }


        }, Response.ErrorListener { error ->
            progressWheel.visibility=View.GONE
        })
        Jsarry.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
        Volley.newRequestQueue(content).add(Jsarry)
    }

    interface Getlist{
        fun list(list:ArrayList<Datamodel_cart>)
    }
}