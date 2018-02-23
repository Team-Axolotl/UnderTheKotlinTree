package com.softwaregroup.underthekotlintree.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.UserFetchData
import com.softwaregroup.underthekotlintree.net.*
import com.softwaregroup.underthekotlintree.util.toast
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.view_toolbar_standard.*

class DashboardActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        toolbar.title = title

        showLoading()
        HttpAsyncTask<JsonRpcResponse<UserFetchData>> { response ->
            hideLoading()
            val userData: UserFetchData? = processResponse(response)
            Log.wtf("TAAAG_D", "user count = ${userData!!.user.size}")
        }.execute(UT5_SERVICE.userFetch(getUserFetchRequest()))

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

    private fun hideLoading() = toast("Loaded!")
    private fun showLoading() = toast("Loading...")

    /** Unwrap the [UserFetchData] from withing the [response]. Show and error message and return null if the request was not successful*/
    private fun processResponse(response: HttpCallResponse<JsonRpcResponse<UserFetchData>>): UserFetchData? {
        return if (response.isSuccess && response.result!!.error == null) {
            response.result.result
        } else {
            showErrorMessage(when (response.isSuccess) {
                true -> response.result!!.error!!.message
                false -> getString(response.errorCode!!.messageStringId)
            })
            null
        }
    }

    /** Generate a [JsonRpcRequest] for the [Ut5Service.userFetch] */
    private fun getUserFetchRequest(): JsonRpcRequest {
        return JsonRpcRequest(
                method = REQUEST_USER_USER_FETCH,
                params = mapOf(
                        "pageSize" to 100,
                        "pageNumber" to 1,
                        "breadcrumbs" to "[]",
                        "customSearch" to mapOf("field" to "userName", "value" to "")
                ))
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_users -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
