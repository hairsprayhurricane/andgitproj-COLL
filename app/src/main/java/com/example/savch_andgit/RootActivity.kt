package com.example.savch_andgit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.appbar.MaterialToolbar

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.musicFragment, R.id.favoritesFragment))
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
        val bottom = findViewById<BottomNavigationView>(R.id.bottomNav)
        NavigationUI.setupWithNavController(bottom, navController)
    }
}
