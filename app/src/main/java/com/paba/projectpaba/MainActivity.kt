package com.paba.projectpaba

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.frameContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fragmentManager = supportFragmentManager
        val initialFragment = pageSatu()
        fragmentManager.beginTransaction()
            .add(R.id.frameContainer, initialFragment, pageSatu::class.java.simpleName)
            .commit()

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavView.setOnItemSelectedListener {
            val fragment = when (it.itemId) {
                R.id.game_menu -> pageSatu()
                R.id.score_menu -> pageDua()
                R.id.setting_menu -> pageTiga()
                else -> null
            }
            fragment?.let {
                fragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, it, it::class.java.simpleName)
                    .commit()
                true
            } ?: false
        }
    }

    companion object {
        var startNumber = 1
        var playerScore = 50
    }
}