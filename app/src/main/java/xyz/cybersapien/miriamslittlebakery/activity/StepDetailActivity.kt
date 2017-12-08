package xyz.cybersapien.miriamslittlebakery.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_step_detail.*
import xyz.cybersapien.miriamslittlebakery.R
import xyz.cybersapien.miriamslittlebakery.fragment.IngredientsListFragment
import xyz.cybersapien.miriamslittlebakery.fragment.StepDetailFragment
import xyz.cybersapien.miriamslittlebakery.model.Recipe
import xyz.cybersapien.miriamslittlebakery.utils.*


class StepDetailActivity : AppCompatActivity() {

    val STEP_DETAIL_TAG = "step_detail_fragment"
    lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recipe = intent.getParcelableExtra(SELECTED_RECIPE)

        supportActionBar?.title = recipe.name

        val fragType = intent.getIntExtra(DETAIL_TYPE, TYPE_STEP)

        if (fragType == TYPE_STEP) {
            initStep()
        } else {
            initIngredients()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                android.R.id.home -> {
                    // The Parent Activity must not be recreated, but re-initialized from the back stack
                    // This is to ensure proper transition of the Activity as and when required
                    val h = NavUtils.getParentActivityIntent(this)
                    h!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    NavUtils.navigateUpTo(this, h)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    private fun initStep() {
        val stepId = intent.getIntExtra(STEP_ID, -1)

        val stepDetailFragment = StepDetailFragment()
        val fragDataBundle = Bundle()
        fragDataBundle.putParcelable(SELECTED_STEP, recipe.steps[stepId])
        stepDetailFragment.arguments = fragDataBundle

        supportFragmentManager.beginTransaction()
                .replace(R.id.step_detail_fragment_container, stepDetailFragment, STEP_DETAIL_TAG)
                .commit()
    }

    private fun initIngredients() {
        val ingredientsListFragment = IngredientsListFragment()
        val fragDataBundle = Bundle()
        fragDataBundle.putParcelable(SELECTED_RECIPE, recipe)
        ingredientsListFragment.arguments = fragDataBundle

        supportFragmentManager.beginTransaction()
                .replace(R.id.step_detail_fragment_container, ingredientsListFragment, STEP_DETAIL_TAG)
                .commit()
    }
}
