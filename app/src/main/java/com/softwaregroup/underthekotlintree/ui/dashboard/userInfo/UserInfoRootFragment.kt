package com.softwaregroup.underthekotlintree.ui.dashboard.userInfo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.User
import com.softwaregroup.underthekotlintree.model.UserFetchData
import com.softwaregroup.underthekotlintree.model.UserGetData
import com.softwaregroup.underthekotlintree.net.*
import com.softwaregroup.underthekotlintree.util.showErrorMessage
import com.softwaregroup.underthekotlintree.util.toast
import kotlinx.android.synthetic.main.fragment_user_info_root.*

class UserInfoRootFragment : Fragment() {

    private lateinit var user: User

    companion object {
        fun newInstance(user: User): UserInfoRootFragment {
            val frag = UserInfoRootFragment()
            frag.user = user
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_user_info_root, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HttpAsyncTask<JsonRpcResponse<UserGetData>> { response ->
            val userData: UserGetData? = processResponse(response)

            if (userData?.user != null){
                userInfoPager.adapter = object : FragmentPagerAdapter(fragmentManager) {
                    val pages = listOf<Fragment>(
                            UserGeneralInfoFragment.newInstance(userData)
                    )

                    override fun getItem(position: Int): Fragment = pages[position]
                    override fun getCount(): Int = pages.size
                    override fun getPageTitle(position: Int): String = "TODO: $position"
                }
            } else{
                activity.toast("Fuck my life with a cattle prod on fire")
            }
        }.execute(UT5_SERVICE.userGet(getUserGetRequest()))
    }


    /** Generate a [JsonRpcRequest] for the [Ut5Service.userFetch] */
    private fun getUserGetRequest(): JsonRpcRequest {
        return JsonRpcRequest(
                method = REQUEST_USER_USER_GET,
                params = mapOf("actorId" to "${user.actorId}")
        )
    }

    /** Unwrap the [UserFetchData] from withing the [response]. Show and error message and return null if the request was not successful*/
    private fun processResponse(response: HttpCallResponse<JsonRpcResponse<UserGetData>>): UserGetData? {
        return if (response.isSuccess && response.result!!.error == null) {
            response.result.result
        } else {
            activity.showErrorMessage(when (response.isSuccess) {
                true -> response.result!!.error!!.message
                false -> getString(response.errorCode!!.messageStringId)
            })
            null
        }
    }

}