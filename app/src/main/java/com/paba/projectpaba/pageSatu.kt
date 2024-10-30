package com.paba.projectpaba

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class pageSatu : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page_satu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentScoreTextView = view.findViewById<TextView>(R.id.CurrentScore)
        currentScoreTextView.text = MainActivity.playerScore.toString()

        gameLogic(currentScoreTextView)

        view.findViewById<Button>(R.id.btnGiveUp).setOnClickListener {
            (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNav).selectedItemId =
                R.id.score_menu
        }
    }

    private fun gameLogic(currentScoreTextView: TextView) {
        val fragmentView = view ?: return

        val buttonIds = listOf(
            R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
            R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10
        )

        val buttons = buttonIds.map { id -> fragmentView.findViewById<Button>(id) }.toTypedArray()
        buttons.shuffle()

        var cardNumber = MainActivity.startNumber
        for (i in buttons.indices step 2) {
            buttons[i]?.text = cardNumber.toString()
            buttons[i + 1]?.text = cardNumber.toString()
            cardNumber++
        }

        MainActivity.playerScore = 50
        currentScoreTextView.text = MainActivity.playerScore.toString()

        var opened1: Button? = null
        var opened2: Button? = null
        var matchedPairs = 0
        val totalPairs = buttons.size / 2

        buttons.forEach { button ->
            button?.setOnClickListener {
                button.setTextColor(Color.BLACK)
                button.isEnabled = false

                if (opened1 == null) {
                    opened1 = button
                } else {
                    opened2 = button

                    val isMatch = opened1?.text == opened2?.text
                    updateScoreAndColor(isMatch, opened1, opened2, currentScoreTextView)

                    if (isMatch) {
                        matchedPairs++
                        if (matchedPairs == totalPairs) navigateToScoreScreen()
                    } else if (MainActivity.playerScore <= 0) {
                        navigateToScoreScreen()
                    }

                    resetOpenedButtonsDelayed(isMatch, opened1, opened2)
                    opened1 = null
                    opened2 = null
                }
            }
        }
    }

    private fun updateScoreAndColor(
        isMatch: Boolean,
        button1: Button?,
        button2: Button?,
        scoreView: TextView
    ) {
        MainActivity.playerScore += if (isMatch) 10 else -5

        scoreView.text = MainActivity.playerScore.toString()
        scoreView.setTextColor(if (isMatch) Color.parseColor("#008000") else Color.RED)

        scoreView.postDelayed({
            scoreView.setTextColor(Color.BLACK)
        }, 1000)

        button1?.setTextColor(if (isMatch) Color.parseColor("#008000") else Color.RED)
        button2?.setTextColor(if (isMatch) Color.parseColor("#008000") else Color.RED)
    }

    private fun resetOpenedButtonsDelayed(isMatch: Boolean, button1: Button?, button2: Button?) {
        if (!isMatch) {
            button1?.postDelayed({
                button1?.setTextColor(Color.TRANSPARENT)
                button2?.setTextColor(Color.TRANSPARENT)
                button1?.isEnabled = true
                button2?.isEnabled = true
            }, 1000)
        }
    }

    private fun navigateToScoreScreen() {
        (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNav).selectedItemId =
            R.id.score_menu
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            pageSatu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}