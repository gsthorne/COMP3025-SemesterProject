package com.example.w21_semesterproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.w21_semesterproject.databinding.ActivityRecyclerViewRestaurantListBinding

class RecyclerViewRestaurantListActivity : AppCompatActivity(), RecyclerViewAdapter.RestaurantItemListener {
    private lateinit var binding : ActivityRecyclerViewRestaurantListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model : RestaurantListViewModel by viewModels()
        model.getRestaurants().observe(this, Observer<List<Restaurant>> { restaurants ->
            var recyclerAdapter =
                RecyclerViewAdapter(this, restaurants, this)
            binding.verticalRecyclerView.adapter = recyclerAdapter
        })
    }

    override fun restaurantSelected(restaurant: Restaurant) {
        val intent = Intent(this, CommentActivity::class.java)
        intent.putExtra("restaurantId", restaurant.id)
        intent.putExtra("restaurantName", restaurant.name)
        startActivity(intent)
    }
}