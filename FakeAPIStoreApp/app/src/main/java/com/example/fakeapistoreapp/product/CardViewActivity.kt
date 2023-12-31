package com.example.fakeapistoreapp.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.fakeapistoreapp.R
import com.example.fakeapistoreapp.databinding.ActivityCardViewBinding
import com.squareup.picasso.Picasso

class CardViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_view)

        var data = intent
        var title = data.getStringExtra("title")
        var description = data.getStringExtra("description")
        var price = data.getDoubleExtra("price",0.0)
        var category = data.getStringExtra("category")
        var image = data.getStringExtra("image")
        var rate = data.getDoubleExtra("rating",0.0)
        var count = data.getIntExtra("ratingCount",0)


//        binding.ratingBar.rating = rating.toFloat()

        binding.showRating.text = "Rating     -   $rate"
        binding.showRating.text = "Total Rating   -    $count"
        binding.showCategory.text = "Category     -   $category"
        binding.showTitle.text = "Title    -    $title"
        binding.showPrice.text = "Price    - $$price"
        binding.showDescription.text = "Description    -  $description"

        Picasso.get().load("$image").into(binding.showImage)

    }
}