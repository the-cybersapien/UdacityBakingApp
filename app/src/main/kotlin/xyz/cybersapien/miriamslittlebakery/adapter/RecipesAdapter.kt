package xyz.cybersapien.miriamslittlebakery.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import xyz.cybersapien.miriamslittlebakery.R
import xyz.cybersapien.miriamslittlebakery.model.Recipe

/**
 * Created by ogcybersapien on 5/12/17.
 */
class RecipesAdapter(
        private val context: Context,
        private val recipes: ArrayList<Recipe>
) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    var itemClickCallback: OnRecipeItemClick

    init {
        if (context is OnRecipeItemClick)
            itemClickCallback = context
        else
            throw IllegalArgumentException("User must implement `RecipeAdapter.OnRecipeItemClick`")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRecipe = recipes[position]

        holder.recipeNameView.text = currentRecipe.name
        if (currentRecipe.image.isNotEmpty())
            Picasso.with(context)
                    .load(currentRecipe.image)
                    .placeholder(R.drawable.placeholder).centerCrop()
                    .into(holder.recipeImageView)
        else
            holder.recipeImageView.setImageResource(R.drawable.placeholder)

        holder.recipeServingView.text = currentRecipe.servings.toString()

        holder.itemView.setOnClickListener { itemClickCallback.onClick(position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int = recipes.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeImageView = view.findViewById<ImageView>(R.id.recipe_image)
        val recipeNameView = view.findViewById<TextView>(R.id.recipe_name)
        val recipeServingView = view.findViewById<TextView>(R.id.recipe_servings)
    }

    interface OnRecipeItemClick {
        fun onClick(position: Int)
    }
}