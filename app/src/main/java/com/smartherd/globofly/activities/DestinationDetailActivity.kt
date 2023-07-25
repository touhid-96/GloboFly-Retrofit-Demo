package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.smartherd.globofly.R
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import com.smartherd.globofly.services.DestinationService
import com.smartherd.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationDetailActivity : AppCompatActivity() {

	private lateinit var toolbar: Toolbar
	private lateinit var collapsingToolbar: CollapsingToolbarLayout
	private lateinit var updateButton: Button
	private lateinit var deleteButton: Button
	private lateinit var cityName: EditText
	private lateinit var countryName: EditText
	private lateinit var description: EditText

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_detail)

		toolbar = findViewById(R.id.detail_toolbar)
		setSupportActionBar(toolbar)
		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		collapsingToolbar = findViewById(R.id.collapsing_toolbar)

		cityName = findViewById(R.id.et_city)
		countryName = findViewById(R.id.et_country)
		description = findViewById(R.id.et_description)
		updateButton = findViewById(R.id.btn_update)
		deleteButton = findViewById(R.id.btn_delete)

		val bundle: Bundle? = intent.extras

		if (bundle?.containsKey(ARG_ITEM_ID)!!) {

			val id = intent.getIntExtra(ARG_ITEM_ID, 0)

			loadDetails(id)

			initUpdateButton(id)

			initDeleteButton(id)
		}
	}

	private fun loadDetails(id: Int) {

		// To be replaced by retrofit code
		/*val destination = SampleData.getDestinationById(id)

		destination?.let {
			cityName.setText(destination.city)
			description.setText(destination.description)
			countryName.setText(destination.country)

			collapsingToolbar.title = destination.city
		}*/

		/**
		 * retrofit code
		 */
		val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
		val requestCall = destinationService.getDestination(id)

		requestCall.enqueue(object: Callback<Destination> {
			override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
				if (response.isSuccessful) {
					val destination = response.body()
					destination?.let {
						cityName.setText(destination.city)
						description.setText(destination.description)
						countryName.setText(destination.country)

						collapsingToolbar.title = destination.city
					}
				}
				else {
					Toast.makeText(this@DestinationDetailActivity, "Failed to retrieve details. Status code : " + response.code(), Toast.LENGTH_LONG).show()
				}
			}

			override fun onFailure(call: Call<Destination>, t: Throwable) {
				Toast.makeText(this@DestinationDetailActivity, "requestCall error : " + t.message, Toast.LENGTH_LONG).show()
			}
		})
	}

	private fun initUpdateButton(id: Int) {

		updateButton.setOnClickListener {

			val city = cityName.text.toString()
			val description = description.text.toString()
			val country = countryName.text.toString()

			/**
			 * offline
			 */
			/*val destination = Destination()
            destination.id = id
            destination.city = city
            destination.description = description
            destination.country = country

            SampleData.updateDestination(destination);
            finish() // Move back to DestinationListActivity*/

			/**
			 * retrofit code
			 */
			val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
			val requestCall = destinationService.updateDestination(id, city, country, description)

			requestCall.enqueue(object: Callback<Destination> {
				override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
					if (response.isSuccessful) {
						// var updatedDestination = response.body()  //this response contains updated detail also. Can be used if needed.
						finish()
						Toast.makeText(this@DestinationDetailActivity, "Updated!", Toast.LENGTH_SHORT).show()
					}
					else {
						Toast.makeText(this@DestinationDetailActivity, "Failed!", Toast.LENGTH_LONG).show()
					}
				}

				override fun onFailure(call: Call<Destination>, t: Throwable) {
					Toast.makeText(this@DestinationDetailActivity, "requestCall error : " + t.message, Toast.LENGTH_LONG).show()
				}
			})
		}
	}

	private fun initDeleteButton(id: Int) {

		deleteButton.setOnClickListener {

			/**
			 * offline
			 */
			// SampleData.deleteDestination(id)
            // finish() // Move back to DestinationListActivity

			/**
			 * retrofit code
			 */
			val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
			val requestCall = destinationService.deleteDestination(id)

			requestCall.enqueue(object: Callback<Unit> {
				override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
					if (response.isSuccessful) {
						finish()
						Toast.makeText(this@DestinationDetailActivity, "Deleted!", Toast.LENGTH_SHORT).show()
					}
					else {
						Toast.makeText(this@DestinationDetailActivity, "Failed!", Toast.LENGTH_LONG).show()
					}
				}

				override fun onFailure(call: Call<Unit>, t: Throwable) {
					Toast.makeText(this@DestinationDetailActivity, "requestCall error : " + t.message, Toast.LENGTH_LONG).show()
				}
			})
		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		val id = item.itemId
		if (id == android.R.id.home) {
			navigateUpTo(Intent(this, DestinationListActivity::class.java))
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	companion object {
		const val ARG_ITEM_ID = "item_id"
	}
}
