package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario =
            new ActivityScenarioRule<>(MainActivity.class);

    //test from pdf

    @Test
    public void testAddCity() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearCity() {
        // Add first city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Add second city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Clear all
        onView(withId(R.id.button_clear)).perform(click());

        // Both should no longer exist
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }

    @Test
    public void testListView() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Verify first list item text
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .check(matches(withText("Edmonton")));
    }


    /**
     * Test 1 — Check whether the activity correctly switched
     */
    @Test
    public void testActivitySwitch() {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Calgary"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on the city in list to open detail activity
        onView(withText("Calgary")).perform(click());

        // Verify that the new activity (CityDetailActivity) is displayed
        onView(withId(R.id.text_cityName)).check(matches(isDisplayed()));
    }

    /**
     * Test 2 — Test whether the city name is consistent across activities
     */
    @Test
    public void testCityNameConsistency() {
        // Add Toronto
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Toronto"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Open the city detail
        onView(withText("Toronto")).perform(click());

        // Check if same name is shown in detail activity
        onView(withId(R.id.text_cityName)).check(matches(withText("Toronto")));
    }

    /**
     * Test 3 — Test the Back button functionality
     */
    @Test
    public void testBackButtonFunctionality() {
        // Add Montreal
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Montreal"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Open detail activity:
        onView(withText("Montreal")).perform(click());

        // Press the BACK button in the detail screen:
        onView(withId(R.id.button_back)).perform(click());

        // Verify that we returned to MainActivity (ListView is now visible again)
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}
