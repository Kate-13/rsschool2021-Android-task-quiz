package com.rsschool.quiz


import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity(), StartFragment
{
    val RB_PREFERENCES = "Radio_Button_Preferences"
    val ANSWERS = "ANSWERS"

    var answerList = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFirstQuizFragment()
    }


    override fun openFirstQuizFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, FirstQuizFragment())
        transaction.commit()
    }

    override fun openSecondQuizFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, SecondQuizFragment())
        transaction.commit()
    }

    override fun openThirdQuizFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, ThirdQuizFragment())
        transaction.commit()
    }

    override fun openFourthQuizFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, FourthQuizFragment())
        transaction.commit()
    }

    override fun openFifthQuizFragment() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, FifthQuizFragment())
        transaction.commit()
    }

    override fun openResultFragment(result: Int, resultTextList: String) {
        val quizResultFragment: Fragment = ResultFragment.newInstance(result, resultTextList)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizResultFragment)
        transaction.commit()
    }


    override fun savePreferences(name: String, key: String, value: Int) {
        val sharedPreferences: SharedPreferences? =
            getSharedPreferences(name, MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putInt(key, value)
        editor?.apply()
    }

     override fun saveAnswerList(key: String, answer: String) {
        val sharedPreferences: SharedPreferences? =
            getSharedPreferences(ANSWERS, MODE_PRIVATE)
         answerList.add(answer)
        val editor = sharedPreferences?.edit()
         editor?.putString(key, answer)
         editor?.apply()
     }

    override fun setStatusBarTheme() {
        val typedValue = TypedValue()
        val currentTheme = theme
        currentTheme?.resolveAttribute(android.R.attr.statusBarColor, typedValue, true)
        val window = this.window
        window?.statusBarColor = typedValue.data
    }

    override fun onDestroy() {
        clearAnswerList(RB_PREFERENCES, "RB_PREFERENCES_ID")
        clearAnswerList(ANSWERS, "ANSWER_FIRST")

        super.onDestroy()
    }

    fun clearAnswerList(name: String, key: String) {
        val sharedPreferences: SharedPreferences? = getSharedPreferences(name, MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.remove(key)
        editor?.clear()
        editor?.apply()
    }

}

interface StartFragment {
    fun openFirstQuizFragment()
    fun openSecondQuizFragment()
    fun openThirdQuizFragment()
    fun openFourthQuizFragment()
    fun openFifthQuizFragment()
    fun openResultFragment(result: Int, resultTextList: String)
    fun savePreferences(name: String, key: String, value: Int)
    fun saveAnswerList(key: String, answer: String)
    fun setStatusBarTheme()
}