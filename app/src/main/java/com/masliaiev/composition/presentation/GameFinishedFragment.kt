package com.masliaiev.composition.presentation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.masliaiev.composition.R
import com.masliaiev.composition.databinding.FragmentGameFinishedBinding
import com.masliaiev.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                retryGame()
            }
        })
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        binding.emojiResult.setImageResource(chooseImage(gameResult.winner))
        binding.tvRequiredAnswers.text = String.format(getString(R.string.required_score), gameResult.gameSettings.minCountOfRightAnswers)
        binding.tvScoreAnswers.text = String.format(getString(R.string.score_answers), gameResult.countOfRightAnswers)
        binding.tvRequiredPercentage.text = String.format(getString(R.string.required_percentage), gameResult.gameSettings.minPercentOfRightAnswers)
        binding.tvScorePercentage.text = String.format(getString(R.string.score_percentage),calculatePercentOfRightAnswers())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (gameResult.countOfQuestions == 0){
            return 0
        }
        return ((gameResult.countOfRightAnswers / gameResult.countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun parseArgs() {
       requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
           gameResult = it
       }
    }

    private fun chooseImage (isWinner: Boolean): Int {
        return  if (isWinner){
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.GAME_FRAGMENT_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {

        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}