/* Copyright Matthew Coughlin 2018, 2019, 2020 */

package dev.mattcoughlin.concoctioncrafter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.mattcoughlin.concoctioncrafter.data.Recipe
import dev.mattcoughlin.concoctioncrafter.data.RecipeViewModel
import io.reactivex.subjects.PublishSubject

class RecipeListAdapter internal constructor(private val _recipeViewModel: RecipeViewModel) :
        RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {
    val recipeClicks: PublishSubject<Recipe> = PublishSubject.create()
    private var _recipes = emptyList<Recipe>()

    fun loadItems(recipes: List<Recipe>) {
        _recipes = recipes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.recipe_item,
                parent,
                false) as ViewGroup)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.title.text = _recipes[position].recipeName
        holder.viewGroup.setOnClickListener { recipeClicks.onNext(_recipes[position]) }
        holder.deleteButton.setOnClickListener { _recipeViewModel.delete(_recipes[position]) }
    }

    override fun getItemCount() = _recipes.size

    inner class RecipeViewHolder(val viewGroup: ViewGroup) : RecyclerView.ViewHolder(viewGroup) {
        val title: TextView = viewGroup.findViewById(R.id.recipe_name)
        val deleteButton: ImageButton = viewGroup.findViewById(R.id.delete_button)
    }
}
