/* Copyright Matthew Coughlin 2018, 2019 */

package com.example.matt.concoctioncrafter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.matt.concoctioncrafter.data.Fermentable
import com.example.matt.concoctioncrafter.data.Hop
import com.example.matt.concoctioncrafter.data.Recipe
import com.example.matt.concoctioncrafter.data.RecipeViewModel
import com.google.android.material.tabs.TabLayout
import io.reactivex.subjects.PublishSubject

class MainActivity : AppCompatActivity() {
    val recipeSubject = PublishSubject.create<Recipe>()
    private var _pager: ViewPager? = null
    private var _pageAdapter: MainPageAdapter? = null
    private var _tabLayout: TabLayout? = null
    private var _toolBar: Toolbar? = null
    private var _recipeViewModel: RecipeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _pager = findViewById(R.id.pager)
        _tabLayout = findViewById(R.id.pager_header)
        _toolBar = findViewById(R.id.toolbar)
        _pageAdapter = MainPageAdapter(supportFragmentManager)
        _pager!!.adapter = _pageAdapter
        _recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)

        _tabLayout!!.setupWithViewPager(_pager)
        setSupportActionBar(_toolBar)
    }

    override fun onResume() {
        if (_recipeViewModel?.recipe != null) {
            recipeSubject.onNext(_recipeViewModel?.recipe as Recipe)
        } else {
            recipeSubject.onNext(Recipe())
        }
        super.onResume()
    }

    override fun onPause() {
        _recipeViewModel?.recipe = makeRecipe()
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK &&
                requestCode == REQUEST_CODE &&
                data!!.getParcelableExtra<Parcelable>(RECIPE_KEY) != null) {
            _recipeViewModel?.recipe = data.getParcelableExtra(RECIPE_KEY)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.title_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                if (_pager!!.currentItem == 0) {
                    val recipe = makeRecipe()

                    if (_recipeViewModel?.findByName(recipe.recipeName) != null) {
                        _recipeViewModel?.update(recipe)
                    } else {
                        _recipeViewModel?.insert(recipe)
                    }

                    _recipeViewModel?.recipe = recipe
                    recipeSubject.onNext(recipe)
                } else if (_pager!!.currentItem == 1) {
                    Toast.makeText(this, "Cannot save a recipe on Brew Day", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Invalid page: " + _pager!!.currentItem, Toast.LENGTH_SHORT).show()
                }
                return true
            }
            R.id.action_import -> {
                val chooserIntent = Intent(this, ChooseRecipeActivity::class.java)
                startActivityForResult(chooserIntent, REQUEST_CODE)
                return true
            }
            R.id.action_clear -> {
                recipeSubject.onNext(Recipe())
                return true
            }
            R.id.action_delete -> {
                _recipeViewModel!!.deleteAll()
                return true
            }
            R.id.settings_action -> {
                val settingsIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingsIntent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (_pager!!.currentItem == 0) {
            super.onBackPressed()
        } else {
            _pager!!.currentItem = _pager!!.currentItem - 1
        }
    }

    /**
     * Creates a new Recipe from the views in the Recipe fragment.
     */
    private fun makeRecipe(): Recipe {
        Log.d("TESTING", "Making a recipe")
        val beerName = getTextFromEditText(R.id.name_input)
        val style = getTextFromSpinner(R.id.style_spinner)
        val fermentables = getFermentablesFromList()
        val hops = getHopsFromList()
        val yeast = getTextFromSpinner(R.id.yeast_spinner)

        return Recipe(beerName, style, fermentables, hops, yeast)
    }

    private fun getTextFromSpinner(@IdRes viewId: Int): String {
        return (findViewById<View>(viewId) as Spinner).selectedItem.toString()
    }

    private fun getTextFromEditText(@IdRes viewId: Int): String {
        val view = findViewById<View>(viewId)

        if (view != null) {
            val text = (view as EditText).text.toString()
            return if (text.isEmpty()) "" else text
        }

        return ""
    }

    private fun getFermentablesFromList(): List<Fermentable> {
        val fermentables = ArrayList<Fermentable>()
        val list = findViewById<LinearLayout>(R.id.fermentable_list)

        for (i in 0 until list.childCount) {
            val row = list.getChildAt(i)

            if ("Not Used" != row.findViewById<Spinner>(R.id.spinner).selectedItem.toString()) {
                val amount = row.findViewById<EditText>(R.id.amount).text.toString()
                fermentables.add(Fermentable(row.findViewById<Spinner>(R.id.spinner).selectedItem.toString(),
                        if (amount.isEmpty()) 0f else amount.toFloat()))
            }
        }

        return fermentables
    }

    private fun getHopsFromList(): List<Hop> {
        val hops = ArrayList<Hop>()
        val list = findViewById<LinearLayout>(R.id.hop_list)

        for (i in 0 until list.childCount) {
            val row = list.getChildAt(i)

            if ("Not Used" != row.findViewById<Spinner>(R.id.spinner).selectedItem.toString()) {
                val amount = row.findViewById<EditText>(R.id.amount).text.toString()
                val time = row.findViewById<EditText>(R.id.time).text.toString()
                hops.add(Hop(row.findViewById<Spinner>(R.id.spinner).selectedItem.toString(),
                        if (amount.isEmpty()) 0f else amount.toFloat(),
                        if (time.isEmpty()) -1 else time.toInt()))
            }
        }

        return hops
    }

    companion object {
        const val REQUEST_CODE = 7
        const val RECIPE_KEY = "RECIPE_KEY"
    }
}
