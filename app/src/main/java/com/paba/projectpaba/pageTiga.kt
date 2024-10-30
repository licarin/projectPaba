package com.paba.projectpaba

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class pageTiga : Fragment() {
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page_tiga, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startEditText = view.findViewById<EditText>(R.id.start)
        val startGameButton = view.findViewById<Button>(R.id.btnStartGame)

        startGameButton.setOnClickListener {
            val nextStartNumber = startEditText.text.toString().toIntOrNull() ?: 1
            MainActivity.startNumber = nextStartNumber
            MainActivity.playerScore = 50

            val bottomNav = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNav)
            bottomNav.selectedItemId = R.id.game_menu
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            pageTiga().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}