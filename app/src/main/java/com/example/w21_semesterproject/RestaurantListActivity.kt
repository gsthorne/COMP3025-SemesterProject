package com.example.w21_semesterproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.w21_semesterproject.databinding.ActivityRestaurantListBinding
import com.google.firebase.firestore.FirebaseFirestore
import androidx.activity.viewModels
import androidx.lifecycle.Observer

class RestaurantListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRestaurantListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model : RestaurantListViewModel by viewModels()
        // my install of android studio needs Observer<List<Restaurant>> because it won't
        // automatically recognize the type for whatever reason
        model.getRestaurants().observe( this, Observer<List<Restaurant>>{ restaurantList ->
            binding.linearLayout.removeAllViews()

            for (restaurant in restaurantList) {
                val textView = TextView(this)
                textView.text = restaurant.name
                textView.textSize = 20f
                binding.linearLayout.addView(textView)
            }
        }
        )
    }
}