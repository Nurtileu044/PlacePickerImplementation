package me.placepickerimplementation

import android.R
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.*
import com.yandex.mapkit.map.Map
import com.yandex.runtime.image.ImageProvider
import me.placepickerimplementation.databinding.ActivityMapBinding
import java.io.IOException
import java.util.*


class MapActivity : AppCompatActivity(), InputListener {

    private lateinit var binding: ActivityMapBinding
    private var placemark: PlacemarkMapObject? = null
    private var location: List<Double> = emptyList()
    private lateinit var city: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("e31a982a-b9f7-4220-ab44-b070288587da")
        MapKitFactory.initialize(this)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lat = intent.getDoubleExtra("Latitude", 0.0)
        val long = intent.getDoubleExtra("Longitude", 0.0)
        location = listOf(lat, long)

        addPlacemark(Point(location[0], location[1]))

        with(binding) {
            toolbar.setNavigationOnClickListener {

                val intent = Intent()
                intent.putExtra("Latitude", location[0])
                intent.putExtra("Longitude", location[1])
                intent.putExtra("Address", city)
                setResult(REQUEST_CODE, intent)
                finish()
            }

            mapView.map.addInputListener(this@MapActivity)
        }

    }

    override fun onStart() {
        binding.mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        binding.mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onMapTap(map: Map, point: Point) {
        addPlacemark(point)

        val geocoder = Geocoder(this, Locale.getDefault())
        city = try {
            val addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val returnedAddress = addresses[0]
                returnedAddress.countryName + ", " + returnedAddress.adminArea.toString() + ", " + returnedAddress.featureName.toString()
            } else {
                "Error"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            "Error"
        }

        Toast.makeText(
            this@MapActivity,
            "LONGITUDE: ${point.longitude}, LATITUDE: ${point.latitude}, ADDRESS: $city",
            Toast.LENGTH_SHORT
        ).show()
        location = listOf(point.latitude, point.longitude)
    }

    override fun onMapLongTap(map: Map, point: Point) {

    }

    private fun addPlacemark(point: Point) {
        with(binding) {
            placemark?.let { mapView.mapWindow.map.mapObjects.remove(it) }
            val pinIcon =
                ImageProvider.fromResource(this@MapActivity, R.drawable.arrow_down_float)
            placemark = mapView.mapWindow.map.mapObjects.addPlacemark(point, pinIcon)
        }
    }
}