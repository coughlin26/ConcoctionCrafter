/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matt.concoctioncrafter.data.Recipe
import com.example.matt.concoctioncrafter.data.RecipeViewModel
import io.reactivex.disposables.Disposable

class ChooseRecipeActivity : AppCompatActivity() {
    private var _recipeList: RecyclerView? = null
    private var _recipeAdapter: RecipeListAdapter? = null
    private var _recipeClickSubscription: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_chooser)

        val recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)

        _recipeList = findViewById(R.id.recipe_list)
        _recipeList!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        _recipeAdapter = RecipeListAdapter(recipeViewModel)

        recipeViewModel.recipeList.observe(this, Observer<List<Recipe>> { recipeList ->
            _recipeAdapter!!.loadItems(recipeList)
            _recipeAdapter!!.notifyDataSetChanged()
        })

        _recipeList!!.adapter = _recipeAdapter

        _recipeClickSubscription = _recipeAdapter!!.recipeClicks.subscribe { recipe ->
            val intent = Intent()
            intent.putExtra(MainActivity.RECIPE_KEY, recipe)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onDestroy() {
        _recipeClickSubscription!!.dispose()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }
}
