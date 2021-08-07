package com.jmzd.ghazal.onlineshop.address

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.onlineshop.R
import com.jmzd.ghazal.onlineshop.adapter.Adapter_address
import com.jmzd.ghazal.onlineshop.adapter.Adapter_recyclerview_shop
import com.jmzd.ghazal.onlineshop.api.Api_address
import com.jmzd.ghazal.onlineshop.dataModel.Datamodel_address
import com.jmzd.ghazal.onlineshop.databinding.ActivityAddressBinding

class Address_activity : AppCompatActivity() {
    lateinit var binding: ActivityAddressBinding
    lateinit var adapter: Adapter_address

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        binding= ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Setup()
    }
    fun Setup(){
        val api = Api_address(applicationContext )
        api.Get_list(object : Api_address.Getlist{
            override fun list(list: ArrayList<Datamodel_address>) {
                binding.recyclerview.layoutManager= LinearLayoutManager(applicationContext)
                adapter = Adapter_address(applicationContext,list)
                binding.recyclerview.adapter=adapter
                binding.recyclerview.setHasFixedSize(true)
            }
        })
    }
}