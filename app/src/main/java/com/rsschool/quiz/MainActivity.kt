package com.rsschool.quiz

//import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity(), StartFragment
{
    val RB_PREFERENCES = "Radio_Button_Preferences"

//    var anwersList: MutableList<Int> = mutableListOf(0, 0, 0, 0, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFirstQuizFragment()
    }


    override fun openFirstQuizFragment() {
        val quizFragment: Fragment = FirstQuizFragment.newInstance()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizFragment)
        transaction.commit()
    }

    override fun openSecondQuizFragment() {
        val quizSecondFragment: Fragment = SecondQuizFragment.newInstance()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizSecondFragment)
        transaction.commit()
    }

    override fun openThirdQuizFragment() {
        val quizThirdFragment: Fragment = ThirdQuizFragment.newInstance()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizThirdFragment)
        transaction.commit()
    }

    override fun openFourthQuizFragment() {
        val quizFourthFragment: Fragment = FourthQuizFragment.newInstance()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizFourthFragment)
        transaction.commit()
    }

    override fun openFifthQuizFragment() {
        val quizFifthFragment: Fragment = FifthQuizFragment.newInstance()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizFifthFragment)
        transaction.commit()
    }

    override fun openResultFragment(previousResult: Int) {
        val quizResultFragment: Fragment = ResultFragment.newInstance(previousResult)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizResultFragment)
        transaction.commit()
    }

//    override fun onClick(v: View) {
//    val intent = Intent(this, MainActivity.class)
//    intent.putExtra("fname", etFName.getText().toString())
//    intent.putExtra("lname", etLName.getText().toString())
//    startActivity(intent);
//}

    override fun savePreferences(key: String, value: Int) {
        val sharedPreferences: SharedPreferences? =
            getSharedPreferences(RB_PREFERENCES, MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putInt(key, value)
        editor?.apply()
        println("Saved $key, $value")
    }

    override fun onDestroy() {
        val sharedPreferences: SharedPreferences? = getSharedPreferences(RB_PREFERENCES, MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.remove("RB_PREFERENCES_ID")
        editor?.clear()
        editor?.apply()

        super.onDestroy()
    }

}

interface StartFragment {
    fun openFirstQuizFragment()
    fun openSecondQuizFragment()
    fun openThirdQuizFragment()
    fun openFourthQuizFragment()
    fun openFifthQuizFragment()
    fun openResultFragment(previousResult: Int)
    fun savePreferences(key: String, value: Int)
}