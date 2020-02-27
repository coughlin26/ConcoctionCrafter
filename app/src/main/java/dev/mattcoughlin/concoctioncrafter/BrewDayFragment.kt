/* Copyright Matthew Coughlin 2018, 2019, 2020 */

package dev.mattcoughlin.concoctioncrafter

import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
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

class BrewDayFragment : Fragment() {
    private var _startBoilButton: Button? = null
    private var _recipeName: TextView? = null
    private var _hopList: LinearLayout? = null
    private var _alcoholContent: TextView? = null
    private var _timeRemaining: TextView? = null
    private var _startingGravity: EditText? = null
    private var _finalGravity: EditText? = null
    private var _recipeSubscription: Disposable? = null
    private var _boilTimer: CountDownTimer? = null
    private var _remainingSeconds: Long = 0
    private var _hops: List<Hop>? = null
    private var _alarmManager: AlarmManager? = null
    private var _recipeViewModel: RecipeViewModel? = null
    private lateinit var _alarmIntent: PendingIntent

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
            }, { throwable -> Log.e("Brew_Day_Fragment", "Failed to get the recipe", throwable) })
        }

        if (savedInstanceState != null) {
            _remainingSeconds = savedInstanceState.getLong("REMAINING_TIME")
        }

        _recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: BrewDayFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.brew_day_fragment, container, false)
        val rootView = binding.root

        _startBoilButton = rootView.findViewById(R.id.start_boil_button)
        _startBoilButton?.setOnClickListener {
            //Toast.makeText(context, "This feature is coming soon!", Toast.LENGTH_SHORT).show()
            when (_startBoilButton?.text) {
                getString(R.string.start_boil) -> startBoil(TimeUnit.MINUTES.toSeconds(60))
                "Stop Boil" -> stopBoil()
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
            _alcoholContent?.text = (((1.05 * (startingGravity - finalGravity)) / finalGravity) / 0.79).toString()
        }

        binding.recipeViewModel = _recipeViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        return rootView
    }

    override fun onDestroy() {
        _recipeSubscription?.dispose()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong("REMAINING_TIME", _remainingSeconds)
        super.onSaveInstanceState(outState)
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

    private fun setHopViews(hops: List<Hop>?) {
        _hopList!!.removeAllViews()

        if (hops != null) {
            for (hop in hops) {
                val newRow = layoutInflater.inflate(R.layout.hop_info_row, activity!!.findViewById(R.id.hop_info_list), false)
                newRow.findViewById<TextView>(R.id.hop_name).text = hop.name
                newRow.findViewById<TextView>(R.id.hop_amount).text = "%.2f".format(hop.amount_oz)
                newRow.findViewById<TextView>(R.id.hop_time).text = if (hop.additionTime_min != -1) hop.additionTime_min.toString() else ""

                if (_hopList!!.parent != null) (_hopList!!.parent as ViewGroup).removeView(newRow)
                _hopList!!.addView(newRow)
            }
        }
    }

    private fun startBoil(boilTimeSeconds: Long) {
        _startBoilButton?.text = getString(R.string.stop_boil)
        _timeRemaining?.visibility = View.VISIBLE

        _recipeViewModel?.setAlarm(true)

//        _boilTimer = object : CountDownTimer(TimeUnit.SECONDS.toMillis(boilTimeSeconds), 1000) {
//            override fun onFinish() {
//                val builder: AlertDialog.Builder? = activity.let { AlertDialog.Builder(it) }
//                builder?.setTitle(R.string.boil_finished)
//                        ?.setMessage(R.string.click_to_dismiss)
//                        ?.setPositiveButton(R.string.ok) { dialog, _ ->
//                            stopBoil()
//                            dialog.dismiss()
//                        }
//                        ?.show()
//            }
//
//            override fun onTick(millisUntilFinished: Long) {
//                _remainingSeconds = millisUntilFinished / 1000
//
//                if (_hops != null) {
//                    for (hop in _hops!!) {
//                        if (_remainingSeconds == hop.additionTime_min * 60L) {
//                            val id: Int = (Math.random() * 1000).toInt()
//
//                            val builder = NotificationCompat.Builder(activity!!.applicationContext, NOTIFICATION_CHANNEL)
//                                    .setSmallIcon(R.mipmap.ic_text_launcher_square)
//                                    .setContentTitle(getString(R.string.add_title, hop.name))
//                                    .setContentText(getString(R.string.add_message, hop.amount_oz))
//                                    .setPriority(NotificationCompat.DEFAULT_ALL)
//
//                            with(NotificationManagerCompat.from(activity!!.applicationContext)) {
//                                notify(id, builder.build())
//                            }
//
//                            activity.let { AlertDialog.Builder(it) }
//                                    .setTitle(getString(R.string.add_title, hop.name))
//                                    ?.setMessage(getString(R.string.add_message, hop.amount_oz))
//                                    ?.setPositiveButton(R.string.ok) { dialog, _ ->
//                                        dialog.dismiss()
//                                        with(NotificationManagerCompat.from(activity!!.applicationContext)) {
//                                            cancel(id)
//                                        }
//                                    }
//                                    ?.show()
//                        }
//                    }
//                }
//
//                _timeRemaining?.text = getString(R.string.remaining_time,
//                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
//                        _remainingSeconds - (_remainingSeconds / 60 * 60))
//            }
//        }.start()
    }

    private fun stopBoil() {
        _startBoilButton?.text = getString(R.string.start_boil)
        _timeRemaining?.visibility = View.GONE
        _boilTimer?.cancel()
        _remainingSeconds = 0
        _alarmManager?.cancel(_alarmIntent)
        _recipeViewModel?.setAlarm(false)
    }

    @BindingAdapter("elapsedTime")
    fun TextView.setElapsedTime(value: Long) {
        text = getString(R.string.remaining_time,
                TimeUnit.MILLISECONDS.toMinutes(value),
                _remainingSeconds - (_remainingSeconds / 60 * 60))
    }
}
