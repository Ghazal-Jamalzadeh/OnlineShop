package com.jmzd.ghazal.onlineshop.api

interface Config {
    val Baseurl: String get() = "http://192.168.1.105/shop/"
}
// در جاوا به این گت نیاز نداشتیم و مستقیم = میذاشتیم