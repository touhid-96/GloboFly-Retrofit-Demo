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
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import com.smartherd.globofly.services.DestinationService
import com.smartherd.globofly.services.ServiceBuilder
import com.smartherd.globofly.services.ServiceBuilderTest
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
//		don't remove the comment
//		destinyRecyclerView.adapter = DestinationAdapter(SampleData.DESTINATIONS)
		val destinationService = ServiceBuilderTest.buildService(DestinationService::class.java)
		val requestCall = destinationService.getDestinationList()

		requestCall.enqueue(object : Callback<List<Destination>> {
			override fun onResponse(call: Call<List<Destination>>, response: Response<List<Destination>>) {
				if (response.isSuccessful) {
					val destinationList: List<Destination>? = response.body()
					if (destinationList != null) {
						destinyRecyclerView.adapter = DestinationAdapter(destinationList)
					} else {
						Toast.makeText(this@DestinationListActivity, "Empty Response!", Toast.LENGTH_LONG).show()
					}
				} else {
					Toast.makeText(this@DestinationListActivity, "Status code : " + response.code(), Toast.LENGTH_LONG).show()
				}
			}

			override fun onFailure(call: Call<List<Destination>>, t: Throwable) {
				Toast.makeText(this@DestinationListActivity, "Failed to fetch data!", Toast.LENGTH_LONG).show()
				t.printStackTrace() // Print the complete error message in Logcat

				// Print call: Call<List<Destination>> information
				println("Failed call: $call")
			}
		})
	}
}
