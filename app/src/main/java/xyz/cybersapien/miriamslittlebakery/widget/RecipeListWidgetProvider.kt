package xyz.cybersapien.miriamslittlebakery.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.google.gson.Gson
import xyz.cybersapien.miriamslittlebakery.R
import xyz.cybersapien.miriamslittlebakery.model.Recipe
import xyz.cybersapien.miriamslittlebakery.utils.PACKAGE_NAME
import xyz.cybersapien.miriamslittlebakery.utils.SAVED_RECIPE
import xyz.cybersapien.miriamslittlebakery.utils.fromJson

/**
 * Implementation of App Widget functionality.
 */
class RecipeListWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent) {
        super.onReceive(context, intent)
        recipe = intent.getParcelableExtra(SAVED_RECIPE)
        Log.d("WidgetProvider", recipe.toString())
    }

    companion object {

        var recipe: Recipe? = null

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

            val views = RemoteViews(PACKAGE_NAME, R.layout.recipe_list_widget_provider)
            val prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
            val recipeString = prefs.getString(SAVED_RECIPE, null)

            if (recipe == null && recipeString != null) {
                recipe = Gson().fromJson(recipeString)
            }

            if (recipe != null) {
                // Construct the RemoteViews object
                views.setTextViewText(R.id.widget_title_text_view, recipe?.name)
                val listIntent = Intent(context, IngredientViewService::class.java)
                views.setRemoteAdapter(R.id.ingredients_list_view, listIntent)
            } else {
                views.setTextViewText(R.id.widget_title_text_view, context.getString(R.string.no_recipe))
            }

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

