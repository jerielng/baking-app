package com.udacity.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    private String mRecipeName;
    private ArrayList<Ingredient> mIngredientsList;
    private ArrayList<RecipeStep> mRecipeStepList;

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public Recipe(String recipeName, ArrayList<Ingredient> ingredientsList,
                  ArrayList<RecipeStep> recipeStepList) {
        mRecipeName = recipeName;
        mIngredientsList = ingredientsList;
        mRecipeStepList = recipeStepList;
    }

    /* I used http://www.parcelabler.com/ for help on creating this constructor. */
    private Recipe(Parcel in) {
        mRecipeName = in.readString();
        if (in.readByte() == 0x01) {
            mIngredientsList = new ArrayList<>();
            in.readList(mIngredientsList, Ingredient.class.getClassLoader());
        } else {
            mIngredientsList = null;
        }
        if (in.readByte() == 0x01) {
            mRecipeStepList = new ArrayList<>();
            in.readList(mRecipeStepList, RecipeStep.class.getClassLoader());
        } else {
            mRecipeStepList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /* I used http://www.parcelabler.com/ for help on creating this method. */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRecipeName);
        if (mIngredientsList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mIngredientsList);
        }
        if (mRecipeStepList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mRecipeStepList);
        }
    }

    public String getmRecipeName() {
        return mRecipeName;
    }

    public void setmRecipeName(String mRecipeName) {
        this.mRecipeName = mRecipeName;
    }

    public ArrayList<Ingredient> getmIngredientsList() {
        return mIngredientsList;
    }

    public void setmIngredientsList(ArrayList<Ingredient> mIngredientsList) {
        this.mIngredientsList = mIngredientsList;
    }

    public ArrayList<RecipeStep> getmRecipeStepList() {
        return mRecipeStepList;
    }

    public void setmRecipeStepList(ArrayList<RecipeStep> mRecipeStepList) {
        this.mRecipeStepList = mRecipeStepList;
    }
}