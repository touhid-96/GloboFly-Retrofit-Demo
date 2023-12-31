package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.smartherd.globofly.R
import com.smartherd.globofly.helpers.DestinationAdapter
import com.smartherd.globofly.models.Destination
import com.smartherd.globofly.services.DestinationService
import com.smartherd.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationListActivity : AppCompatActivity() {

	private lateinit var toolbar: Toolbar
	private lateinit var floatingActionButton: FloatingActionButton
	private lateinit var destinyRecyclerView: RecyclerView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_list)

		toolbar = findViewById(R.id.m_toolbar)
		setSupportActionBar(toolbar)
		toolbar.title = title

		destinyRecyclerView = findViewById(R.id.destiny_recycler_view)
		floatingActionButton = findViewById(R.id.fab)
		floatingActionButton.setOnClickListener {
			val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
			startActivity(intent)
		}
	}

	override fun onResume() {
		super.onResume()

		loadDestinations()
	}

	private fun loadDestinations() {

        // To be replaced by retrofit code
		// destinyRecyclerView.adapter = DestinationAdapter(SampleData.DESTINATIONS)

		/**
		 * retrofit code
		 */
		val filter = HashMap<String, String>()
		// filter["country"] = "India"
		// filter["count"] = "1"

		val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
		val requestCall = destinationService.getDestinationList(filter)

		requestCall.enqueue(object: Callback<List<Destination>> {
			override fun onResponse(call: Call<List<Destination>>, response: Response<List<Destination>>) {
				if (response.isSuccessful) {
					val destinationList: List<Destination> = response.body()!!
 					destinyRecyclerView.adapter = DestinationAdapter(destinationList)
				}
				else {
					Toast.makeText(this@DestinationListActivity, "Failed to retrieve items. Status code : "+ response.code(), Toast.LENGTH_LONG).show()
				}
			}

			override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
				Toast.makeText(this@DestinationListActivity, "requestCall error : " + t.message, Toast.LENGTH_LONG).show()
			}
		})
    }
}
