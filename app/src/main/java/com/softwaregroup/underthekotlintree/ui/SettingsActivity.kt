package com.softwaregroup.underthekotlintree.ui

import android.os.Bundle
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.net.UT5_SERVICE
import com.softwaregroup.underthekotlintree.net.createUt5Service
import com.softwaregroup.underthekotlintree.storage.baseUrl
import com.softwaregroup.underthekotlintree.util.startActivity
import com.softwaregroup.underthekotlintree.util.toast
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.view_toolbar_standard.*
import okhttp3.HttpUrl

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar.setTitle(R.string.title_activity_settings)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_navigation_back)
        toolbar.setNavigationOnClickListener{ onBackPressed() }

        settingsHostInput.setText(baseUrl.host())
        settingsPortInput.setText(baseUrl.port().toString())
        settingsUseHttp.isChecked = baseUrl.scheme() == "https"

        settingsButtonConfirm.setOnClickListener{
            if (validateInput()){
                val scheme = if (settingsUseHttp.isChecked) "https" else "http"
                baseUrl = HttpUrl.Builder()
                        .scheme(scheme)
                        .host(settingsHostInput.text.toString())
                        .port(settingsPortInput.text.toString().toInt())
                        .build()

                UT5_SERVICE = createUt5Service() // recreate to update url!

                toast(R.string.message_configuration_saved)
                startActivity(LoginActivity::class.java)
                finish()
            }
        }
    }

    private fun validateInput(): Boolean {
        return when{
            settingsHostInput.text.isBlank() -> {
                settingsHostInput.error = getString(R.string.error_invalid_input)
                settingsHostInput.requestFocus()
                false
            }
            settingsPortInput.text.isBlank() -> {
                settingsPortInput.error = getString(R.string.error_invalid_input)
                settingsPortInput.requestFocus()
                false
            }
            else -> true
        }}

    override fun onBackPressed() {
        // start the LoginActivity a-new because it has the no-history flag and doesn't retain after exit.
        startActivity(LoginActivity::class.java)
    }
}