package com.falcon.restaurants.screens.common.dialogs
import androidx.fragment.app.FragmentManager
import javax.inject.Inject

class DialogsNavigator @Inject constructor (val fragmentManager: FragmentManager) {

    fun showServerErrorDialog(error: String) {
        val fragment: ServerErrorDialogFragment = ServerErrorDialogFragment.newInstance(error)
        fragment.show(fragmentManager, "fragment_server_error")
    }

}
