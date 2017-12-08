package xyz.cybersapien.miriamslittlebakery.fragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_step_detail.*
import xyz.cybersapien.miriamslittlebakery.R
import xyz.cybersapien.miriamslittlebakery.model.Step
import xyz.cybersapien.miriamslittlebakery.utils.SELECTED_STEP

class StepDetailFragment : Fragment() {

    private val TAG = "StepDetailFragment"
    private lateinit var thisStep: Step

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val rootView = inflater.inflate(R.layout.fragment_step_detail, container, false)

        thisStep = arguments?.getParcelable(SELECTED_STEP)!!
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.placeholder, null) as BitmapDrawable
        videoPlayerView.defaultArtwork = drawable.bitmap

        if (thisStep.id != 0)
            step_desc_text_view.text = resources.getString(R.string.step, thisStep.id, thisStep.shortDescription)
        else
            step_desc_text_view.text = thisStep.shortDescription

        step_long_description_textview.text = thisStep.description

        when {
            thisStep.videoURL.isNotEmpty() -> setupExoPlayer(thisStep.videoURL)
            thisStep.thumbnailURL.isNotEmpty() -> setupExoPlayer(thisStep.thumbnailURL)
            else -> showNoVideoView()
        }
    }

    private fun showNoVideoView() {
        videoPlayerView.visibility = View.GONE
        noVideoView.visibility = View.VISIBLE
    }

    private fun setupExoPlayer(url: String) {
        // TODO: Setup Player
        noVideoView.visibility = View.GONE
        videoPlayerView.visibility = View.VISIBLE
    }
}