package xyz.cybersapien.miriamslittlebakery.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import xyz.cybersapien.miriamslittlebakery.R
import xyz.cybersapien.miriamslittlebakery.callback.OnStepClick
import xyz.cybersapien.miriamslittlebakery.model.Step
import xyz.cybersapien.miriamslittlebakery.utils.INGREDIENT_STEP


class StepsAdapter(val context: Context,
                   private val steps: ArrayList<Step>) :
//                   private val clickListenerCallback: OnStepClick) :
        RecyclerView.Adapter<StepsAdapter.ViewHolder>() {

    private val stepType = 101
    private val ingredientType = 102
    private val clickListenerCallback: OnStepClick

    init {
        if (context is OnStepClick)
            clickListenerCallback = context
        else
            throw IllegalArgumentException("Container Activity must implement `OnStepClick` interface")
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_step, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == ingredientType) {
            holder.textView?.text = context.getString(R.string.ingredients)
            holder.textView?.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_light))
            holder.itemView.setOnClickListener {
                clickListenerCallback.stepClicked(INGREDIENT_STEP)
            }
        } else {
            if (position != 1)
                holder.textView?.text = context.getString(R.string.step, position - 1, steps[position - 1].shortDescription)
            else
                holder.textView?.text = steps[position - 1].shortDescription
            holder.textView?.setTextColor(ContextCompat.getColor(context, android.R.color.black))

            // Add some extra padding to the last card
            if (position == steps.size) {
                val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
                val pxBottomMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.resources.displayMetrics)
                layoutParams.bottomMargin = Math.round(pxBottomMargin)
            }
            holder.itemView.setOnClickListener {
                clickListenerCallback.stepClicked(position - 1)
            }
        }
    }

    override fun getItemCount(): Int =
            when (steps.size) {
                0 -> 0
                else -> steps.size + 1
            }

    override fun getItemViewType(position: Int): Int =
            when (position) {
                0 -> ingredientType
                else -> stepType
            }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView?.findViewById<TextView>(R.id.step_desc_text_view)
    }
}