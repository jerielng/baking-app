package com.udacity.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.RecipeStep;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.click;

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    Context mContext = InstrumentationRegistry.getTargetContext();

    Ingredient testIngredient = new Ingredient(5, "CUP", "Spice");
    RecipeStep testStep = new RecipeStep(3,
            "Test Short Description",
            "Test Description",
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/" +
                    "April/58ffd974_-intro-creampie/-intro-creampie.mp4", "");

    ArrayList<Ingredient> testIngredients = new ArrayList<Ingredient>();
    ArrayList<RecipeStep> testSteps = new ArrayList<RecipeStep>();

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule =
            new ActivityTestRule(DetailActivity.class, false, false);

    @Test
    public void testIngredientsView() {
        testIngredients.add(testIngredient);
        testSteps.add(testStep);
        Recipe testRecipe =
                new Recipe("Cake", 8, testIngredients, testSteps, "");
        Intent testIntent = new Intent();
        testIntent.putExtra(mContext.getString(R.string.recipe_object), testRecipe);
        mActivityRule.launchActivity(testIntent);
        onView(withId(R.id.ingredients_text)).perform().check(matches(isDisplayed()));
    }

    @Test
    public void checkStepElement() {
        testIngredients.add(testIngredient);
        testSteps.add(testStep);
        Recipe testRecipe =
                new Recipe("Cake", 8, testIngredients, testSteps, "");
        Intent testIntent = new Intent();
        testIntent.putExtra(mContext.getString(R.string.recipe_object), testRecipe);
        mActivityRule.launchActivity(testIntent);
        onView(withContentDescription(testStep.getmShortDescription()))
                .perform().check(matches(isDisplayed()));
    }

    @Test
    public void checkStepOpen() {
        testIngredients.add(testIngredient);
        testSteps.add(testStep);
        Recipe testRecipe =
                new Recipe("Cake", 8, testIngredients, testSteps, "");
        Intent testIntent = new Intent();
        testIntent.putExtra(mContext.getString(R.string.recipe_object), testRecipe);
        mActivityRule.launchActivity(testIntent);
        onView(withContentDescription(testStep.getmShortDescription()))
                .perform(click());
        onView(withId(R.id.video_container)).check(matches(isDisplayed()));
    }
}
