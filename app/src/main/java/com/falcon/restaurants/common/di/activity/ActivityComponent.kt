package com.falcon.restaurants.common.di.activity
import com.falcon.restaurants.common.di.presentation.PresentationComponent
import com.falcon.restaurants.common.di.presentation.PresentationModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
   fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent
}
