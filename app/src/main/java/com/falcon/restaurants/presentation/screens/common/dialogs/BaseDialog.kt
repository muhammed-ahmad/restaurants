package com.falcon.restaurants.presentation.screens.common.dialogs;
import androidx.fragment.app.DialogFragment;
import com.falcon.restaurants.presentation.common.di.presentation.PresentationModule;
import com.falcon.restaurants.presentation.screens.common.BaseActivity;

open class BaseDialog : DialogFragment() {

    val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent
            .newPresentationComponent(PresentationModule())
    }

}
