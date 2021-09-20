package com.falcon.restaurants.screens.common.dialogs;

import androidx.fragment.app.DialogFragment;
import com.falcon.restaurants.common.di.presentation.PresentationComponent;
import com.falcon.restaurants.common.di.presentation.PresentationModule;
import com.falcon.restaurants.screens.common.BaseActivity;

public class BaseDialog extends DialogFragment {

    private PresentationComponent presentationComponent;
    public PresentationComponent getPresentationComponent(){
        if(presentationComponent == null){
            presentationComponent =
                    ((BaseActivity)requireActivity()).getActivityComponent()
                            .newPresentationComponent(new PresentationModule());
        }
        return presentationComponent;
    }
}
