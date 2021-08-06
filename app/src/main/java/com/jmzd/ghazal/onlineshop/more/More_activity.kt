package com.jmzd.ghazal.onlineshop.more

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.jmzd.ghazal.onlineshop.R
import com.jmzd.ghazal.onlineshop.Userid
import com.jmzd.ghazal.onlineshop.api.Config
import com.jmzd.ghazal.onlineshop.auth.Login_user
import com.jmzd.ghazal.onlineshop.cart.Cart_user
import com.jmzd.ghazal.onlineshop.databinding.ActivityMoreBinding

class More_activity : AppCompatActivity()  , Config{
    lateinit var binding:ActivityMoreBinding
    lateinit var idproduct:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        binding= ActivityMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent=getIntent()
        idproduct= intent.getStringExtra("id").toString()
        Glide.with(applicationContext).load(intent.getStringExtra("imageurl")).into(binding.ImPost)
        binding.TvTitle.text=intent.getStringExtra("title")
        binding.TvDate.text="تاریخ پست : "+intent.getStringExtra("date")
        binding.TvView.text="تعداد بازدید : "+intent.getStringExtra("view")
        binding.TvPrice.text=intent.getStringExtra("price")
        binding.webview.loadData(intent.getStringExtra("des").toString(),"text/html; charset=utf-8", "UTF-8")

        val user= Userid(applicationContext)
        binding.BtnAddcart.setOnClickListener {
            val check=user.Getuser()
            Log.d("ghazalError" , "user id = $check")
            if(check.equals("null") ){
                val intent= Intent(applicationContext, Login_user::class.java)
                startActivity(intent)
            }else
            {
                val json= JsonObjectRequest(Baseurl+"Addcart.php?check=add&user="+check+"&product="+idproduct+"&count=1",null,
                    { response ->
                        val check2=response.getString("status")
                        if(check2.equals("ok")){
                            val intent=Intent(applicationContext, Cart_user::class.java)
                            startActivity(intent)
                        }
                    }
                    ,
                    {

                    })

                json.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
                Volley.newRequestQueue(applicationContext).add(json)
            }

        }

    }

}
