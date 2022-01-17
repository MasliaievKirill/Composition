package com.masliaiev.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.masliaiev.composition.R
import com.masliaiev.composition.domain.entity.GameResult

interface OnOptionClickListener{
    fun onOptionClick (option: Int)
}

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, count: Int) {
    textView.text =
        String.format(textView.context.getString(R.string.score_answers), count)
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text =
        String.format(
            textView.context.getString(R.string.score_percentage),
            calculatePercentOfRightAnswers(gameResult)
        )
}

@BindingAdapter("emojiResult")
fun bindEmojiResult(imageView: ImageView, isWinner: Boolean) {
    imageView.setImageResource(
        if (isWinner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    )
}

@BindingAdapter("answersProgressColour")
fun bindAnswersProgressColour(textView: TextView, enoughCount: Boolean) {
    textView.setTextColor(getColourByState(textView.context, enoughCount))
}

@BindingAdapter("enoughPercentColour")
fun bindEnoughPercentColour(progressBar: ProgressBar, enoughPercent: Boolean) {
    val colour = getColourByState(progressBar.context, enoughPercent)
    progressBar.progressTintList = ColorStateList.valueOf(colour)
}

@BindingAdapter("numberAsString")
fun bindNumberAsString (textView: TextView, number: Int){
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener (textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}


private fun calculatePercentOfRightAnswers(gameResult: GameResult): Int {
    with(gameResult) {
        if (countOfQuestions == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

private fun getColourByState(context: Context, state: Boolean): Int {
    val colourResId = if (state) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colourResId)
}