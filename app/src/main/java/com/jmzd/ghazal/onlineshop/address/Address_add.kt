package com.jmzd.ghazal.onlineshop.address

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jmzd.ghazal.onlineshop.R
import com.jmzd.ghazal.onlineshop.Userid
import com.jmzd.ghazal.onlineshop.api.Config
import com.jmzd.ghazal.onlineshop.databinding.ActivityAddressAddBinding
import com.jmzd.ghazal.onlineshop.databinding.ActivityLoginUserBinding
import org.json.JSONObject

class Address_add : AppCompatActivity() , Config {
    lateinit var binding: ActivityAddressAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_add)

        binding= ActivityAddressAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnRegister.setOnClickListener {
            Get_Auth()
        }
    }
    fun Get_Auth(){
        val user= Userid(applicationContext)
        val jsonObject=object : StringRequest(1,Baseurl+"add_address.php",
             { response ->
                val jsonObject = JSONObject(response)
                val Strcheck=jsonObject.getString("status")
                if(Strcheck.equals("ok")){
                    val intent= Intent(applicationContext, Address_activity::class.java)
                    startActivity(intent)
                    finish()

                }else
                {
                    Toast.makeText(applicationContext,"خطا در ارسال اطلاعات به سمت سرور",
                        Toast.LENGTH_LONG).show()

                }
            }
            , Response.ErrorListener { error ->
                Toast.makeText(applicationContext,error.message, Toast.LENGTH_LONG).show()
            }){
            override fun getParams(): MutableMap<String, String> {
                val hashMap=HashMap<String,String>()
                hashMap.put("iduser",user.Getuser())
                hashMap.put("city",binding.EtCity.text.toString())
                hashMap.put("meli",binding.EtMeli.text.toString())
                hashMap.put("code",binding.EtCode.text.toString())
                hashMap.put("address",binding.EtAddress.text.toString())
                hashMap.put("phone",binding.EtPhone.text.toString())
                hashMap.put("tell",binding.EtTell.text.toString())
                return hashMap
            }
        }

        jsonObject.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
        Volley.newRequestQueue(applicationContext).add(jsonObject)
    }

}
