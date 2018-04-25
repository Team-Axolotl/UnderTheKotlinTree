package com.softwaregroup.underthekotlintree.ui.dashboard.userInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.softwaregroup.underthekotlintree.R
import com.softwaregroup.underthekotlintree.model.UserGetData
import com.softwaregroup.underthekotlintree.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_user_general_info.*

private const val KEY_USER_GET_DATA = "KEY_USER_GET_DATA"

class UserGeneralInfoFragment : BaseFragment() {
    override fun getTitle() = "UserGeneralInfoFragment"

    private lateinit var user: UserGetData

    companion object {
        const val TAG = "UserGeneralInfoFragment"
        fun newInstance(user: UserGetData): UserGeneralInfoFragment {
            val fragment = UserGeneralInfoFragment()

            val bundle = Bundle()
            bundle.putSerializable(KEY_USER_GET_DATA, user)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.user = arguments[KEY_USER_GET_DATA] as UserGetData
        return inflater!!.inflate(R.layout.fragment_user_general_info, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userInfoFirstName.setText(user.person.firstName)
        userInfoLastName.setText(user.person.lastName)

        userInfoComputerModel.setText(user.person.computerModel)
        userInfoPhoneModel.setText(user.person.phoneModel)

        if (user.person.gender != null) {
            if (user.person.gender.equals("male", true))
                userInfoGenderRadio.check(R.id.userInfoGenderMale)
            else if (user.person.gender.equals("female", true))
                userInfoGenderRadio.check(R.id.userInfoGenderFemale)
        }

        // todo - not right data to display
        if (user.person.bioId != null)
            userInfoBioText.text = user.person.bioId.toString()
        else
            userInfoBioText.text = "No bio data for user"
    }
}
