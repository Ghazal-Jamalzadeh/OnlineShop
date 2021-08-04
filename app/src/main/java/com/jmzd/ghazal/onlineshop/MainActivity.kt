package com.jmzd.ghazal.onlineshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.onlineshop.adapter.Adapter_recyclerview_shop
import com.jmzd.ghazal.onlineshop.api.Api_shop
import com.jmzd.ghazal.onlineshop.dataModel.Datamodel_shop
import com.jmzd.ghazal.onlineshop.databinding.ActivityMainBinding
import com.pnikosis.materialishprogress.ProgressWheel

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var adapter: Adapter_recyclerview_shop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Setup()
    }
    fun Setup(){
        val api = Api_shop(applicationContext,binding.progressWheel )
        api.Get_list(object : Api_shop.Getlist{
            override fun list(list: List<Datamodel_shop>) {
                binding.recyclerview.layoutManager= LinearLayoutManager(applicationContext)
                adapter = Adapter_recyclerview_shop(applicationContext,list)
                binding.recyclerview.adapter=adapter
                binding.recyclerview.setHasFixedSize(true)
            }
        })
    }
}