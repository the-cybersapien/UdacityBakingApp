package xyz.cybersapien.miriamslittlebakery

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import xyz.cybersapien.miriamslittlebakery.activity.MainActivity
import xyz.cybersapien.miriamslittlebakery.adapter.RecipesAdapter
import xyz.cybersapien.miriamslittlebakery.utils.SELECTED_RECIPE

@RunWith(AndroidJUnit4::class)
class RecipeGridClickIntentTest {
    @Rule
    @JvmField
    val mainActivityTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun clickRecyclerViewItem_OpenSecondActivity() {


        // Perform a click Action in the App's recipe Grid
        onView(withId(R.id.recipesRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecipesAdapter.ViewHolder>(3, click()))

        // Verify that the intent sent contains a recipe for the second Activity to process
        intended(allOf(
                IntentMatchers.hasExtraWithKey(SELECTED_RECIPE),
                IntentMatchers.isInternal()
        ))
    }
}