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

        binding.result.text = "Ваш результат: "
        backButton = binding.backButton
        exitButton = binding.exitButton

        exitButton?.setOnClickListener {
            //finish()
        }

        backButton?.setOnClickListener {
            startFirstFragment?.openFirstQuizFragment(1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

}