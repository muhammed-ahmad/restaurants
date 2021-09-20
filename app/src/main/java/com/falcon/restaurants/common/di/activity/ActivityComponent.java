package com.falcon.restaurants.common.di.activity;

import com.falcon.restaurants.common.di.presentation.PresentationComponent;
import com.falcon.restaurants.common.di.presentation.PresentationModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

   PresentationComponent newPresentationComponent(PresentationModule presentationModule);

}
