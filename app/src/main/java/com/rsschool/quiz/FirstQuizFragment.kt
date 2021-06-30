package com.rsschool.quiz


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
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
    //private var anwersList: MutableList<Int> = mutableListOf()
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
        //return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.question.text = "Какое животное говорит Мяу?"
        binding.option1.text = "Кот"
        binding.option2.text = "Собака"
        binding.option3.text = "Петух"
        binding.option4.text = "Сова"
        binding.option5.text = "Корова"

        nextButton = binding.nextButton
        previousButton = binding.previousButton
        previousButton?.isEnabled = false
        nextButton?.isEnabled = false
        question = binding.question.text.toString()
        rightAnswer = "Кот"


        val radioGroup = binding.radioGroup

        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(RB_PREFERENCES, MODE_PRIVATE)
            ?: throw IllegalArgumentException()
        savedRadioIndex = sharedPreferences.getInt(RB_PREFERENCES_ID, 0)

        radioGroup.check(savedRadioIndex)

        radioGroup?.forEach {
            if ((it as RadioButton).isChecked) {
                nextButton?.isEnabled = true
                println("NextButton is true")
            }
        }

        radioGroup?.setOnCheckedChangeListener { _, checkedId ->
            isRightAnswer = false

            //val radioButtonID: Int = radioGroup?.checkedRadioButtonId

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
                println("$answer is $isRightAnswer")
            } else {
                isRight = 0
                println("$answer is $isRightAnswer")
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


    companion object {

        @JvmStatic

        fun newInstance(): FirstQuizFragment {
            val fragment = FirstQuizFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }

        private const val ANSWERS = "ANSWERS"
        private const val PREVIOUS_ANSWER = "PREVIOUS_RESULT"

    }
}
