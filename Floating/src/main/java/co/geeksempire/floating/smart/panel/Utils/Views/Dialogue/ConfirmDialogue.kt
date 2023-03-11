/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/11/23, 11:27 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.floating.smart.panel.Utils.Views.Dialogue

import android.text.Html
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.floating.smart.panel.R
import co.geeksempire.floating.smart.panel.databinding.ConfirmationLayoutBinding

interface ConfirmDialogueInterface {
    fun confirmed()
    fun dismissed()
}

class ConfirmDialogue (private val context: AppCompatActivity, private val viewGroup: ConstraintLayout) {

    private val configurationsLayoutBinding = ConfirmationLayoutBinding.inflate(context.layoutInflater)

    fun initialize(dialogueTitle: String, dialogueDescription: String) : ConfirmDialogue {

        viewGroup.addView(configurationsLayoutBinding.root)

        configurationsLayoutBinding.root.visibility = View.INVISIBLE

        configurationsLayoutBinding.confirmTitle.text = dialogueTitle

        configurationsLayoutBinding.confirmDescription.text = Html.fromHtml(dialogueDescription, Html.FROM_HTML_MODE_COMPACT)

        configurationsLayoutBinding.rootView.apply {
            visibility = View.VISIBLE
            startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        }

        return this@ConfirmDialogue
    }

    fun show(confirmDialogueInterface: ConfirmDialogueInterface) {

        configurationsLayoutBinding.contentWrapper.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_up))

        configurationsLayoutBinding.confirmButton.setOnClickListener {

            confirmDialogueInterface.confirmed()

            dismiss()

        }

        configurationsLayoutBinding.dismissButton.setOnClickListener {

            confirmDialogueInterface.dismissed()

        }

    }

    fun dismiss() {

        configurationsLayoutBinding.contentWrapper.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_down))

        configurationsLayoutBinding.rootView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))

        viewGroup.removeView(configurationsLayoutBinding.root)

    }

}