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
import androidx.appcompat.widget.Toolbar
import androidx.core.view.forEach
import com.rsschool.quiz.databinding.FifthFragmentBinding



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

    private var toolbar: Toolbar? = null

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

        context?.setTheme(R.style.ThemeQuizFifth)
        startFragment?.setStatusBarTheme()

        _binding = FifthFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.question.text = "When did humans first SEE Antarctica?"
        binding.option1.text = "500 B.C."
        binding.option2.text = "1000 A.D."
        binding.option3.text = "1520 A.D."
        binding.option4.text = "1820 A.D."
        binding.option5.text = "1313 A.D."

        rightAnswer = "1820 A.D."
        question = binding.question.text.toString()

        resultButton = binding.nextButton
        previousButton = binding.previousButton
        resultButton?.isEnabled = false
        toolbar = binding.toolbar

        binding.nextButton.text = "Submit"

        val radioGroup = binding.radioGroup
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences(RB_PREFERENCES, Context.MODE_PRIVATE)
            ?: throw IllegalArgumentException()
        savedRadioIndex = sharedPreferences.getInt(RB_PREFERENCES_ID_FIFTH_FRAGMENT, 0)
        radioGroup.check(savedRadioIndex)

        radioGroup?.forEach {
            if ((it as RadioButton).isChecked) {
                resultButton?.isEnabled = true
                answer = it.text.toString()
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
            } else {
                isRight = 0
            }
            startFragment?.savePreferences(ANSWER_PREFERENCES, IS_RIGHT_ANSWER_FIFTH, isRight)
        }

        resultButton?.setOnClickListener {
            startFragment?.saveAnswerList(QUESTION_FIFTH, question)
            startFragment?.saveAnswerList(ANSWER_FIFTH, answer)

            val answerPreferences: SharedPreferences = context?.getSharedPreferences(ANSWER_PREFERENCES, Context.MODE_PRIVATE)
                ?: throw IllegalArgumentException()
            val sharedPreferencesValues = answerPreferences.all.map{it.value}
            var sum = 0
            sharedPreferencesValues.forEach { el ->
                if (el == 1){
                    sum += 1
                }
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

            startResultFragment?.openResultFragment(sum, resultTextList)
        }

        previousButton?.setOnClickListener {
            startFragment?.openFourthQuizFragment()
        }

        toolbar?.setNavigationOnClickListener {
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

}