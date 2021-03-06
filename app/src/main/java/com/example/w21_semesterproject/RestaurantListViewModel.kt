package com.example.w21_semesterproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class RestaurantListViewModel : ViewModel() {
    private val restaurants = MutableLiveData<List<Restaurant>>()

    init {
        loadRestaurants()
    }

    /**
     * this method will "get" the restaurant list as liveData. note this
     * is different from mutableLiveData as the calling classes cannot
     * change the data
     */
    fun getRestaurants() : LiveData<List<Restaurant>> {
        return restaurants
    }

    /**
     * this method will return a list of restaurant objects. if there are
     * any changes to the db, it will automatically update the list.
     */
    private fun loadRestaurants() {
        val db = FirebaseFirestore.getInstance().collection("restaurants")
            .orderBy("name", Query.Direction.ASCENDING)

        db.addSnapshotListener { documents, exception ->
            Log.i("DB_RESPONSE", "# of elements returned: ${documents?.size()}")

            if (exception != null) {
                Log.w("DB_RESPONSE", "Listen failed", exception)
                return@addSnapshotListener
            }

            val restaurantList = ArrayList<Restaurant>()

            // .let is used to ensure the documents object is not null
            documents?.let {
                // loop over the documents returned and update the list of restaurants
                for (document in documents) {
                    val restaurant = document.toObject(Restaurant::class.java)
                    restaurantList.add(restaurant)
                }
            }
            // .value overwrites it instead of something like .add that just adds it
            restaurants.value = restaurantList
        }
    }
}