package com.jmzd.ghazal.onlineshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmzd.ghazal.onlineshop.R
import com.jmzd.ghazal.onlineshop.dataModel.Datamodel_pay

class Adapter_pay (val context: Context, val list:ArrayList<Datamodel_pay>, val getlink :Get_link ) :
    RecyclerView.Adapter<Adapter_pay.viewhoder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewhoder {
        val view= LayoutInflater.from(context).inflate(R.layout.items_pay,parent,false)
        return viewhoder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewhoder, position: Int) {
        val data:Datamodel_pay=list.get(position)
        Glide.with(context).load(data.image).into(holder.Im_post)
        holder.Tv_title.text=data.title
        getlink.Str(data.link,data.title)
        holder.itemView.setOnClickListener {
            getlink.Str(data.link,data.title)
        }

    }
    interface Get_link{
        fun Str(link:String,title:String)
    }
    class viewhoder(itemview: View):RecyclerView.ViewHolder(itemview){
        val Im_post=itemview.findViewById(R.id.Im_post) as ImageView
        val Tv_title=itemview.findViewById(R.id.Tv_title) as TextView

    }
}