package com.falcon.restaurants.screens.common.dialogs;

import androidx.fragment.app.FragmentManager;
import javax.inject.Inject;

public class DialogsNavigator {

    FragmentManager fragmentManager;

    @Inject
    public DialogsNavigator(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showServerErrorDialog(String error) {
        ServerErrorDialogFragment fragment = ServerErrorDialogFragment.newInstance(error);
        fragment.show(fragmentManager, "fragment_server_error");
    }
}
