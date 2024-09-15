package com.example.contextmonitoringapp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView

class SymptomAdapter(private val symptoms: List<Symptom>) : RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_symptom, parent, false)
        return SymptomViewHolder(view)
    }

    override fun onBindViewHolder(holder: SymptomViewHolder, position: Int) {
        val symptom = symptoms[position]
        // Set the actual symptom name on the checkbox
        holder.checkboxSymptom.text = symptom.name
        holder.checkboxSymptom.isChecked = symptom.isSelected
        holder.ratingSymptom.rating = symptom.rating.toFloat()

        // Handle checkbox selection and rating changes
        holder.checkboxSymptom.setOnCheckedChangeListener { _, isChecked ->
            symptom.isSelected = isChecked
        }

        // Handle rating changes
        holder.ratingSymptom.setOnRatingBarChangeListener { _, rating, _ ->
            symptom.rating = rating.toInt()

            // Automatically check the checkbox if the rating is greater than 0
            if (rating > 0) {
                symptom.isSelected = true
                holder.checkboxSymptom.isChecked = true
            } else {
                // If the rating is 0, uncheck the checkbox
                symptom.isSelected = false
                holder.checkboxSymptom.isChecked = false
            }
        }
    }

    override fun getItemCount(): Int {
        return symptoms.size
    }

    class SymptomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkboxSymptom: CheckBox = itemView.findViewById(R.id.checkbox_symptom)
        val ratingSymptom: RatingBar = itemView.findViewById(R.id.rating_symptom)
    }
}

