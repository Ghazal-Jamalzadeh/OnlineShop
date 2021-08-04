package com.jmzd.ghazal.onlineshop.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jmzd.ghazal.onlineshop.R
import com.jmzd.ghazal.onlineshop.api.Config
import com.jmzd.ghazal.onlineshop.databinding.ActivityLoginUserBinding
import org.json.JSONObject


class Login_user : AppCompatActivity(), Config {
    lateinit var binding: ActivityLoginUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)

        binding= ActivityLoginUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnLogin.setOnClickListener {
            Get_Auth()
        }

        binding.BtnRegister.setOnClickListener {
            val intent= Intent(applicationContext,Register_user::class.java)
            startActivity(intent)
        }

    }

    fun Get_Auth(){
        val mobile=binding.EtPhone.text.toString().trim()
        val pass=binding.EtPass.text.toString().trim()
        if(mobile.isNullOrEmpty()){
            Toast.makeText(applicationContext,"لطفا شماره موبایل را وارد کنید...", Toast.LENGTH_LONG).show()
        }
        else if(pass.isNullOrEmpty()){
            Toast.makeText(applicationContext,"لطفا رمز عبور را وارد کنید...",Toast.LENGTH_LONG).show()

        }else
        {    // stringRequest = می خواهیم چیزی را به سمت سرور بفرستیم.
            // 1 = post method | 0 = get method
            val jsonObject=object : StringRequest(1,Baseurl+"login.php",
                Response.Listener { response ->
                    val jsonObject = JSONObject(response)
                    val Strcheck=jsonObject.getString("status")
                    if(Strcheck.equals("ok")){

//                        val sharedPreferences=getSharedPreferences("user",0)
//                        val edit: SharedPreferences.Editor=sharedPreferences.edit()
//                        edit.putString("id",jsonObject.getString("user_id"))
//                        edit.apply()
                        val intent=Intent(applicationContext,Panel_user::class.java)
                        startActivity(intent)
                        finish()

                    }else
                    {
                        Toast.makeText(applicationContext,"شماره موبایل یا پسورد عبور اشتباه است...",Toast.LENGTH_LONG).show()

                    }
                }
                , Response.ErrorListener { error ->
                    Toast.makeText(applicationContext,error.message,Toast.LENGTH_LONG).show()
                }){
                override fun getParams(): MutableMap<String, String> {
                    val hashMap=HashMap<String,String>()
                    hashMap.put("mobile",mobile)
                    hashMap.put("pass",pass)
                    return hashMap
                }
            }

            jsonObject.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
            Volley.newRequestQueue(applicationContext).add(jsonObject)
        }

    }


}
