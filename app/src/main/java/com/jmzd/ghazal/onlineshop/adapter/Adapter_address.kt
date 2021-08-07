package com.jmzd.ghazal.onlineshop.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jmzd.ghazal.onlineshop.R
import com.jmzd.ghazal.onlineshop.cart.Activity_order
import com.jmzd.ghazal.onlineshop.dataModel.Datamodel_address

class Adapter_address (val context: Context, val list:ArrayList<Datamodel_address> )
    : RecyclerView.Adapter<Adapter_address.viewhoder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewhoder {
        val view= LayoutInflater.from(context).inflate(R.layout.items_address,parent,false)
        return viewhoder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewhoder, position: Int) {
        val data:Datamodel_address=list.get(position)
        holder.Tv_name.text="کد ملی : "+data.meli
        holder.Tv_city.text="شهر شما : "+data.city
        holder.Tv_address.text="آدرس شما : "+data.address
        holder.Tv_phone.text="شماره موبایل : "+data.phone
        holder.Tv_tell.text="شماره تلفن : "+data.tell
        holder.itemView.setOnClickListener {

            val intent= Intent(context, Activity_order::class.java)
            intent.putExtra("idaddress",data.id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
    class viewhoder(itemview: View):RecyclerView.ViewHolder(itemview){
        val Tv_name=itemview.findViewById(R.id.Tv_name) as TextView
        val Tv_city=itemview.findViewById(R.id.Tv_city) as TextView
        val Tv_address=itemview.findViewById(R.id.Tv_address) as TextView
        val Tv_phone=itemview.findViewById(R.id.Tv_phone) as TextView
        val Tv_tell=itemview.findViewById(R.id.Tv_tell) as TextView
    }
}