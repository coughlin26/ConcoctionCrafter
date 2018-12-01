/* Copyright Matthew Coughlin 2018 */

package com.example.matt.concoctioncrafter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.matt.concoctioncrafter.data.Recipe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private List<Recipe> _recipes;
    private PublishSubject<Recipe> _recipeSubject = PublishSubject.create();

    RecipeListAdapter(final List<Recipe> recipes) {
        _recipes = recipes;
        for (final Recipe recipe : recipes) {
            Log.d("Testing", "Found recipe: " + recipe.getRecipeName());
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder._title.setText(_recipes.get(position).getRecipeName());
        holder._viewGroup.setOnClickListener(v -> _recipeSubject.onNext(_recipes.get(position)));
    }

    public PublishSubject<Recipe> getRecipeClicks() {
        return _recipeSubject;
    }

    @Override
    public int getItemCount() {
        return _recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public ViewGroup _viewGroup;
        public TextView _title;

        RecipeViewHolder(ViewGroup viewGroup) {
            super(viewGroup);
            _viewGroup = viewGroup;
            _title = _viewGroup.findViewById(R.id.recipe_name);
        }
    }
}
