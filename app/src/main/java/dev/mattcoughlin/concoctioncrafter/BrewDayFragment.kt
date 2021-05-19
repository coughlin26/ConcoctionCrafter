/* Copyright Matthew Coughlin 2018, 2019, 2020 */

package dev.mattcoughlin.concoctioncrafter

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.mattcoughlin.concoctioncrafter.data.Hop
import dev.mattcoughlin.concoctioncrafter.data.Recipe
import dev.mattcoughlin.concoctioncrafter.data.RecipeViewModel
import dev.mattcoughlin.concoctioncrafter.databinding.BrewDayFragmentBinding
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

@SuppressLint("SetTextI18n")
@BindingAdapter("elapsedTime")
fun TextView.setElapsedTime(value: Long) {
    text = String.format(
            "Time Remaining: ${TimeUnit.MILLISECONDS.toMinutes(value)}:%02d",
            TimeUnit.MILLISECONDS.toSeconds(value) - TimeUnit.MILLISECONDS.toMinutes(value) * 60)
}

class BrewDayFragment : Fragment() {
    private var _startBoilButton: Button? = null
    private var _recipeName: TextView? = null
    private var _hopList: LinearLayout? = null
    private var _alcoholContent: TextView? = null
    private var _timeRemaining: TextView? = null
    private var _startingGravity: EditText? = null
    private var _finalGravity: EditText? = null
    private var _recipeSubscription: Disposable? = null
    private var _hops: List<Hop>? = null
    private lateinit var _viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private var _recipeViewModel: RecipeViewModel? = null

    private var recipeName: String
        get() = _recipeName!!.text.toString()
        set(name) {
            if (_recipeName != null)
                _recipeName!!.text = name
            else
                Log.w("Brew_Day_Fragment", "Recipe Name view is null")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (activity != null) {
            _recipeSubscription = (activity as MainActivity).recipeSubject.subscribe({ recipe ->
                restoreRecipeViews(recipe)
            }, { throwable ->
                Log.e("Brew_Day_Fragment", "Failed to get the recipe", throwable)
            })
        }

        _viewModelFactory = ViewModelProvider.AndroidViewModelFactory(this.requireActivity().application)
        _recipeViewModel = ViewModelProvider(this, _viewModelFactory).get(RecipeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: BrewDayFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.brew_day_fragment,
                container,
                false)
        val rootView = binding.root

        _startBoilButton = rootView.findViewById(R.id.start_boil_button)
        _startBoilButton?.setOnClickListener {
            when (_startBoilButton?.text) {
                getString(R.string.start_boil) -> startBoil(TimeUnit.MINUTES.toSeconds(60))
                getString(R.string.stop_boil) -> stopBoil()
                else -> stopBoil()
            }
        }
        _recipeName = rootView.findViewById(R.id.name)
        _hopList = rootView.findViewById(R.id.hop_info_list)
        _alcoholContent = rootView.findViewById(R.id.actual_ac)
        _timeRemaining = rootView.findViewById(R.id.time_remaining)
        _startingGravity = rootView.findViewById(R.id.actual_og_input)
        _finalGravity = rootView.findViewById(R.id.actual_fg_input)

        var startingGravity = 0.0
        var finalGravity = 0.0

        if (_startingGravity?.text.toString() != "") {
            startingGravity = _startingGravity?.text.toString().toDouble()
        }
        if (_finalGravity?.text.toString() != "") {
            finalGravity = _finalGravity?.text.toString().toDouble()
        }

        if (finalGravity != 0.0) {
            _alcoholContent?.text =
                    (((1.05 * (startingGravity - finalGravity)) / finalGravity) / 0.79).toString()
        }

        binding.recipeViewModel = _recipeViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        createNotificationChannels()

        return rootView
    }

    override fun onDestroy() {
        _recipeSubscription?.dispose()
        super.onDestroy()
    }

    /**
     * Restore the recipe information.
     */
    private fun restoreRecipeViews(recipe: Recipe?) {
        if (recipe != null) {
            recipeName = recipe.recipeName
            _hops = recipe.hops
            setHopViews(_hops as ArrayList<Hop>)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setHopViews(hops: List<Hop>?) {
        _hopList!!.removeAllViews()

        if (hops != null) {
            for (hop in hops.sortedByDescending { hop -> hop.additionTime_min }) {
                val newRow = layoutInflater.inflate(
                        R.layout.hop_info_row,
                        requireActivity().findViewById(R.id.hop_info_list),
                        false)
                newRow.findViewById<TextView>(R.id.hop_name).text = hop.name
                newRow.findViewById<TextView>(R.id.hop_amount).text = "%.2f".format(hop.amount_oz)
                newRow.findViewById<TextView>(R.id.hop_time).text =
                        if (hop.additionTime_min != -1) hop.additionTime_min.toString() else ""

                if (_hopList!!.parent != null) (_hopList!!.parent as ViewGroup).removeView(newRow)
                _hopList!!.addView(newRow)
            }
        }
    }

    private fun startBoil(boilTimeSeconds: Long) {
        _startBoilButton?.text = getString(R.string.stop_boil)
        _timeRemaining?.visibility = View.VISIBLE

        _recipeViewModel?.setAlarm(true, _hops)
    }

    private fun stopBoil() {
        _startBoilButton?.text = getString(R.string.start_boil)
        _timeRemaining?.visibility = View.GONE
        _recipeViewModel!!.setAlarm(false)
    }

    private fun createNotificationChannels() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.boil_channel_name)
            val descriptionText = getString(R.string.boil_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                    requireActivity().getString(R.string.boil_channel_name),
                    name,
                    importance)
                    .apply { setShowBadge(true) }

            channel.enableLights(true)
            channel.lightColor = ContextCompat.getColor(requireActivity(), R.color.colorAccent)
            channel.enableVibration(true)
            channel.description = descriptionText

            // Register the channel with the system
            val notificationManager = requireActivity().getSystemService(
                    NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            val hopName = getString(R.string.hop_channel_name)
            val hopDesc = getString(R.string.hop_channel_description)
            val hopImportance = NotificationManager.IMPORTANCE_HIGH
            val hopChannel = NotificationChannel(
                    requireActivity().getString(R.string.hop_channel_name),
                    hopName,
                    hopImportance)
                    .apply { setShowBadge(true) }

            hopChannel.enableLights(true)
            hopChannel.lightColor = ContextCompat.getColor(requireActivity(), R.color.colorAccent)
            hopChannel.enableVibration(true)
            hopChannel.description = hopDesc

            notificationManager.createNotificationChannel(hopChannel)
        }
    }
}
