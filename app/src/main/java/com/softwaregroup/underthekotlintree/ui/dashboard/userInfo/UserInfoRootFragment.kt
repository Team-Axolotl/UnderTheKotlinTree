package com.softwaregroup.underthekotlintree.ui.dashboard.userInfo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.UserBasicInfo
import com.softwaregroup.underthekotlintree.model.UserGetData
import com.softwaregroup.underthekotlintree.net.*
import com.softwaregroup.underthekotlintree.util.showHttpErrorMessage
import kotlinx.android.synthetic.main.fragment_user_info_root.*

class UserInfoRootFragment : Fragment() {

    private lateinit var userBasicInfo: UserBasicInfo
    private lateinit var userData: UserGetData

    companion object {
        fun newInstance(user: UserBasicInfo): UserInfoRootFragment {
            val frag = UserInfoRootFragment()
            frag.userBasicInfo = user
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_user_info_root, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HttpAsyncTask<UserGetData> { response ->

            if (response.isSuccess) {
                response.result!!
                userData = response.result

                userInfoPager.adapter = object : FragmentPagerAdapter(fragmentManager) {
                    val pages = listOf<Fragment>(
                            UserGeneralInfoFragment.newInstance(response.result)
                            //todo - other pages
                    )

                    override fun getItem(position: Int): Fragment = pages[position]
                    override fun getCount(): Int = pages.size
                    override fun getPageTitle(position: Int): String = "TODO: $position"
                }
            } else {
                activity.showHttpErrorMessage(response)
            }
        }.execute(UT5_SERVICE.userGet(getUserGetRequest()))
    }


    /** Generate a [JsonRpcRequest] for the [Ut5Service.userFetch] */
    private fun getUserGetRequest(): JsonRpcRequest {
        return JsonRpcRequest(
                method = REQUEST_USER_USER_GET,
                params = mapOf("actorId" to "${userBasicInfo.actorId}")
        )
    }
}