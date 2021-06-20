package com.rsschool.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.content.Context
import android.widget.Button
import com.rsschool.quiz.databinding.FourthFragmentBinding


class FourthQuizFragment : Fragment(){

    private var _binding: FourthFragmentBinding? = null
    private val binding get() = _binding!!

    private var nextButton: Button? = null
    private var previousButton: Button? = null

    private var startThirdFragment: StartFragment? = null
    private var startFifthFragment: StartFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startThirdFragment = context as? StartFragment
        startFifthFragment = context as? StartFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FourthFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        //return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.question.text = "Какое животное говорит Кукареку?"
        binding.option1.text = "Кот"
        binding.option2.text = "Собака"
        binding.option3.text = "Петух"
        binding.option4.text = "Сова"
        binding.option5.text = "Корова"

        nextButton = binding.nextButton
        previousButton = binding.previousButton


        nextButton?.setOnClickListener {
           startFifthFragment?.openFifthQuizFragment(1)
        }

        previousButton?.setOnClickListener {
            startThirdFragment?.openThirdQuizFragment(1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FourthQuizFragment {
            val fragment = FourthQuizFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

}