package com.softwaregroup.underthekotlintree.ui.dashboard

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.ui.BaseActivity
import com.softwaregroup.underthekotlintree.ui.dashboard.userCreation.UserCreationRootFragment
import com.softwaregroup.underthekotlintree.ui.dashboard.userList.DashboardUserListFragment
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.view_toolbar_standard.*

class DashboardActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        toolbar.title = title

        val toggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        findViewById<View>(R.id.dashboardFillerImage).visibility = View.GONE

        when (item.itemId) {
            R.id.nav_user_list -> openFragment(DashboardUserListFragment.newInstance())
            R.id.nav_user_create -> openFragment(UserCreationRootFragment.newInstance())
            R.id.nav_logout -> {
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.dashboardFragmentContainer, fragment).commit()
    }
}
