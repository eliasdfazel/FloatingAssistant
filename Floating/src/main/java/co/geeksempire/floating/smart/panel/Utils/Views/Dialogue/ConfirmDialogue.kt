/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/11/23, 11:33 AM
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

    private val confirmationLayoutBinding = ConfirmationLayoutBinding.inflate(context.layoutInflater)

    fun initialize(dialogueTitle: String, dialogueDescription: String) : ConfirmDialogue {

        viewGroup.addView(confirmationLayoutBinding.root)

        confirmationLayoutBinding.root.visibility = View.INVISIBLE

        val confirmDialogueParameters = confirmationLayoutBinding.root.layoutParams as ConstraintLayout.LayoutParams
        confirmDialogueParameters.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        confirmDialogueParameters.height = ConstraintLayout.LayoutParams.MATCH_PARENT
        confirmationLayoutBinding.root.layoutParams = confirmDialogueParameters

        confirmationLayoutBinding.confirmTitle.text = dialogueTitle

        confirmationLayoutBinding.confirmDescription.text = Html.fromHtml(dialogueDescription, Html.FROM_HTML_MODE_COMPACT)

        confirmationLayoutBinding.rootView.apply {
            visibility = View.VISIBLE
            startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        }

        return this@ConfirmDialogue
    }

    fun show(confirmDialogueInterface: ConfirmDialogueInterface) {

        confirmationLayoutBinding.contentWrapper.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_up))

        confirmationLayoutBinding.confirmButton.setOnClickListener {

            confirmDialogueInterface.confirmed()

            dismiss()

        }

        confirmationLayoutBinding.dismissButton.setOnClickListener {

            confirmDialogueInterface.dismissed()

        }

    }

    fun dismiss() {

        confirmationLayoutBinding.contentWrapper.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_down))

        confirmationLayoutBinding.rootView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))

        viewGroup.removeView(confirmationLayoutBinding.root)

    }

}