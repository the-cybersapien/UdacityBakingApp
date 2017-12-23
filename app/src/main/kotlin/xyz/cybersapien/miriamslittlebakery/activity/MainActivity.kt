package xyz.cybersapien.miriamslittlebakery.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.android.volley.Response
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import xyz.cybersapien.miriamslittlebakery.R
import xyz.cybersapien.miriamslittlebakery.adapter.RecipesAdapter
import xyz.cybersapien.miriamslittlebakery.model.Recipe
import xyz.cybersapien.miriamslittlebakery.utils.SELECTED_RECIPE
import xyz.cybersapien.miriamslittlebakery.utils.fromJson
import xyz.cybersapien.miriamslittlebakery.utils.getRecipeData
import java.io.IOException

class MainActivity : AppCompatActivity(), RecipesAdapter.OnRecipeItemClick {

    private val LOG_TAG = MainActivity::class.simpleName
    private val DATA_PREFERENCE_KEY = "DATA KEY"
    private lateinit var toolbar: Toolbar
    private lateinit var prefs: SharedPreferences
    private val recipesList = ArrayList<Recipe>()
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        prefs = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        recipesAdapter = RecipesAdapter(this, recipesList)
        val colsCount = resources.getInteger(R.integer.cols_count)
        recipesRecyclerView.layoutManager = GridLayoutManager(this, colsCount)
        recipesRecyclerView.adapter = recipesAdapter

        showLoading()

        if (prefs.contains(DATA_PREFERENCE_KEY)) {
            parseDataFromJson(prefs.getString(DATA_PREFERENCE_KEY, ""), true)
        } else {
            try {
                getRecipeData(this, Response.Listener { response ->
                    parseDataFromJson(response, false)
                })
            } catch (e: IOException) {
                showError(e.localizedMessage)
            }
        }
    }

    private fun parseDataFromJson(string: String, isSaved: Boolean) {
        val gsonEngine = GsonBuilder()
                .create()
        val recipes = gsonEngine.fromJson<ArrayList<Recipe>>(string)
        if (!isSaved)
            prefs.edit()
                    .putString(DATA_PREFERENCE_KEY, string).apply()
        showList(recipes)
    }

    /**
     * This function acts as a delegate between the RecyclerView list and the recipes
     */
    override fun onClick(position: Int) {
        val intent = Intent(this, RecipeDetailActivity::class.java)
        intent.putExtra(SELECTED_RECIPE, recipesList[position])
        startActivity(intent)
    }

    private fun showList(recipes: ArrayList<Recipe>) {
        recipesList.clear()
        recipesList.addAll(recipes)
        recipesAdapter.notifyDataSetChanged()
        recipesRecyclerView.visibility = View.VISIBLE
        loading_view.visibility = View.GONE
        error_view.visibility = View.GONE
    }

    private fun showLoading() {
        recipesRecyclerView.visibility = View.GONE
        error_view.visibility = View.GONE
        loading_view.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        recipesRecyclerView.visibility = View.GONE
        loading_view.visibility = View.GONE
        error_view.visibility = View.VISIBLE
        error_text_view.text = errorMessage
    }
}
