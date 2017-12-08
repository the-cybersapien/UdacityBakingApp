package xyz.cybersapien.miriamslittlebakery.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_ingredients_list.*

import xyz.cybersapien.miriamslittlebakery.R
import xyz.cybersapien.miriamslittlebakery.adapter.IngredientAdapter
import xyz.cybersapien.miriamslittlebakery.model.Recipe
import xyz.cybersapien.miriamslittlebakery.utils.SELECTED_RECIPE

class IngredientsListFragment : Fragment() {

    lateinit var recipe: Recipe
    lateinit var ingredientAdapter: IngredientAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        recipe = arguments?.getParcelable(SELECTED_RECIPE)!!

        return inflater.inflate(R.layout.fragment_ingredients_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ingredientAdapter = IngredientAdapter(context!!, recipe.ingredients)

        ingredients_recycler_view.layoutManager = LinearLayoutManager(context!!)
        ingredients_recycler_view.adapter = ingredientAdapter
    }
}