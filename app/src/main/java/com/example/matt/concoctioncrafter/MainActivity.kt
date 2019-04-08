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
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.matt.concoctioncrafter.data.Fermentable
import com.example.matt.concoctioncrafter.data.Hop
import com.example.matt.concoctioncrafter.data.Recipe
import com.example.matt.concoctioncrafter.data.RecipeViewModel
import com.google.android.material.tabs.TabLayout
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.*

class MainActivity : AppCompatActivity() {
    private var _pager: ViewPager? = null
    private var _pageAdapter: FragmentPagerAdapter? = null
    private var _tabLayout: TabLayout? = null
    private var _toolBar: Toolbar? = null
    private var _recipeViewModel: RecipeViewModel? = null
    private val _recipe = PublishSubject.create<Recipe>()

    /**
     * Provides a recipe observable to populate the recipe and brew day fragments.
     *
     * @return The current recipe being viewed
     */
    val recipe: Subject<Recipe>
        get() = _recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _pager = findViewById(R.id.pager)
        _tabLayout = findViewById(R.id.pager_header)
        _toolBar = findViewById(R.id.toolbar)
        _pageAdapter = CustomPageAdapter(supportFragmentManager)
        _pager!!.adapter = _pageAdapter
        _recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)

        _tabLayout!!.setupWithViewPager(_pager)
        setSupportActionBar(_toolBar)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            if (data!!.getParcelableExtra<Parcelable>(RECIPE_KEY) != null) {
                _recipe.onNext(data.getParcelableExtra<Parcelable>(RECIPE_KEY) as Recipe)
            }
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

                    if (_recipeViewModel!!.findByName(recipe._recipeName) != null) {
                        Log.d("Testing", "Updating the recipe")
                        _recipeViewModel!!.update(recipe)
                    } else {
                        Log.d("Testing", "Inserting a new recipe")
                        _recipeViewModel!!.insert(recipe)
                    }
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
     * A custom page adapter.
     */
    private inner class CustomPageAdapter constructor(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
                RecipeFragment()
            } else {
                BrewDayFragment()
            }
        }

        override fun getCount(): Int {
            return NUM_PAGES
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0) {
                getString(R.string.recipe)
            } else {
                getString(R.string.brew_day)
            }
        }
    }

    /**
     * Creates a new Recipe from the views in the Recipe fragment.
     */
    private fun makeRecipe(): Recipe {
        val beerName = getTextFromEditText(R.id.name_input)
        val style = "No Style"
        val grain1 = getTextFromSpinner(R.id.grain_spinner1)
        val grain1Amount = getFloatFromEditText(R.id.amount_input1)
        val grain2 = getTextFromSpinner(R.id.grain_spinner2)
        val grain2Amount = getFloatFromEditText(R.id.amount_input2)
        val grain3 = getTextFromSpinner(R.id.grain_spinner3)
        val grain3Amount = getFloatFromEditText(R.id.amount_input3)
        val grain4 = getTextFromSpinner(R.id.grain_spinner4)
        val grain4Amount = getFloatFromEditText(R.id.amount_input4)
        val hop1 = getTextFromSpinner(R.id.hop_spinner1)
        val hop1Amount = getFloatFromEditText(R.id.hop_amount_input1)
        val hop1Time = getIntFromEditText(R.id.addition_time1)
        val hop2 = getTextFromSpinner(R.id.hop_spinner2)
        val hop2Amount = getFloatFromEditText(R.id.hop_amount_input2)
        val hop2Time = getIntFromEditText(R.id.addition_time2)
        val hop3 = getTextFromSpinner(R.id.hop_spinner3)
        val hop3Amount = getFloatFromEditText(R.id.hop_amount_input3)
        val hop3Time = getIntFromEditText(R.id.addition_time3)
        val hop4 = getTextFromSpinner(R.id.hop_spinner4)
        val hop4Amount = getFloatFromEditText(R.id.hop_amount_input4)
        val hop4Time = getIntFromEditText(R.id.addition_time4)
        val yeast = getTextFromSpinner(R.id.yeast_spinner)

        val fermentableList = ArrayList<Fermentable>()
        val hopList = ArrayList<Hop>()

        fermentableList.add(Fermentable(grain1, grain1Amount))
        fermentableList.add(Fermentable(grain2, grain2Amount))
        fermentableList.add(Fermentable(grain3, grain3Amount))
        fermentableList.add(Fermentable(grain4, grain4Amount))

        hopList.add(Hop(hop1, hop1Amount, hop1Time))
        hopList.add(Hop(hop2, hop2Amount, hop2Time))
        hopList.add(Hop(hop3, hop3Amount, hop3Time))
        hopList.add(Hop(hop4, hop4Amount, hop4Time))

        return Recipe(beerName, style, fermentableList, hopList, yeast)
    }

    private fun getTextFromSpinner(@IdRes viewId: Int): String {
        return (findViewById<View>(viewId) as Spinner).selectedItem.toString()
    }

    private fun getTextFromEditText(@IdRes viewId: Int): String {
        val text = (findViewById<View>(viewId) as EditText).text.toString()
        return if (text.isEmpty()) "Nothing" else text
    }

    private fun getFloatFromEditText(@IdRes viewId: Int): Float {
        val text = getTextFromEditText(viewId)
        return if (text.isEmpty() or text.equals("Nothing")) {
            0f
        } else {
            java.lang.Float.valueOf(text)
        }
    }

    private fun getIntFromEditText(@IdRes viewId: Int): Int {
        val text = getTextFromEditText(viewId)
        return if (text.isEmpty() or text.equals("Nothing")) {
            0
        } else {
            Integer.valueOf(text)
        }
    }

    companion object {
        const val REQUEST_CODE = 7
        const val RECIPE_KEY = "RECIPE_KEY"
        private const val NUM_PAGES = 2
    }
}
