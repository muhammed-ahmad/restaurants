package com.falcon.restaurants.presentation.view.restaurant

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.falcon.restaurants.R
import com.falcon.restaurants.presentation.EspressoTestUtils
import junit.framework.AssertionFailedError
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class RestaurantsActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(RestaurantsActivity::class.java)

    @Test
    fun hasItemWithText() {
        onView(withId(R.id.recyclerview))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Abo Foda Fishes"))
                )
            )
    }

    @Test(expected = PerformException::class)
    fun hasNotItemWithText() {
        onView(withId(R.id.recyclerview))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText(""))
                )
            )
    }

    @Test(expected = PerformException::class)
    fun hasNotThisPosition() {
        onView(withId(R.id.recyclerview))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(40, scrollTo()))
    }

    @Test
    fun hasTheseTextsOnTheseIndices() {
        onView(withId(R.id.recyclerview))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(EspressoTestUtils.atPosition(0, hasDescendant(withText("Abo Foda Fishes")))))
        onView(withId(R.id.recyclerview))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(4))
            .check(matches(EspressoTestUtils.atPosition(4, hasDescendant(withText("Al Masry Brost")))))
    }

    @Test(expected = AssertionFailedError::class)
    fun hasNotThisTextsOnThisIndex() {
        onView(withId(R.id.recyclerview))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(4))
            .check(matches(EspressoTestUtils.atPosition(4, hasDescendant(withText("Abo Foda Fishes")))))
    }

}