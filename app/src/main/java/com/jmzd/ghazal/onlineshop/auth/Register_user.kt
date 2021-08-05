package com.jmzd.ghazal.onlineshop.auth

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jmzd.ghazal.onlineshop.R
import com.jmzd.ghazal.onlineshop.api.Config
import com.jmzd.ghazal.onlineshop.databinding.ActivityRegisterUserBinding
import org.json.JSONObject

class Register_user : AppCompatActivity() , Config {
    lateinit var binding: ActivityRegisterUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        binding= ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnLogin.setOnClickListener {
            val intent= Intent(applicationContext,Login_user::class.java)
            startActivity(intent)
        }

        binding.BtnRegister.setOnClickListener {
            Get_Auth()
        }
    }


    fun Get_Auth(){
        val mobile=binding.EtPhone.text.toString().trim()
        val pass=binding.EtPass.text.toString().trim()
        val name=binding.EtName.text.toString().trim()
        val email=binding.EtEmail.text.toString().trim()

        if(mobile.isNullOrEmpty()||pass.isNullOrEmpty()||name.isNullOrEmpty()||email.isNullOrEmpty()){
            Toast.makeText(applicationContext,"لطفا تمام فیلدها را پر کنید...", Toast.LENGTH_LONG).show()
        } else {
            val jsonObject=object : StringRequest(1,Baseurl+"reg.php",
                Response.Listener { response ->
                    val jsonObject = JSONObject(response)
                    val Strcheck=jsonObject.getString("status")
                    if(Strcheck.equals("ok")){

                        val sharedPreferences=getSharedPreferences("user",0)
                        val edit: SharedPreferences.Editor=sharedPreferences.edit()
                        edit.putString("id",jsonObject.getString("user_id"))
                        edit.apply()

                        val intent=Intent(applicationContext,Panel_user::class.java)
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
                    hashMap.put("name",name)
                    hashMap.put("mobile",mobile)
                    hashMap.put("email",email)
                    hashMap.put("pass",pass)
                    return hashMap
                }
            }

            jsonObject.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
            Volley.newRequestQueue(applicationContext).add(jsonObject)
        }
    }
}
