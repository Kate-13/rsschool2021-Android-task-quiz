package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.widget.Button
import android.widget.RadioButton
import androidx.core.view.forEach
import com.rsschool.quiz.databinding.FourthFragmentBinding


class FourthQuizFragment : Fragment(){

    private var _binding: FourthFragmentBinding? = null
    private val binding get() = _binding!!

    private var nextButton: Button? = null
    private var previousButton: Button? = null

    private var startFragment: StartFragment? = null

    private var answer = ""
    private var rightAnswer = ""
    private var isRightAnswer = false
    private var answerID: Int = 0
    private var isRight: Int = 0

    val RB_PREFERENCES = "Radio_Button_Preferences"
    val RB_PREFERENCES_ID_FOURTH_FRAGMENT = "RADIO_BUTTON_ID_FOURTH_FRAGMENT"

    private var ANSWER_PREFERENCES = "IS_RIGHT_ANSWER_PREFERENCES"
    private var IS_RIGHT_ANSWER_FOURTH = "IS_RIGHT_ANSWER_FOURTH"

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

        _binding = FourthFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.question.text = "Какое животное говорит Кукареку?"
        binding.option1.text = "Кот"
        binding.option2.text = "Собака"
        binding.option3.text = "Петух"
        binding.option4.text = "Сова"
        binding.option5.text = "Корова"

        rightAnswer = "Петух"

        nextButton = binding.nextButton
        previousButton = binding.previousButton
        nextButton?.isEnabled = false

        val radioGroup = binding.radioGroup
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(RB_PREFERENCES, Context.MODE_PRIVATE)
            ?: throw IllegalArgumentException()
        savedRadioIndex = sharedPreferences.getInt(RB_PREFERENCES_ID_FOURTH_FRAGMENT, 0)
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
                startFragment?.savePreferences(RB_PREFERENCES, RB_PREFERENCES_ID_FOURTH_FRAGMENT, answerID)

            }
            if(answer == rightAnswer){
                isRight = 1
                println("$answer is $isRightAnswer")
            } else {
                isRight = 0
                println("$answer is $isRightAnswer")
            }
            startFragment?.savePreferences(ANSWER_PREFERENCES, IS_RIGHT_ANSWER_FOURTH, isRight)
        }

        nextButton?.setOnClickListener {
            startFragment?.openFifthQuizFragment()
        }

        previousButton?.setOnClickListener {
            startFragment?.openThirdQuizFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(): FourthQuizFragment {
            val fragment = FourthQuizFragment()
            val args = Bundle()
            //args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

}