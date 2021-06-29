package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.ResultFragmentBinding

class ResultFragment : Fragment(){

    private var _binding: ResultFragmentBinding? = null
    private val binding get() = _binding!!
    private var exitButton: Button? = null
    private var backButton: Button? = null
    private var result: Int = 0

    private var startFirstFragment: StartFragment? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        startFirstFragment = context as? StartFragment

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ResultFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        //return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        backButton = binding.backButton
        exitButton = binding.exitButton

        result  = arguments?.getInt(RESULT_KEY) ?: throw IllegalArgumentException()
        println("result: $result")

        binding.result.text = "Ваш результат: $result из 5"

        exitButton?.setOnClickListener {
            //finish()
        }

        backButton?.setOnClickListener {
            startFirstFragment?.openFirstQuizFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(result: Int): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putInt(RESULT_KEY, result)
            fragment.arguments = args
            return fragment
        }

        private const val RESULT_KEY = "RESULT"
    }

}