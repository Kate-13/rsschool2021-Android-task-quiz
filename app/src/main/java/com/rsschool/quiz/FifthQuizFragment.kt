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


class FifthQuizFragment : Fragment(){

    private var _binding: FifthFragmentBinding? = null
    private val binding get() = _binding!!

    private var resultButton: Button? = null
    private var previousButton: Button? = null

    private var startFragment: StartFragment? = null
    private var startResultFragment: StartFragment? = null

    private var answer = ""
    private var rightAnswer = ""
    private var isRightAnswer = false
    private var answerID: Int = 0

    val RB_PREFERENCES = "Radio_Button_Preferences"
    val RB_PREFERENCES_ID_FIFTH_FRAGMENT = "RADIO_BUTTON_ID_FIFTH_FRAGMENT"

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
                startFragment?.savePreferences(RB_PREFERENCES_ID_FIFTH_FRAGMENT, answerID)

            }
            if(answer == rightAnswer){
                isRightAnswer = true
                println("$answer is $isRightAnswer")
            } else println("$answer is $isRightAnswer")
        }

        resultButton?.setOnClickListener {
            startResultFragment?.openResultFragment(1)
        }

        previousButton?.setOnClickListener {
            startFragment?.openFourthQuizFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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