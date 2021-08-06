package com.jmzd.ghazal.onlineshop.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.jmzd.ghazal.onlineshop.MainActivity
import com.jmzd.ghazal.onlineshop.R
import com.jmzd.ghazal.onlineshop.Userid
import com.jmzd.ghazal.onlineshop.api.Config
import com.jmzd.ghazal.onlineshop.cart.Cart_user
import com.jmzd.ghazal.onlineshop.databinding.ActivityLoginUserBinding
import com.jmzd.ghazal.onlineshop.databinding.ActivityPanelUserBinding
import org.json.JSONObject

class Panel_user : AppCompatActivity() , Config {
    lateinit var binding : ActivityPanelUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel_user)

        binding= ActivityPanelUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BtnHistory.setOnClickListener(){
            val intent= Intent(applicationContext,Cart_user::class.java)
            startActivity(intent)
        }
        Getapi()
    }

    fun Getapi(){
        val userid= Userid(applicationContext)
        val jsonarry= JsonArrayRequest(0,Baseurl+"user_info.php?user_id="+userid.Getuser(),null,
            { response ->
                val jsonObject: JSONObject =response.getJSONObject(0)
                binding.TvName.text="نام شما : "+jsonObject.getString("name")
                binding.TvMobile.text="شماره موبایل شما : ‌"+jsonObject.getString("mobile")
                binding.TvEmail.text="ایمیل شما : "+jsonObject.getString("email")
            },
            { error ->

            })

        jsonarry.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
        Volley.newRequestQueue(applicationContext).add(jsonarry)
    }
}
