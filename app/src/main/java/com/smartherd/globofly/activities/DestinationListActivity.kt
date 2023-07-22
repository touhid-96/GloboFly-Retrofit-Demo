package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.smartherd.globofly.R
import com.smartherd.globofly.helpers.DestinationAdapter
import com.smartherd.globofly.helpers.SampleData

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
		destinyRecyclerView.adapter = DestinationAdapter(SampleData.DESTINATIONS)
    }
}
