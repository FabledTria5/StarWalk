package com.example.nasatoday.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.nasatoday.R

class ThemesAdapter(items: ArrayList<Drawable?>) : RecyclerView.Adapter<ThemesAdapter.ThemesViewHolder>() {

    private val themes = items

    inner class ThemesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val themeImage = itemView.findViewById<ImageView>(R.id.ivThemeImage)

        fun bind(theme: Drawable?) {
            themeImage.setImageDrawable(theme)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ThemesViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.theme_item, parent, false)
        )

    override fun onBindViewHolder(holder: ThemesViewHolder, position: Int) =
        holder.bind(themes[position])

    override fun getItemCount() = themes.count()

}