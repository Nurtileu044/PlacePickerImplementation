package me.placepickerimplementation

class PreferencesImpl(
    private val preferences: java.util.prefs.Preferences
) : Preferences {

    companion object {
        const val PREF_KEY_LONGITUDE = "PREF_KEY_LONGITUDE"
        const val PREF_KEY_LATITUDE = "PREF_KEY_LATITUDE"
    }

    override fun putDouble(key: String, value: Double) {
        preferences.putDouble(key, value)
    }

    override fun getDouble(key: String, defaultValue: Double): Double {
        return preferences.getDouble(key, defaultValue)
    }

}