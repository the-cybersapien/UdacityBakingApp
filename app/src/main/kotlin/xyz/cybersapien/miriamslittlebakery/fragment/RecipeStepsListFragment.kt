package xyz.cybersapien.miriamslittlebakery.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
import xyz.cybersapien.miriamslittlebakery.R
import xyz.cybersapien.miriamslittlebakery.adapter.StepsAdapter
import xyz.cybersapien.miriamslittlebakery.callback.OnStepClick
import xyz.cybersapien.miriamslittlebakery.model.Recipe
import xyz.cybersapien.miriamslittlebakery.utils.SELECTED_RECIPE

/**
 * A placeholder fragment containing a simple view.
 */
class RecipeStepsListFragment : Fragment(), OnStepClick {

    private val LOG_TAG = "RecipeStepsListFragment"
    private var clickListenerCallback: OnStepClick? = null
    private lateinit var rootView: View
    private lateinit var currentRecipe: Recipe
    private lateinit var stepsAdapter: StepsAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnStepClick) {
            clickListenerCallback = context
        } else {
            throw AssertionError("Activity must implement `RecipeStepsListFragment.OnStepClick` Interface")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false)

        currentRecipe = arguments?.getParcelable(SELECTED_RECIPE)!!
        stepsAdapter = StepsAdapter(context!!, currentRecipe.steps)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Adding these here to ensure proper working of Kotlin Android Extensions
        stepsRecyclerView.adapter = stepsAdapter
        stepsRecyclerView.layoutManager = LinearLayoutManager(context!!)
    }

    override fun onDetach() {
        super.onDetach()
        clickListenerCallback = null
    }

    override fun stepClicked(stepId: Int) {
        clickListenerCallback?.stepClicked(stepId)
        Log.d(LOG_TAG, stepId.toString())
    }
}
