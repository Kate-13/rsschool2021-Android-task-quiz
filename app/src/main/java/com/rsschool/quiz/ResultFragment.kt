package com.rsschool.quiz


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.ResultFragmentBinding
import kotlin.system.exitProcess


class ResultFragment : Fragment() {

    private var _binding: ResultFragmentBinding? = null
    private val binding get() = _binding!!
    private var exitButton: Button? = null
    private var backButton: Button? = null
    private var shareButton: Button? = null

    private var resultCount: Int = 0
    private var textResult = ""
    private var resultTextList = ""

    private var RB_PREFERENCES = "Radio_Button_Preferences"

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

        context?.setTheme(R.style.ThemeQuizResult)
        startFragment?.setStatusBarTheme()

        _binding = ResultFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton = binding.backButton
        exitButton = binding.exitButton
        shareButton = binding.shareButton

        resultCount = arguments?.getInt(RESULT_KEY) ?: throw IllegalArgumentException()

        binding.result.text = "Ваш результат: $resultCount из 5"
        textResult = "Ваш результат: $resultCount из 5\n\n"

        resultTextList = textResult + arguments?.getString(RESULT_LIST) ?: throw IllegalArgumentException()

        shareButton?.setOnClickListener {

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_TEXT, resultTextList)
            emailIntent.setType("text/plain")
            startActivity(Intent.createChooser(emailIntent, "Choose application"))
        }

        exitButton?.setOnClickListener {
            val sharedPreferences: SharedPreferences? = context?.getSharedPreferences(
                RB_PREFERENCES,
                AppCompatActivity.MODE_PRIVATE
            )
            val editor = sharedPreferences?.edit()
            editor?.remove("RB_PREFERENCES_ID")
            editor?.clear()
            editor?.apply()

            getActivity()?.finish()
            //exitProcess(0)
        }

        backButton?.setOnClickListener {
            val sharedPreferences: SharedPreferences? = context?.getSharedPreferences(
                RB_PREFERENCES,
                AppCompatActivity.MODE_PRIVATE
            )
            val editor = sharedPreferences?.edit()
            editor?.remove("RB_PREFERENCES_ID")
            editor?.clear()
            editor?.apply()

            startFragment?.openFirstQuizFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(result: Int, resultTextList: String): ResultFragment {
            val fragment = ResultFragment()
            val args = Bundle()
            args.putInt(RESULT_KEY, result)
            args.putString(RESULT_LIST, resultTextList)
            fragment.arguments = args
            return fragment
        }

        private const val RESULT_KEY = "RESULT"
        private const val RESULT_LIST = "RESULT_LIST"
    }

}