package xyz.cybersapien.miriamslittlebakery.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import xyz.cybersapien.miriamslittlebakery.R
import xyz.cybersapien.miriamslittlebakery.model.Ingredient

/**
 * Created by ogcybersapien on 8/12/17.
 */
class IngredientAdapter(val context: Context, private val ingredientsList: ArrayList<Ingredient>) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentIngredient = ingredientsList[position]

        holder.ingredientName.text = currentIngredient.ingredient
        holder.quantityView.text = currentIngredient.quantity.toString()
        holder.quantityTypeView.text = currentIngredient.measure
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quantityView = itemView.findViewById<TextView>(R.id.quantity_text_view)
        val quantityTypeView = itemView.findViewById<TextView>(R.id.quantity_type_text_view)
        val ingredientName = itemView.findViewById<TextView>(R.id.ingredient_name_text_view)
    }
}