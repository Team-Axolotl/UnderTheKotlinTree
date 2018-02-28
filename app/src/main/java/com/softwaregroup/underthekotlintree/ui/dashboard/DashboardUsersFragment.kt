package com.softwaregroup.underthekotlintree.ui.dashboard

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.User
import com.softwaregroup.underthekotlintree.model.UserFetchData
import com.softwaregroup.underthekotlintree.model.UserStatusMap
import com.softwaregroup.underthekotlintree.net.*
import com.softwaregroup.underthekotlintree.ui.dashboard.userInfo.UserGeneralInfoFragment
import com.softwaregroup.underthekotlintree.ui.dashboard.userInfo.UserInfoRootFragment
import com.softwaregroup.underthekotlintree.util.showErrorMessage
import com.softwaregroup.underthekotlintree.util.toast
import kotlinx.android.synthetic.main.fragment_dashboard_users.*

class DashboardUsersFragment : Fragment() {

    companion object {
        fun newInstance(): DashboardUsersFragment = DashboardUsersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_dashboard_users, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingIndicator()
        HttpAsyncTask<UserFetchData> { response ->
            hideLoadingIndicator()
            val userData: UserFetchData? = response.result

            if (userData?.user != null){
                usersRecyclerView.adapter = UserAdapter(activity.layoutInflater, userData.user)
                // todo - update data, instead of re-creating adapter in case more than one call
            } else{
                activity.toast("Fuck my life with a cattle prod on fire")
            }
        }.execute(UT5_SERVICE.userFetch(getUserFetchRequest()))
    }

    private fun showLoadingIndicator() {
        usersProgress.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        usersProgress.visibility = View.GONE
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

    private fun openUserFragment(boundUser: User) {
        activity.supportFragmentManager.beginTransaction().replace(R.id.dashboardFragmentContainer, UserInfoRootFragment.newInstance(boundUser)).commit()
    }


    inner class UserAdapter(
            private val inflater: LayoutInflater,
            private val users: MutableList<User>
    ) : RecyclerView.Adapter<UserAdapter.UserHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
                UserHolder(inflater.inflate(R.layout.row_user, parent, false))

        override fun getItemCount() = users.size

        override fun onBindViewHolder(holder: UserHolder?, position: Int) {
            Log.wtf("TAAAG", "SAdasdadsd   " + position)
            holder!!.bind(users[position])
        }


        inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            init { itemView.setOnClickListener(this) }

            private val statusIcon: ImageView = itemView.findViewById(R.id.userStatus)
            private val nameView: TextView = itemView.findViewById(R.id.userName)
            private val branchView: TextView = itemView.findViewById(R.id.userBranch)
            private var boundUser: User? = null

            fun bind(user: User) {
                this.statusIcon.setImageDrawable(ContextCompat.getDrawable(statusIcon.context, getUserStatusIcon(user)))
                this.nameView.text = user.userName
                this.branchView.text = "${user.branches} | ${user.roles}"
                this.boundUser = user
            }

            @DrawableRes
            private fun getUserStatusIcon(user: User): Int {
                return if (user.failed != null) {
                    R.drawable.ic_locked
                } else {
                    // todo - handle possible nullity. Maybe add an 'unknown/?' status icon?
                    UserStatusMap.statusIdIconRes[user.statusId]!!
                }
            }

            override fun onClick(v: View?) {
                if (boundUser != null)
                    this@DashboardUsersFragment.openUserFragment(boundUser!!)
            }
        }
    }
}


