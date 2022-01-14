package com.masliaiev.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.masliaiev.composition.R
import com.masliaiev.composition.domain.entity.GameResult

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

private fun calculatePercentOfRightAnswers(gameResult: GameResult): Int {
    with(gameResult) {
        if (countOfQuestions == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}