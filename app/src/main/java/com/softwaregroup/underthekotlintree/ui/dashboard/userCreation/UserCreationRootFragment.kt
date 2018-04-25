package com.softwaregroup.underthekotlintree.ui.dashboard.userCreation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.UserGetData
import com.softwaregroup.underthekotlintree.net.HttpAsyncTask
import com.softwaregroup.underthekotlintree.net.JsonRpcRequest
import com.softwaregroup.underthekotlintree.net.UT5_SERVICE
import com.softwaregroup.underthekotlintree.ui.BaseFragment
import com.softwaregroup.underthekotlintree.ui.dashboard.userInfo.UserGeneralInfoFragment
import com.softwaregroup.underthekotlintree.util.showHttpErrorMessage
import kotlinx.android.synthetic.main.fragment_user_info_root.*

class UserCreationRootFragment : BaseFragment() {

    override fun getTitle(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance() = UserCreationRootFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HttpAsyncTask<UserGetData> { response ->

            if (response.isSuccess) {
                response.result!!
                userInfoPager.adapter = object : FragmentPagerAdapter(fragmentManager) {
                    val pages = listOf<Fragment>(
                            UserGeneralInfoFragment.newInstance(response.result)
                    )

                    override fun getItem(position: Int): Fragment = pages[position]
                    override fun getCount(): Int = pages.size
                    override fun getPageTitle(position: Int): String = "TODO: $position"
                }
            } else {
                activity.showHttpErrorMessage(response)
            }
        }.execute(UT5_SERVICE.userGet(getUserCreateRequest()))

    }

    private fun getUserCreateRequest(): JsonRpcRequest {
        return JsonRpcRequest(
                method = "",
                params = mapOf("actorId" to "${1}")
        )
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_user_info_root, container, false)
    }

}