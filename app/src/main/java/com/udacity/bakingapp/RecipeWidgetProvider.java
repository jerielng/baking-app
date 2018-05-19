package com.udacity.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.udacity.bakingapp.model.Ingredient;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    @BindView(R.id.widget_recipe_text) TextView mRecipeNameText;
    @BindView(R.id.widget_ingredients_text) TextView mIngredientsText;

    private static String mRecipeName = "";
    private static ArrayList<Ingredient> mIngredientsList = new ArrayList<Ingredient>();

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetRecipeText = mRecipeName;
        String ingredientsList = "\n";
        for (Ingredient i : mIngredientsList) {
            ingredientsList += "\n(" + i.getmQuantity() + " " + i.getmMeasure()
                    + ")\t\t" + i.getmIngredientName();
        }
        // Construct the RemoteViews object
        RemoteViews views =
                new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        if (!mRecipeName.equals("") && !mIngredientsList.isEmpty()) {
            views.setTextViewText(R.id.widget_recipe_text, widgetRecipeText);
            views.setTextViewText(R.id.widget_ingredients_text, ingredientsList);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String recipeSearchTag = context.getString(R.string.recipe_name);
        String ingredientsSearchTag = context.getString(R.string.ingredients_text);
        if (intent.hasExtra(recipeSearchTag) && intent.hasExtra(ingredientsSearchTag)) {
            mRecipeName = intent.getStringExtra(recipeSearchTag);
            mIngredientsList = intent.getParcelableArrayListExtra(ingredientsSearchTag);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context.getPackageName(),
                    RecipeWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }
}

