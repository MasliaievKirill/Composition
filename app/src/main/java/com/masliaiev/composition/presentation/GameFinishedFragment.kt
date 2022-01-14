package com.masliaiev.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.masliaiev.composition.R
import com.masliaiev.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        binding.emojiResult.setImageResource(chooseImage(args.gameResult.winner))
        binding.tvRequiredAnswers.text = String.format(
            getString(R.string.required_score),
            args.gameResult.gameSettings.minCountOfRightAnswers
        )
        binding.tvScoreAnswers.text =
            String.format(getString(R.string.score_answers), args.gameResult.countOfRightAnswers)
        binding.tvRequiredPercentage.text = String.format(
            getString(R.string.required_percentage),
            args.gameResult.gameSettings.minPercentOfRightAnswers
        )
        binding.tvScorePercentage.text =
            String.format(getString(R.string.score_percentage), calculatePercentOfRightAnswers())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (args.gameResult.countOfQuestions == 0) {
            return 0
        }
        return ((args.gameResult.countOfRightAnswers / args.gameResult.countOfQuestions.toDouble()) * 100).toInt()
    }


    private fun chooseImage(isWinner: Boolean): Int {
        return if (isWinner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }


}