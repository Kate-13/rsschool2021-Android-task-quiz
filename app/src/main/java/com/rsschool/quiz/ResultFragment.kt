package com.rsschool.quiz

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.ResultFragmentBinding
import java.util.ArrayList


class ResultFragment : Fragment() {

    private var _binding: ResultFragmentBinding? = null
    private val binding get() = _binding!!
    private var exitButton: Button? = null
    private var backButton: Button? = null
    private var shareButton: Button? = null
    //private var answerList = mutableListOf<String?>()
    //private var answerList = mutableSetOf<String>()
    //private var answerList = "false"

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

        resultTextList = arguments?.getString(RESULT_LIST) ?: throw IllegalArgumentException()

        binding.result.text = "Ваш результат: $resultCount из 5"
        textResult = "Ваш результат: $resultCount из 5"

        shareButton?.setOnClickListener {

//            getList("QUESTION_FIRST")
//            getList("ANSWER_FIRST")
//            getList("QUESTION_SECOND")
//            getList("ANSWER_SECOND")
//            getList("QUESTION_THIRD")
//            getList("ANSWER_THIRD")
//            getList("QUESTION_FOURTH")
//            getList("ANSWER_FOURTH")
//            getList("QUESTION_FIFTH")
//            getList("ANSWER_FIFTH")
//
//            var resultList = ""
//            answerList?.forEachIndexed { ind, el->
//                 if(ind % 2 == 0) {
//                     val qNumber = ind/2 + 1
//                     resultList += "Question $qNumber: $el\n"
//                 } else resultList += "Your answer: $el\n\n"
//            }
//
//            val resultTextList = "$textResult\n$resultList"
//            println("result list: $resultTextList")

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_TEXT, resultTextList)
            emailIntent.setType("text/plain")

            //try {
            startActivity(Intent.createChooser(emailIntent, "Choose application"))

//            } catch (ex: ActivityNotFoundException) {
//                Toast.makeText(requireContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show()
//            }

            //if(emailIntent.resolveActivity(getPackageManager()) != null){
            //startActivity(emailIntent)
            //}
        }

        exitButton?.setOnClickListener {
            //finish()
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

//    fun getList(key: String) {
//        val answerPreferences: SharedPreferences = context?.getSharedPreferences(ANSWERS, Context.MODE_PRIVATE)
//            ?: throw IllegalArgumentException()
//        val sharedPreferencesValues = answerPreferences.getString(key, "")
//        answerList.add(sharedPreferencesValues)
//    }

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