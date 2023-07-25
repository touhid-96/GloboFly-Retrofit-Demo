package com.smartherd.globofly.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.smartherd.globofly.R
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import com.smartherd.globofly.services.DestinationService
import com.smartherd.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationCreateActivity : AppCompatActivity() {

	private lateinit var toolbar: Toolbar
	private lateinit var addButton: Button
	private lateinit var cityName: EditText
	private lateinit var countryName: EditText
	private lateinit var description: EditText

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_create)

		toolbar = findViewById(R.id.m_toolbar)
		setSupportActionBar(toolbar)

		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		cityName = findViewById(R.id.et_city)
		countryName = findViewById(R.id.et_country)
		description = findViewById(R.id.et_description)

		addButton = findViewById(R.id.btn_add)
		addButton.setOnClickListener {
			val newDestination = Destination()
			newDestination.city = cityName.text.toString()
			newDestination.description = description.text.toString()
			newDestination.country = countryName.text.toString()

			// To be replaced by retrofit code
			// SampleData.addDestination(newDestination)
            // finish() // Move back to DestinationListActivity

			/**
			 * retrofit code
			 */
			val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
			val requestCall = destinationService.addDestination(newDestination)

			requestCall.enqueue(object: Callback<Destination> {
				override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
					if (response.isSuccessful) {
						finish()
						Toast.makeText(this@DestinationCreateActivity, "Added", Toast.LENGTH_SHORT).show()
					}
					else {
						Toast.makeText(this@DestinationCreateActivity, "Failed!", Toast.LENGTH_LONG).show()
					}
				}

				override fun onFailure(call: Call<Destination>, t: Throwable) {
					Toast.makeText(this@DestinationCreateActivity, "requestCall error : " + t.message, Toast.LENGTH_LONG).show()
				}

			})
		}
	}
}
