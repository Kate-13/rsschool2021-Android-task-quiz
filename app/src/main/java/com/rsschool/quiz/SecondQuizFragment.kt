package com.rsschool.quiz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.SecondFragmentBinding
import kotlin.properties.Delegates
import kotlin.reflect.typeOf


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

    //private var previousToolbar: navigation? = null

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
//        val currentTheme = context?.theme
//        currentTheme?.resolveAttribute(android.R.attr.statusBarColor, typedValue, true)
//        val window = activity?.window
//        window?.statusBarColor = typedValue.data

        _binding = SecondFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.question.text = "Какое животное говорит Гав?"
        binding.option1.text = "Кот"
        binding.option2.text = "Собака"
        binding.option3.text = "Петух"
        binding.option4.text = "Сова"
        binding.option5.text = "Корова"

        rightAnswer = "Собака"
        question = binding.question.text.toString()

        nextButton = binding.nextButton
        previousButton = binding.previousButton
        nextButton?.isEnabled = false
        //previousToolbar = binding.toolbar
        val radioGroup = binding.radioGroup

        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(RB_PREFERENCES, Context.MODE_PRIVATE)
            ?: throw IllegalArgumentException()
        savedRadioIndex = sharedPreferences.getInt(RB_PREFERENCES_ID_SECOND_FRAGMENT, 0)
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
                startFragment?.savePreferences(RB_PREFERENCES,RB_PREFERENCES_ID_SECOND_FRAGMENT, answerID)

            }
            if(answer == rightAnswer){
                isRight = 1
                println("$answer is $isRightAnswer")
            } else {
                isRight = 0
                println("$answer is $isRightAnswer")
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(): SecondQuizFragment {
            val fragment = SecondQuizFragment()
            val args = Bundle()
//          args.putInt(PREVIOUS_ANSWER, previousAnswer)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_ANSWER = "PREVIOUS_RESULT"

    }

}