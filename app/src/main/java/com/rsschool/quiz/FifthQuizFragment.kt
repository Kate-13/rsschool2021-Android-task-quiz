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
import com.rsschool.quiz.databinding.FifthFragmentBinding
import kotlin.collections.sum


class FifthQuizFragment : Fragment(){

    private var _binding: FifthFragmentBinding? = null
    private val binding get() = _binding!!

    private var resultButton: Button? = null
    private var previousButton: Button? = null

    private var startFragment: StartFragment? = null
    private var startResultFragment: StartFragment? = null
    private var question = ""
    private var answer = ""
    private var rightAnswer = ""
    private var isRightAnswer = false
    private var answerID: Int = 0
    private var isRight: Int = 0
    private var answerList = mutableListOf<String?>()
    private var textResult = ""

    val RB_PREFERENCES = "Radio_Button_Preferences"
    val RB_PREFERENCES_ID_FIFTH_FRAGMENT = "RADIO_BUTTON_ID_FIFTH_FRAGMENT"

    private var ANSWER_PREFERENCES = "IS_RIGHT_ANSWER_PREFERENCES"
    private var IS_RIGHT_ANSWER_FIFTH = "IS_RIGHT_ANSWER_FIFTH"

    val ANSWERS = "ANSWERS"
    private var QUESTION_FIFTH = "QUESTION_FIFTH"
    private var ANSWER_FIFTH = "ANSWER_FIFTH"

    private var savedRadioIndex: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startFragment = context as? StartFragment
        startResultFragment = context as? StartFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FifthFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.question.text = "Какое животное говорит ууу?"
        binding.option1.text = "Кот"
        binding.option2.text = "Собака"
        binding.option3.text = "Петух"
        binding.option4.text = "Сова"
        binding.option5.text = "Корова"

        rightAnswer = "Сова"
        question = binding.question.text.toString()

        resultButton = binding.nextButton
        previousButton = binding.previousButton
        resultButton?.isEnabled = false

        //        position  = arguments?.getInt(PREVIOUS_ANSWER) ?: throw IllegalArgumentException()
//        println("position: $position")

        binding.nextButton.text = "Submit"

        val radioGroup = binding.radioGroup
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(RB_PREFERENCES, Context.MODE_PRIVATE)
            ?: throw IllegalArgumentException()
        savedRadioIndex = sharedPreferences.getInt(RB_PREFERENCES_ID_FIFTH_FRAGMENT, 0)
        radioGroup.check(savedRadioIndex)

        radioGroup?.forEach {
            if ((it as RadioButton).isChecked) {
                resultButton?.isEnabled = true
                println("NextButton is true")
            }
        }

        radioGroup?.setOnCheckedChangeListener { _, checkedId ->
            isRightAnswer = false

            radioGroup?.forEach {
                if((it as RadioButton).isChecked)
                    answer = it.text.toString()
                resultButton?.isEnabled = true
                answerID = checkedId
                startFragment?.savePreferences(RB_PREFERENCES, RB_PREFERENCES_ID_FIFTH_FRAGMENT, answerID)

            }
            if(answer == rightAnswer){
                isRight = 1
                println("$answer is $isRightAnswer")
            } else {
                isRight = 0
                println("$answer is $isRightAnswer")
            }
            startFragment?.savePreferences(ANSWER_PREFERENCES, IS_RIGHT_ANSWER_FIFTH, isRight)
            println("ANSWER_PREFERENCES: $ANSWER_PREFERENCES")
        }

        resultButton?.setOnClickListener {
            startFragment?.saveAnswerList(QUESTION_FIFTH, question)
            startFragment?.saveAnswerList(ANSWER_FIFTH, answer)

            val answerPreferences: SharedPreferences = context?.getSharedPreferences(ANSWER_PREFERENCES, Context.MODE_PRIVATE)
                ?: throw IllegalArgumentException()
            val sharedPreferencesValues = answerPreferences.all.map{it.value}//(ANSWER_PREFERENCES, Context.MODE_PRIVATE)
            println("sharedPreferencesValues: $sharedPreferencesValues")
            var sum = 0
            sharedPreferencesValues.forEach { el ->
                if (el == 1){
                    sum += 1
                }
                println("$sum")
            }

            getList("QUESTION_FIRST")
            getList("ANSWER_FIRST")
            getList("QUESTION_SECOND")
            getList("ANSWER_SECOND")
            getList("QUESTION_THIRD")
            getList("ANSWER_THIRD")
            getList("QUESTION_FOURTH")
            getList("ANSWER_FOURTH")
            getList("QUESTION_FIFTH")
            getList("ANSWER_FIFTH")

            var resultList = ""
            answerList?.forEachIndexed { ind, el->
                if(ind % 2 == 0) {
                    val qNumber = ind/2 + 1
                    resultList += "Question $qNumber: $el\n"
                } else resultList += "Your answer: $el\n\n"
            }

            val resultTextList = "$textResult\n$resultList"
            println("result list: $resultTextList")

            startResultFragment?.openResultFragment(sum, resultTextList)
        }

        previousButton?.setOnClickListener {
            startFragment?.openFourthQuizFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getList(key: String) {
        val answerPreferences: SharedPreferences = context?.getSharedPreferences(ANSWERS, Context.MODE_PRIVATE)
            ?: throw IllegalArgumentException()
        val sharedPreferencesValues = answerPreferences.getString(key, "")
        answerList.add(sharedPreferencesValues)
    }

    companion object {

        @JvmStatic
        fun newInstance(): FifthQuizFragment {
            val fragment = FifthQuizFragment()
            val args = Bundle()
            //args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

}