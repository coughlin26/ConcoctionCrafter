/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.matt.concoctioncrafter.data.Recipe;
import com.example.matt.concoctioncrafter.data.RecipeViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.subjects.PublishSubject;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private List<Recipe> _recipes;
    private PublishSubject<Recipe> _recipeSubject = PublishSubject.create();
    private RecipeViewModel _recipeViewModel;

    RecipeListAdapter(final RecipeViewModel recipeViewModel) {
        _recipeViewModel = recipeViewModel;
    }

    public void loadItems(final List<Recipe> recipes) {
        _recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (_recipes != null) {
            holder._title.setText(_recipes.get(position).get_recipeName());
            holder._viewGroup.setOnClickListener(v -> _recipeSubject.onNext(_recipes.get(position)));
            holder._deleteButton.setOnClickListener(v -> _recipeViewModel.delete(_recipes.get(position)));
        }
    }

    protected PublishSubject<Recipe> getRecipeClicks() {
        return _recipeSubject;
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (_recipes != null) {
            count = _recipes.size();
        }

        return count;
    }

    protected static class RecipeViewHolder extends RecyclerView.ViewHolder {
        private ViewGroup _viewGroup;
        private TextView _title;
        private ImageButton _deleteButton;

        RecipeViewHolder(ViewGroup viewGroup) {
            super(viewGroup);
            _viewGroup = viewGroup;
            _title = _viewGroup.findViewById(R.id.recipe_name);
            _deleteButton = _viewGroup.findViewById(R.id.delete_button);
        }
    }
}
