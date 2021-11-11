package me.placepickerimplementation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.placepickerimplementation.databinding.ActivityMainBinding

const val REQUEST_CODE = 1

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            changeLocationButton.setOnClickListener {
                val intent = Intent(this@MainActivity, MapActivity::class.java)
                intent.putExtra("Latitude", latitude)
                intent.putExtra("Longitude", longitude)

                startActivityForResult(
                    intent,
                    REQUEST_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            with(binding) {
                if (data?.getDoubleExtra(
                        "Latitude",
                        0.0
                    ) != null || data?.getDoubleExtra("Longitude", 0.0) != null
                ) {
                    latitudeTextView.text = "LATITUDE: ${data?.getDoubleExtra("Latitude", 0.0)}"
                    longitudeTextView.text = "LONGITUDE: ${data?.getDoubleExtra("Longitude", 0.0)}"

                    longitude = data.getDoubleExtra("Longitude", 0.0)
                    latitude = data.getDoubleExtra("Latitude", 0.0)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}