package com.rsschool.quiz


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FirstFragmentBinding
import kotlin.properties.Delegates


class FirstQuizFragment : Fragment() {

    private var _binding: FirstFragmentBinding? = null
    private val binding get() = _binding!!
    private var nextButton: Button? = null
    private var previousButton: Button? = null
    private var question = ""
    private var answer = ""
    private var position by Delegates.notNull<Int>()

    private var answerID: Int = 0
    private var rightAnswer = ""
    private var isRightAnswer = false
    private var isRight: Int = 0
    private var startFragment: StartFragment? = null

    private var RB_PREFERENCES = "Radio_Button_Preferences"
    private var RB_PREFERENCES_ID = "RADIO_BUTTON_ID"

    private var ANSWER_PREFERENCES = "IS_RIGHT_ANSWER_PREFERENCES"
    private var IS_RIGHT_ANSWER_FIRST = "IS_RIGHT_ANSWER_FIRST"

    private var QUESTION_FIRST = "QUESTION_FIRST"
    private var ANSWER_FIRST = "ANSWER_FIRST"

    private var savedRadioIndex: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startFragment = context as? StartFragment

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        context?.setTheme(R.style.ThemeQuizFirst)
        startFragment?.setStatusBarTheme()

        _binding = FirstFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.question.text = "Which of these animals do you NOT get in Antarctica?"
        binding.option1.text = "Penguins"
        binding.option2.text = "Whales"
        binding.option3.text = "Polar bears"
        binding.option4.text = "Seals"
        binding.option5.text = "Dolphins"

        nextButton = binding.nextButton
        previousButton = binding.previousButton
        previousButton?.isEnabled = false
        nextButton?.isEnabled = false
        question = binding.question.text.toString()
        rightAnswer = "Polar bears"


        val radioGroup = binding.radioGroup

        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(RB_PREFERENCES, MODE_PRIVATE)
            ?: throw IllegalArgumentException()
        savedRadioIndex = sharedPreferences.getInt(RB_PREFERENCES_ID, 0)

        radioGroup.check(savedRadioIndex)

        radioGroup?.forEach {
            if ((it as RadioButton).isChecked) {
                nextButton?.isEnabled = true
                answer = it.text.toString()
            }
        }

        radioGroup?.setOnCheckedChangeListener { _, checkedId ->
            isRightAnswer = false

            val checkedRadioButton = radioGroup.findViewById<RadioButton>(checkedId)
            val checkedAnswerPosition = radioGroup.indexOfChild(checkedRadioButton)

            radioGroup?.forEach {
                if ((it as RadioButton).isChecked)
                    answer = it.text.toString()
                nextButton?.isEnabled = true
                answerID = checkedId
                position = checkedAnswerPosition

                startFragment?.savePreferences(RB_PREFERENCES, RB_PREFERENCES_ID, answerID)

            }

            if (answer == rightAnswer) {
                isRightAnswer = true
                isRight = 1
            } else {
                isRight = 0
            }

            startFragment?.savePreferences(ANSWER_PREFERENCES, IS_RIGHT_ANSWER_FIRST, isRight)
        }
//

        nextButton?.setOnClickListener {
            startFragment?.saveAnswerList(QUESTION_FIRST, question)
            startFragment?.saveAnswerList(ANSWER_FIRST, answer)
            startFragment?.openSecondQuizFragment()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
