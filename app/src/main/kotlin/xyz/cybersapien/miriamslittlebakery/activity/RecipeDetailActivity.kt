package xyz.cybersapien.miriamslittlebakery.activity

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_recipe_detail.*
import kotlinx.android.synthetic.main.content_recipe_detail.*
import xyz.cybersapien.miriamslittlebakery.R
import xyz.cybersapien.miriamslittlebakery.callback.OnStepClick
import xyz.cybersapien.miriamslittlebakery.fragment.IngredientsListFragment
import xyz.cybersapien.miriamslittlebakery.fragment.RecipeStepsListFragment
import xyz.cybersapien.miriamslittlebakery.fragment.StepDetailFragment
import xyz.cybersapien.miriamslittlebakery.model.Recipe
import xyz.cybersapien.miriamslittlebakery.utils.*
import xyz.cybersapien.miriamslittlebakery.widget.RecipeListWidgetProvider


class RecipeDetailActivity : AppCompatActivity(), OnStepClick {

    val RECIPE_LIST_TAG = "Recipe_list_fragment"
    val STEP_DETAIL_TAG = "step_detail_fragment"
    val LOG_TAG = "RecipeDetailActivity"
    private lateinit var currentRecipe: Recipe
    private var isTwoPane = false
    private lateinit var recipeListFragment: RecipeStepsListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent != null)
            currentRecipe = intent.getParcelableExtra(SELECTED_RECIPE)
        else
            currentRecipe = savedInstanceState?.getParcelable(SELECTED_RECIPE)!!

        supportActionBar?.title = currentRecipe.name

        recipeListFragment = RecipeStepsListFragment()
        val fragmentDataBundle = Bundle()
        fragmentDataBundle.putParcelable(SELECTED_RECIPE, currentRecipe)
        recipeListFragment.arguments = fragmentDataBundle
        supportFragmentManager.beginTransaction()
                .replace(R.id.recipe_steps_fragment_container, recipeListFragment, RECIPE_LIST_TAG).commit()

        isTwoPane = step_detail_fragment_container != null

        if (isTwoPane) {
            replaceStepFragment(INGREDIENT_STEP)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SELECTED_RECIPE, currentRecipe)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recipe_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.action_save_widget -> {
                    val sharedPreference = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
                    val jsonRecipe = Gson().toJson(currentRecipe)
                    sharedPreference.edit()
                            .putString(SAVED_RECIPE, jsonRecipe)
                            .apply()

                    val intent = Intent(this, RecipeListWidgetProvider::class.java)
                    intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                    val ids = AppWidgetManager.getInstance(application)
                            .getAppWidgetIds(ComponentName(application, RecipeListWidgetProvider::class.java))
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
                    sendBroadcast(intent)
                    true
                }
                else ->
                    super.onOptionsItemSelected(item)
            }

    override fun stepClicked(stepId: Int) {
        if (isTwoPane)
            replaceStepFragment(stepId)
        else
            launchDetailActivity(stepId)
    }

    private fun launchDetailActivity(stepId: Int) {

        val detailIntent = Intent(this, StepDetailActivity::class.java)
        when (stepId) {
            INGREDIENT_STEP -> {
                detailIntent.putExtra(DETAIL_TYPE, TYPE_INGREDIENT)
            }
            else -> {
                detailIntent.putExtra(DETAIL_TYPE, TYPE_STEP)
                detailIntent.putExtra(STEP_ID, stepId)
            }
        }
        detailIntent.putExtra(SELECTED_RECIPE, currentRecipe)
        startActivity(detailIntent)
    }

    private fun replaceStepFragment(stepId: Int) {
        val fragment: Fragment
        val dataBundle = Bundle()
        when (stepId) {
            INGREDIENT_STEP -> {
                fragment = IngredientsListFragment()
                dataBundle.putParcelable(SELECTED_RECIPE, currentRecipe)
                fragment.arguments = dataBundle
            }
            else -> {
                fragment = StepDetailFragment()
                dataBundle.putParcelable(SELECTED_STEP, currentRecipe.steps[stepId])
                fragment.arguments = dataBundle
            }
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.step_detail_fragment_container, fragment)
                .commit()
    }

}
