package com.falcon.restaurants.presentation.view.mealdetail

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import com.falcon.restaurants.R
import org.junit.Test

class MealDetailsActivityTest {

    lateinit var scenario: ActivityScenario<MealDetailsActivity>

    @Test
    fun hasItemWithText() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), MealDetailsActivity::class.java)
            .putExtra(MealDetailsActivity.MEAL_ID, "7")
        scenario = ActivityScenario.launch(intent)

        Espresso.onView(ViewMatchers.withId(R.id.nameTxt))
            .check(matches(ViewMatchers.withText("chicken salad")))

    }

    @Test
    fun hasAnotherItemWithText() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), MealDetailsActivity::class.java)
            .putExtra(MealDetailsActivity.MEAL_ID, "2")
        scenario = ActivityScenario.launch(intent)

        Espresso.onView(ViewMatchers.withId(R.id.nameTxt))
            .check(matches(ViewMatchers.withText("brost burger shata")))

    }

}