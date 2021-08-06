package com.jmzd.ghazal.onlineshop.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmzd.ghazal.onlineshop.MainActivity
import com.jmzd.ghazal.onlineshop.R
import com.jmzd.ghazal.onlineshop.auth.Login_user
import com.jmzd.ghazal.onlineshop.dataModel.Datamodel_shop
import com.jmzd.ghazal.onlineshop.more.More_activity


class Adapter_recyclerview_shop(val context: Context, val list:List<Datamodel_shop> ) :
    RecyclerView.Adapter<Adapter_recyclerview_shop.viewhoder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewhoder {
        val view= LayoutInflater.from(context).inflate(R.layout.items_shop,parent,false)
        return viewhoder(view)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewhoder, position: Int) { // foreach
        val data:Datamodel_shop=list.get(position)
        Glide.with(context).load(data.imageurl).into(holder.Im_post)
        holder.Tv_title.text=data.title
        holder.Tv_date.text="تاریخ پست: "+data.date
        holder.Tv_view.text="تعداد بازدید: "+data.view
        holder.Tv_price.text=data.price

        holder.itemView.setOnClickListener {
            val intent= Intent(context, More_activity::class.java)
            intent.putExtra("id",data.id)
            intent.putExtra("title",data.title)
            intent.putExtra("imageurl",data.imageurl)
            intent.putExtra("date",data.date)
            intent.putExtra("view",data.view)
            intent.putExtra("des",data.des)
            intent.putExtra("price",data.price)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            //mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TOP
               Log.d("ghazalError","you clicked on $position and ${data.title} ")
        }

    }
    class viewhoder(itemview: View):RecyclerView.ViewHolder(itemview){
        val Im_post=itemview.findViewById(R.id.Im_post) as ImageView
        val Tv_title=itemview.findViewById(R.id.Tv_title) as TextView
        val Tv_date=itemview.findViewById(R.id.Tv_date) as TextView
        val Tv_view=itemview.findViewById(R.id.Tv_view) as TextView
        val Tv_price=itemview.findViewById(R.id.Tv_price) as TextView
    }
}