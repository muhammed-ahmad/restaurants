package com.falcon.restaurants.screens.common.dialogs;

import androidx.fragment.app.DialogFragment;
import com.falcon.restaurants.common.di.presentation.PresentationComponent;
import com.falcon.restaurants.common.di.presentation.PresentationModule;
import com.falcon.restaurants.screens.common.BaseActivity;

open class BaseDialog : DialogFragment() {

    val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent
            .newPresentationComponent(PresentationModule())
    }

}
