package com.example.fakeapistoreapp.product

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakeapistoreapp.databinding.CustomProductListBinding
import com.squareup.picasso.Picasso


class RecyclerViewApadter(private var context: Context, var list: ProductList,var onItemClick: onItemRecyclerClickListener): RecyclerView.Adapter<RecyclerViewApadter.MyViewHolder>() {



    inner class MyViewHolder(val binding: CustomProductListBinding?): RecyclerView.ViewHolder(binding!!.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = CustomProductListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = list[position]

        Picasso.get().load(product.image).into(holder.binding!!.tvImage)
        holder.binding!!.tvTitle.text = "Title: ${product.title}"
        holder.binding!!.tvCatrgory.text = "Category: ${product.category}"

        holder.itemView.setOnClickListener {
            onItemClick.onItemClick(position, list)
        }

    }

}