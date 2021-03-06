package com.example.w21_semesterproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.w21_semesterproject.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveRestaurantButton.setOnClickListener {
            // check to make sure the fields are populated
            if (binding.restaurantEditText.text.toString().isNotEmpty() && binding.ratingsSpinner.selectedItemPosition>0) {
                val restaurant = Restaurant()
                restaurant.name = binding.restaurantEditText.text.toString()
                restaurant.rating = binding.ratingsSpinner.selectedItem.toString().toInt()

                val db = FirebaseFirestore.getInstance().collection("restaurants")
                restaurant.id = db.document().id

                // our restaurant has everything it needs. we can now push it to the db.
                db.document(restaurant.id!!).set(restaurant)
                        .addOnSuccessListener {
                            // show confirmation and clear fields
                            Toast.makeText(this, "Restaurant added.", Toast.LENGTH_LONG).show()
                            binding.restaurantEditText.setText("")
                            binding.ratingsSpinner.setSelection(0)

                        }
                        .addOnFailureListener {
                            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                        }
            } else {
                Toast.makeText(this, "Restaurant name and rating required", Toast.LENGTH_LONG).show()
            }
        }
    }
}