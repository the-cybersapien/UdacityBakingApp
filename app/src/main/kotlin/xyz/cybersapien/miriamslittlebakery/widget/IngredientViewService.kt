package xyz.cybersapien.miriamslittlebakery.widget

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.google.gson.Gson
import xyz.cybersapien.miriamslittlebakery.R
import xyz.cybersapien.miriamslittlebakery.model.Ingredient
import xyz.cybersapien.miriamslittlebakery.model.Recipe
import xyz.cybersapien.miriamslittlebakery.utils.PACKAGE_NAME
import xyz.cybersapien.miriamslittlebakery.utils.SAVED_RECIPE
import xyz.cybersapien.miriamslittlebakery.utils.fromJson

/**
 * Created by ogcybersapien on 20/12/17.
 */
class IngredientViewService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return IngredientsRemoteViewFactory(applicationContext, intent)
    }

    inner class IngredientsRemoteViewFactory(val context: Context, private val intent: Intent) : RemoteViewsService.RemoteViewsFactory {

        private var ingredientsList: ArrayList<Ingredient>? = null

        override fun onCreate() {
            val prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
            val recipe = Gson().fromJson<Recipe>(prefs.getString(SAVED_RECIPE, null))
            ingredientsList = recipe.ingredients
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun onDataSetChanged() {
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        override fun getViewAt(position: Int): RemoteViews {
            val view = RemoteViews(PACKAGE_NAME, R.layout.item_widget_ingredient)
            val ingredient = ingredientsList?.get(position)
            view.setTextViewText(R.id.quantity_text_view, "${ingredient?.quantity}")
            view.setTextViewText(R.id.quantity_type_text_view, "${ingredient?.measure}")
            view.setTextViewText(R.id.ingredient_name_text_view, "${ingredient?.ingredient}")
            return view
        }

        override fun getCount(): Int {
            return ingredientsList?.size ?: 0
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun onDestroy() {
            // Do Nothing
        }

    }
}