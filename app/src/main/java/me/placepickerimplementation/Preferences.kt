package me.placepickerimplementation

interface Preferences {

    fun putDouble(key: String, value: Double)

    fun getDouble(key: String, defaultValue: Double): Double
}
