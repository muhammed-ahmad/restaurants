package com.falcon.restaurants.presentation.common.di.activity
import com.falcon.restaurants.presentation.common.di.presentation.PresentationComponent
import com.falcon.restaurants.presentation.common.di.presentation.PresentationModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
   fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent
}
