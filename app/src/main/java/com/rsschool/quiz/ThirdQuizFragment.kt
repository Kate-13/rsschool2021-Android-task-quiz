package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.util.TypedValue
import android.widget.Button
import android.widget.RadioButton
import androidx.core.view.forEach
import com.rsschool.quiz.databinding.ThirdFragmentBinding
import kotlin.properties.Delegates


class ThirdQuizFragment : Fragment(){

    private var _binding: ThirdFragmentBinding? = null
    private val binding get() = _binding!!

    private var nextButton: Button? = null
    private var previousButton: Button? = null

    private var startFragment: StartFragment? = null
    private var question = ""
    private var answer = ""
    private var rightAnswer = ""
    private var isRightAnswer = false
    private var answerID: Int = 0
    private var isRight: Int = 0

    private val RB_PREFERENCES = "Radio_Button_Preferences"
    private val RB_PREFERENCES_ID_THIRD_FRAGMENT = "RADIO_BUTTON_ID_THIRD_FRAGMENT"

    private var ANSWER_PREFERENCES = "IS_RIGHT_ANSWER_PREFERENCES"
    private var IS_RIGHT_ANSWER_THIRD = "IS_RIGHT_ANSWER_THIRD"

    private var QUESTION_THIRD = "QUESTION_THIRD"
    private var ANSWER_THIRD = "ANSWER_THIRD"

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

        context?.setTheme(R.style.ThemeQuizThird)
        startFragment?.setStatusBarTheme()

        _binding = ThirdFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        //return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.question.text = "Какое животное говорит Му?"
        binding.option1.text = "Кот"
        binding.option2.text = "Собака"
        binding.option3.text = "Петух"
        binding.option4.text = "Сова"
        binding.option5.text = "Корова"

        rightAnswer = "Корова"
        question = binding.question.text.toString()

        nextButton = binding.nextButton
        previousButton = binding.previousButton
        nextButton?.isEnabled = false

        val radioGroup = binding.radioGroup
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(RB_PREFERENCES, Context.MODE_PRIVATE)
            ?: throw IllegalArgumentException()
        savedRadioIndex = sharedPreferences.getInt(RB_PREFERENCES_ID_THIRD_FRAGMENT, 0)
        radioGroup.check(savedRadioIndex)

        radioGroup?.forEach {
            if ((it as RadioButton).isChecked) {
                nextButton?.isEnabled = true
                println("NextButton is true")
            }
        }

        radioGroup?.setOnCheckedChangeListener { _, checkedId ->
            isRightAnswer = false

            radioGroup?.forEach {
                if((it as RadioButton).isChecked)
                    answer = it.text.toString()
                nextButton?.isEnabled = true
                answerID = checkedId
                startFragment?.savePreferences(RB_PREFERENCES, RB_PREFERENCES_ID_THIRD_FRAGMENT, answerID)

            }
            if(answer == rightAnswer){
                isRight = 1
                println("$answer is $isRightAnswer")
            } else {
                isRight = 0
                println("$answer is $isRightAnswer")
            }
            startFragment?.savePreferences(ANSWER_PREFERENCES, IS_RIGHT_ANSWER_THIRD, isRight)
        }

        nextButton?.setOnClickListener {
            startFragment?.saveAnswerList(QUESTION_THIRD, question)
            startFragment?.saveAnswerList(ANSWER_THIRD, answer)
            startFragment?.openFourthQuizFragment()
        }

        previousButton?.setOnClickListener {
            startFragment?.openSecondQuizFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(): ThirdQuizFragment {
            val fragment = ThirdQuizFragment()
            val args = Bundle()
//            args.putInt(PREVIOUS_ANSWER, previousResult)
//            args.putInt(PREVIOUS_ANSWER_FOR_SECOND, positionForSecond)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_ANSWER = "PREVIOUS_RESULT"
        private const val PREVIOUS_ANSWER_FOR_SECOND = "PREVIOUS_RESULT_FOR_SECOND"
    }

}