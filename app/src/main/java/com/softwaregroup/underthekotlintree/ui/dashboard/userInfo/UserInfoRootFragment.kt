package com.softwaregroup.underthekotlintree.ui.dashboard.userInfo

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.UserBasicInfo
import com.softwaregroup.underthekotlintree.model.UserGetData
import com.softwaregroup.underthekotlintree.net.*
import com.softwaregroup.underthekotlintree.ui.BaseFragment
import com.softwaregroup.underthekotlintree.util.showHttpErrorMessage
import kotlinx.android.synthetic.main.fragment_user_info_root.*

private const val KEY_USER_INFO = "KEY_USER_INFO"

class UserInfoRootFragment : BaseFragment() {
    override fun getTitle(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var userBasicInfo: UserBasicInfo
    private lateinit var userData: UserGetData

    companion object {
        const val TAG = "UserInfoRootFragment"

        fun newInstance(user: UserBasicInfo): UserInfoRootFragment {
            val frag = UserInfoRootFragment()

            val bundle = Bundle()
            bundle.putSerializable(KEY_USER_INFO, user)
            frag.arguments = bundle

            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.userBasicInfo = arguments[KEY_USER_INFO] as UserBasicInfo
        return inflater!!.inflate(R.layout.fragment_user_info_root, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        HttpAsyncTask<UserGetData> { response ->
            if (response.isSuccess) {
                response.result!!
                userData = response.result

                val pagerFragments = listOf<BaseFragment>(
                        UserGeneralInfoFragment.newInstance(userData)
                )

                userInfoPager.adapter = SimpleFragmentPagerAdapter(pagerFragments, childFragmentManager)
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

    inner class SimpleFragmentPagerAdapter(
            private val pages: List<BaseFragment>,
            fragmentManager: FragmentManager
    ) : FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): BaseFragment = pages[position]
        override fun getCount(): Int = pages.size
        override fun getPageTitle(position: Int): String = getItem(position).getTitle()
    }
}