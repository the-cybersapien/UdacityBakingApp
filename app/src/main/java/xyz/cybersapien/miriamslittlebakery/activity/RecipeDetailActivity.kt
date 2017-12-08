package xyz.cybersapien.miriamslittlebakery.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recipe_detail.*
import kotlinx.android.synthetic.main.content_recipe_detail.*
import xyz.cybersapien.miriamslittlebakery.R
import xyz.cybersapien.miriamslittlebakery.callback.OnStepClick
import xyz.cybersapien.miriamslittlebakery.fragment.IngredientsListFragment
import xyz.cybersapien.miriamslittlebakery.fragment.RecipeStepsListFragment
import xyz.cybersapien.miriamslittlebakery.fragment.StepDetailFragment
import xyz.cybersapien.miriamslittlebakery.model.Recipe
import xyz.cybersapien.miriamslittlebakery.utils.*

class RecipeDetailActivity : AppCompatActivity(), OnStepClick {

    val RECIPE_LIST_TAG = "Recipe_list_fragment"
    val STEP_DETAIL_TAG = "step_detail_fragment"
    val LOG_TAG = "RecipeDetailActivity"
    lateinit var currentRecipe: Recipe
    var isTwoPane = false
    lateinit var recipeListFragment: RecipeStepsListFragment

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

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SELECTED_RECIPE, currentRecipe)
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
