package com.jmzd.ghazal.onlineshop

import android.content.Context

class Userid (val context: Context) {

    fun Getuser():String{
        val sharedPreferences=context.getSharedPreferences("user",0)
        val struser=sharedPreferences.getString("id",null)
        return struser.toString()
    }
}
//باید حتما به context دسترسی داشته باشیم. چون این اکتیویتی نیست و کلاس است.