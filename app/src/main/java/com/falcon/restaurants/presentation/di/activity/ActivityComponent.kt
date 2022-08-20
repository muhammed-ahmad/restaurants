package com.falcon.restaurants.presentation.di.activity
import com.falcon.restaurants.presentation.di.presentation.PresentationComponent
import com.falcon.restaurants.presentation.di.presentation.PresentationModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
   fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent
}
