package com.falcon.restaurants.presentation.view.common.dialogs;
import androidx.fragment.app.DialogFragment;
import com.falcon.restaurants.presentation.di.presentation.PresentationModule;
import com.falcon.restaurants.presentation.view.common.BaseActivity;

open class BaseDialog : DialogFragment() {

    val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent
            .newPresentationComponent(PresentationModule())
    }

}
