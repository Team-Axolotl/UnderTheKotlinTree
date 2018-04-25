package com.softwaregroup.underthekotlintree.ui

import android.support.v4.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun getTitle() : String

}
