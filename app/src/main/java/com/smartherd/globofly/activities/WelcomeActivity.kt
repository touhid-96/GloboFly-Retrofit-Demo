package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.smartherd.globofly.R
import com.smartherd.globofly.services.DestinationService
import com.smartherd.globofly.services.MessageService
import com.smartherd.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : AppCompatActivity() {

	private lateinit var message: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_welcome)

		message = findViewById(R.id.message_tv)
		// To be replaced by retrofit code
		// message.text = "Black Friday! Get 50% cash back on saving your first spot."

		/**
		 * get data from a different server
		 */
		// val messageService = ServiceBuilder.buildService(MessageService::class.java)
		// val requestCall = messageService.getMessages("http://10.0.2.2:7000/messages")

		/**
		 * retrofit code
		 */
		val messageService = ServiceBuilder.buildService(DestinationService::class.java)
		val requestCall = messageService.getMessages()

		requestCall.enqueue(object: Callback<String> {
			override fun onResponse(call: Call<String>, response: Response<String>) {
				if (response.isSuccessful) {
					val msg = response.body()
					message.text = msg
				}
				else {
					Toast.makeText(this@WelcomeActivity,
						"Failed to retrieve. Status code : "+ response.code(), Toast.LENGTH_LONG).show()
				}
			}

			override fun onFailure(call: Call<String>, t: Throwable) {
				Toast.makeText(this@WelcomeActivity,
					"requestCall error : "+ t.message, Toast.LENGTH_LONG).show()
			}
		})
	}

	fun getStarted(view: View) {
		val intent = Intent(this, DestinationListActivity::class.java)
		startActivity(intent)
		finish()
	}
}
