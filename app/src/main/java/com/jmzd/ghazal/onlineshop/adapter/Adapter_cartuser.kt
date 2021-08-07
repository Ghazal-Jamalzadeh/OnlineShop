package com.jmzd.ghazal.onlineshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.jmzd.ghazal.onlineshop.R
import com.jmzd.ghazal.onlineshop.api.Config
import com.jmzd.ghazal.onlineshop.dataModel.Datamodel_cart


class Adapter_cartuser(val context: Context, val userid:String, val list:ArrayList<Datamodel_cart>, val getchange: Get_change)
    :RecyclerView.Adapter<Adapter_cartuser.viewhoder>(),
    Config {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewhoder {
        val view=LayoutInflater.from(context).inflate(R.layout.items_cart,parent,false)
        return viewhoder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewhoder, position: Int) {
        val data:Datamodel_cart=list.get(position)
        Glide.with(context).load(data.imageurl).into(holder.Im_post)
        holder.Tv_title.text=data.title
        holder.Tv_price.text=data.price
        holder.Tv_count.text=data.count

        holder.Im_add.setOnClickListener {
            Get_server("add",data.idproduct,holder.Tv_price,holder.Tv_count)
        }

        holder.Im_man.setOnClickListener {
            Get_server("m",data.idproduct,holder.Tv_price,holder.Tv_count)
        }

        holder.Tv_del.setOnClickListener {

            val json= JsonObjectRequest(Baseurl+"del_cart.php?idcart="+data.id,null,
                 { response ->
                    val check=response.getString("status")
                    if(check.equals("ok")){
                        list.removeAt(position)
                        notifyItemRemoved(position)
                        getchange.getchange()
                    }
                }
                ,
                Response.ErrorListener {

                })

            json.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
            Volley.newRequestQueue(context).add(json)

        }

    }

    fun Get_server(url:String,idproduct:String,tv_price:TextView,tv_count:TextView){

        val json= JsonObjectRequest(Baseurl+"Addcart.php?check="+url+"&user="+userid+"&product="+idproduct+"&count=1"
            ,null,
             { response ->
                val check=response.getString("status")
                if(check.equals("ok")){
                    val price_post=response.getString("price_post")
                    val count=response.getString("count")
                    tv_count.text=count
                    tv_price.text=price_post
                    getchange.getchange()

                }
            }
            ,
            Response.ErrorListener {

            })

        json.setRetryPolicy(DefaultRetryPolicy(10000,Int.MAX_VALUE, Float.MAX_VALUE))
        Volley.newRequestQueue(context).add(json)

    }
    interface Get_change{
        fun getchange()
    }
    class viewhoder(itemview:View):RecyclerView.ViewHolder(itemview){
        val Im_post=itemview.findViewById(R.id.Im_post) as ImageView
        val Im_add=itemview.findViewById(R.id.Im_add) as ImageView
        val Im_man=itemview.findViewById(R.id.Im_man) as ImageView
        val Tv_count=itemview.findViewById(R.id.Tv_count) as TextView
        val Tv_title=itemview.findViewById(R.id.Tv_title) as TextView
        val Tv_price=itemview.findViewById(R.id.Tv_price) as TextView
        val Tv_del=itemview.findViewById(R.id.Tv_del) as TextView
    }
}