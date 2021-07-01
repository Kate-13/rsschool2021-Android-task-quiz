package com.rsschool.quiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.widget.Toolbar
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.SecondFragmentBinding


class SecondQuizFragment : Fragment(){

    private var _binding: SecondFragmentBinding? = null
    private val binding get() = _binding!!

    private var nextButton: Button? = null
    private var previousButton: Button? = null
    private var answer = ""
    private var question = ""
    private var rightAnswer = ""
    private var isRightAnswer = false
    private var isRight: Int = 0
    private var answerID: Int = 0

    private var RB_PREFERENCES = "Radio_Button_Preferences"
    private var RB_PREFERENCES_ID_SECOND_FRAGMENT = "RADIO_BUTTON_ID_SECOND_FRAGMENT"

    private var ANSWER_PREFERENCES = "IS_RIGHT_ANSWER_PREFERENCES"
    private var IS_RIGHT_ANSWER_SECOND = "IS_RIGHT_ANSWER_SECOND"

    private var QUESTION_SECOND = "QUESTION_SECOND"
    private var ANSWER_SECOND = "ANSWER_SECOND"

    private var savedRadioIndex: Int = 0

    private var toolbar: Toolbar? = null

    private var startFragment: StartFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startFragment = context as? StartFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        context?.setTheme(R.style.ThemeQuizSecond)
        startFragment?.setStatusBarTheme()

        _binding = SecondFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.question.text = "What are the native people of Antarctica called?"
        binding.option1.text = "Antarcticans"
        binding.option2.text = "South Polians"
        binding.option3.text = "Americans"
        binding.option4.text = "Russians"
        binding.option5.text = "There are no native people of Antarctica"

        rightAnswer = "There are no native people of Antarctica"
        question = binding.question.text.toString()

        toolbar = binding.toolbar

        nextButton = binding.nextButton
        previousButton = binding.previousButton
        nextButton?.isEnabled = false
        val radioGroup = binding.radioGroup

        val sharedPreferences: SharedPreferences =
            context?.getSharedPreferences(RB_PREFERENCES, Context.MODE_PRIVATE)
                ?: throw IllegalArgumentException()
        savedRadioIndex = sharedPreferences.getInt(RB_PREFERENCES_ID_SECOND_FRAGMENT, 0)
        radioGroup.check(savedRadioIndex)

        radioGroup?.forEach {
            if ((it as RadioButton).isChecked) {
                nextButton?.isEnabled = true
                answer = it.text.toString()
            }
        }

        radioGroup?.setOnCheckedChangeListener { _, checkedId ->
            isRightAnswer = false

            radioGroup?.forEach {
                if ((it as RadioButton).isChecked)
                    answer = it.text.toString()
                    nextButton?.isEnabled = true
                    answerID = checkedId
                    startFragment?.savePreferences(RB_PREFERENCES,RB_PREFERENCES_ID_SECOND_FRAGMENT,answerID)
            }

            if (answer == rightAnswer) {
                isRight = 1
            } else {
                isRight = 0
            }
            startFragment?.savePreferences(ANSWER_PREFERENCES, IS_RIGHT_ANSWER_SECOND, isRight)
        }

        nextButton?.setOnClickListener {
            startFragment?.saveAnswerList(QUESTION_SECOND, question)
            startFragment?.saveAnswerList(ANSWER_SECOND, answer)
            startFragment?.openThirdQuizFragment()
        }

        previousButton?.setOnClickListener {
            startFragment?.openFirstQuizFragment()
        }

        toolbar?.setNavigationOnClickListener {
            startFragment?.openFirstQuizFragment()
        }
    }

        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}