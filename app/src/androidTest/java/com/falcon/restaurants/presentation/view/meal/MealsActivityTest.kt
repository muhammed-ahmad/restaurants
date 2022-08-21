package com.falcon.restaurants.presentation.view.meal

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.PerformException
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.falcon.restaurants.R
import com.falcon.restaurants.presentation.EspressoTestUtils
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MealsActivityTest {

    lateinit var scenario: ActivityScenario<MealsActivity>

    @Test
    fun mealsWithRestaurantId1HasItemWithText() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), MealsActivity::class.java)
            .putExtra(MealsActivity.RESTAURANT_ID, "1")
        scenario = launch(intent)

        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText("brost burger shata"))
                )
            )
    }

    @Test
    fun mealsWithRestaurantId1hasTheseTextsOnTheseIndices() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), MealsActivity::class.java)
            .putExtra(MealsActivity.RESTAURANT_ID, "1")
        scenario = launch(intent)


        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
            .check(
                ViewAssertions.matches(
                    EspressoTestUtils.atPosition(
                        1,
                        ViewMatchers.hasDescendant(ViewMatchers.withText("brost salad"))
                    )
                )
            )

        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(6))
            .check(
                ViewAssertions.matches(
                    EspressoTestUtils.atPosition(
                        6,
                        ViewMatchers.hasDescendant(ViewMatchers.withText("corn brost"))
                    )
                )
            )
    }

    @Test
    fun mealsWithRestaurantId7HasItemWithText() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), MealsActivity::class.java)
            .putExtra(MealsActivity.RESTAURANT_ID, "7")
        scenario = launch(intent)

        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText("salmon rosemary"))
                )
            )
    }

    @Test(expected = PerformException::class)
    fun mealsWithRestaurantId7HasNotItemWithText() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), MealsActivity::class.java)
            .putExtra(MealsActivity.RESTAURANT_ID, "7")
        scenario = launch(intent)

        Espresso.onView(ViewMatchers.withId(R.id.recyclerview))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(""))
                )
            )
    }

}