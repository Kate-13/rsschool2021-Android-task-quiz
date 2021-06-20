package com.rsschool.quiz

//import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity(), StartFragment
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFirstQuizFragment(0)
    }

    override fun openFirstQuizFragment(previousNumber: Int) {
        val quizFragment: Fragment = FirstQuizFragment.newInstance(previousNumber)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizFragment)
        transaction.commit()
    }

    override fun openSecondQuizFragment(previousNumber: Int) {
        val quizSecondFragment: Fragment = SecondQuizFragment.newInstance(previousNumber)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizSecondFragment)
        transaction.commit()
    }

    override fun openThirdQuizFragment(previousNumber: Int) {
        val quizThirdFragment: Fragment = ThirdQuizFragment.newInstance(previousNumber)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizThirdFragment)
        transaction.commit()
    }

    override fun openFourthQuizFragment(previousNumber: Int) {
        val quizFourthFragment: Fragment = FourthQuizFragment.newInstance(previousNumber)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizFourthFragment)
        transaction.commit()
    }

    override fun openFifthQuizFragment(previousNumber: Int) {
        val quizFifthFragment: Fragment = FifthQuizFragment.newInstance(previousNumber)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, quizFifthFragment)
        transaction.commit()
    }

}

interface StartFragment {
    //fun openSecondFragment(min: Int, max: Int)
    fun openFirstQuizFragment(previousNumber: Int)
    fun openSecondQuizFragment(previousNumber: Int)
    fun openThirdQuizFragment(previousNumber: Int)
    fun openFourthQuizFragment(previousNumber: Int)
    fun openFifthQuizFragment(previousNumber: Int)
}