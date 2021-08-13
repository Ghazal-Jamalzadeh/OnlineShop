package com.jmzd.ghazal.onlineshop.cart

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jmzd.ghazal.onlineshop.R
import com.jmzd.ghazal.onlineshop.Userid
import com.jmzd.ghazal.onlineshop.adapter.Adapter_pay
import com.jmzd.ghazal.onlineshop.api.Api_pay
import com.jmzd.ghazal.onlineshop.api.Config
import com.jmzd.ghazal.onlineshop.dataModel.Datamodel_pay
import com.jmzd.ghazal.onlineshop.databinding.ActivityOrderBinding
import org.json.JSONObject

class Activity_order : AppCompatActivity(), Config {
    lateinit var binding: ActivityOrderBinding
    lateinit var addressid:String
    lateinit var user: Userid
    lateinit var link_pay:String
    var code:String="null"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        binding= ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent=getIntent()
        addressid= intent.getStringExtra("idaddress").toString()
        user= Userid(applicationContext)

        Get_price()
        Get_pay()
        price_code()
        binding.BtnAddcart.setOnClickListener {
//            if(code==null){
//                Toast.makeText(applicationContext,"خطا در دریافت کد پرداخت...",Toast.LENGTH_LONG).show()
//            }else
//            {
//                if(link_pay.isNullOrEmpty()){
//                    Toast.makeText(applicationContext,"خطا در ارسال به سمت درگاه پرداخت",Toast.LENGTH_LONG).show()
//                }
//                else
//                {
//                    val intentaction= Intent(Intent.ACTION_VIEW, Uri.parse(link_pay+code))
//                    startActivity(intentaction)
//                }
//            }
            val intentaction= Intent(Intent.ACTION_VIEW, Uri.parse(link_pay))
                    startActivity(intentaction)
        }
    }

    fun Get_pay(){
        val api= Api_pay(applicationContext)
        api.Get_list(object :Api_pay.Getlist{
            override fun list(list: ArrayList<Datamodel_pay>) {
                binding.recyclerview.layoutManager= LinearLayoutManager(applicationContext)
                val adapter=Adapter_pay(applicationContext,list,object : Adapter_pay.Get_link{
                    override fun Str(link: String,title:String) {
                        link_pay=link
                        select(title)
                    }

                })
                binding.recyclerview.adapter=adapter
                binding.recyclerview.setHasFixedSize(true)
            }

        })
    }

    fun price_code(){
        val json= JsonObjectRequest(0,Baseurl+"add_order.php?user="+user.Getuser()+"&address="+addressid,null,
            { response ->
                val check=response.getString("status")
                if(check.equals("ok")){
                    code= response.getString("code")
                }
            }
            , Response.ErrorListener {
                code="null"
            })

        json.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
        Volley.newRequestQueue(applicationContext).add(json)

    }

    fun select(select:String){
        binding.TvSelectPay.text=select
    }


    fun Get_price(){

        val json= JsonArrayRequest(0,Baseurl+"Get_pricecart.php?user="+user.Getuser(),null,
            { response ->
            val json : JSONObject =response.getJSONObject(0)
            binding.TvPrice.text=json.getString("price")+" تومان "
        }
            , Response.ErrorListener { Error->

            })

        json.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
        Volley.newRequestQueue(applicationContext).add(json)

    }


}
