package com.jmzd.ghazal.onlineshop.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.jmzd.ghazal.onlineshop.R
import com.jmzd.ghazal.onlineshop.Userid
import com.jmzd.ghazal.onlineshop.adapter.Adapter_cartuser
import com.jmzd.ghazal.onlineshop.address.Address_activity
import com.jmzd.ghazal.onlineshop.api.Api_cart
import com.jmzd.ghazal.onlineshop.api.Config
import com.jmzd.ghazal.onlineshop.dataModel.Datamodel_cart
import com.jmzd.ghazal.onlineshop.databinding.ActivityCartUserBinding
import org.json.JSONObject

class Cart_user : AppCompatActivity() , Config {
    lateinit var binding:ActivityCartUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_user)

        binding= ActivityCartUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Cast()
        Setup()
        Get_price()
    }
    fun Setup(){
        val user= Userid(applicationContext)
        val api = Api_cart(applicationContext,binding.progressWheel,binding.TvError)
        api.Get_list(object : Api_cart.Getlist{
            override fun list(list: ArrayList<Datamodel_cart>) {
                binding.recyclerview.layoutManager= LinearLayoutManager(applicationContext)
                val adapter = Adapter_cartuser(applicationContext,user.Getuser(),list,object :
                    Adapter_cartuser.Get_change{
                    override fun getchange() {
                        Get_price()
                    }

                })
                binding.recyclerview.adapter=adapter
                binding.recyclerview.setHasFixedSize(true)
            }
        })
    }

    fun Get_price(){
        val user= Userid(applicationContext)

        val json=
            JsonArrayRequest(0,Baseurl+"Get_pricecart.php?user="+user.Getuser(),null
                ,  { response ->
            val json : JSONObject =response.getJSONObject(0)
            val check=json.getString("price")
            if(check=="null"){
                binding.TvError.visibility= View.VISIBLE
                binding.TvError.setText("سبد خرید شما خالی است...")
                binding.BtnAddcart.visibility=View.GONE
                binding.liner.visibility=View.GONE


            }
            else
            {
                binding.BtnAddcart.visibility=View.VISIBLE
                binding.liner.visibility=View.VISIBLE
                binding.TvError.visibility= View.GONE
                binding.TvPrice.text=json.getString("price")+" تومان "

            }

        }
            , Response.ErrorListener { Error->

            })

        json.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
        Volley.newRequestQueue(applicationContext).add(json)

    }

    fun Cast(){

        binding.BtnAddcart.setOnClickListener {
            val intent= Intent(applicationContext, Address_activity::class.java)
            startActivity(intent)
        }
    }
}
