/* Copyright Matthew Coughlin 2018, 2019, 2020 */

package dev.mattcoughlin.concoctioncrafter.data

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.mattcoughlin.concoctioncrafter.BoilTimerReceiver
import dev.mattcoughlin.concoctioncrafter.HopTimerReceiver
import dev.mattcoughlin.concoctioncrafter.MainActivity
import dev.mattcoughlin.concoctioncrafter.cancelNotifications
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    val recipeList: LiveData<List<Recipe>>
    var recipe: Recipe? = null

    private val _triggerTime = "TRIGGER_AT"

    private val _second = 1000L
    private val _minute = 60L * _second

    private val _recipeRepository: RecipeRepository = RecipeRepository(application)
    private val _boilPendingIntent: PendingIntent
    private val _hopPendingIntents = ArrayList<PendingIntent>()
    private val _alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val prefs = application.getSharedPreferences(
            "dev.mattcoughlin.concoctioncrafter",
            Context.MODE_PRIVATE)
    private val _boilIntent = Intent(application, BoilTimerReceiver::class.java)
    private val _hopIntents = ArrayList<Intent>()

    private val _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long>
        get() = _elapsedTime

    private var _alarmOn = MutableLiveData<Boolean>()
    val isAlarmOn: LiveData<Boolean>
        get() = _alarmOn

    private lateinit var _boilTimer: CountDownTimer

    val all: List<Recipe>?
        get() {
            Log.d(TAG, "Getting all recipes, unordered")
            return _recipeRepository.all
        }

    init {
        recipeList = _recipeRepository.allRecipes
        _alarmOn.value = PendingIntent.getBroadcast(
                getApplication(),
                MainActivity.BOIL_REQUEST_CODE,
                _boilIntent,
                PendingIntent.FLAG_NO_CREATE
        ) != null

        _boilIntent.putExtra(MainActivity.NOTIFICATION_TITLE, "Boil Complete")
        _boilIntent.putExtra(MainActivity.NOTIFICATION_MESSAGE, "Your concoction is done boiling")

        _boilPendingIntent = PendingIntent.getBroadcast(
                getApplication(),
                MainActivity.BOIL_REQUEST_CODE,
                _boilIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        if (_alarmOn.value!!) {
            createTimer()
        }
    }

    fun insert(recipe: Recipe) {
        Log.d(TAG, "Inserting recipe: $recipe")
        _recipeRepository.insert(recipe)
    }

    fun insert(vararg recipes: Recipe) {
        for (recipe in recipes) {
            Log.d(TAG, "Inserting recipes, starting with: $recipe")
        }
        _recipeRepository.insert(*recipes)
    }

    fun update(recipe: Recipe) {
        Log.d(TAG, "Updating recipe named: $recipe")
        _recipeRepository.update(recipe)
    }

    fun update(vararg recipes: Recipe) {
        for (recipe in recipes) {
            Log.d(TAG, "Updating recipes, stating with: $recipe")
        }
        _recipeRepository.update(*recipes)
    }

    fun findByName(name: String): Recipe? {
        Log.d(TAG, "Searching for $name")
        recipe = _recipeRepository.findByName(name)
        return recipe
    }

    fun delete(recipe: Recipe) {
        Log.d(TAG, "Deleting recipe: $recipe")
        _recipeRepository.delete(recipe)
    }

    fun deleteAll() {
        Log.d(TAG, "Deleting all recipes...")
        _recipeRepository.deleteAll()
    }

    fun setAlarm(isChecked: Boolean, hops: List<Hop>? = null) {
        when (isChecked) {
            true -> startTimer(_minute * 60, hops)
            false -> cancelNotification()
        }
    }

    private fun startTimer(timerLength: Long, hops: List<Hop>?) {
        resetTimer()

        _alarmOn.value?.let {
            _alarmOn.value = true
            val triggerTime = SystemClock.elapsedRealtime() + timerLength
            val notificationManager = ContextCompat.getSystemService(
                    getApplication(),
                    NotificationManager::class.java) as NotificationManager
            notificationManager.cancelNotifications()

            AlarmManagerCompat.setExactAndAllowWhileIdle(
                    _alarmManager,
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime,
                    _boilPendingIntent)

            // TODO: Recipe is null here, it shouldn't be.
            Log.d(TAG, "Recipe: $recipe")
            Log.d(TAG, "Hops: $hops")

            if (hops != null) {
                for (hop in hops) {
                    // Check for an invalid hop time.
                    if (TimeUnit.MINUTES.toMillis(hop.additionTime_min.toLong()) > timerLength) {
                        Toast.makeText(getApplication(),
                                "${hop.name} hops have an invalid time!",
                                Toast.LENGTH_SHORT)
                                .show()

                        // Skip to the next hop.
                        continue
                    }

                    val hopIntent = Intent(getApplication(), HopTimerReceiver::class.java)
                    hopIntent.putExtra(MainActivity.NOTIFICATION_TITLE, "Add Hop")
                    hopIntent.putExtra(
                            MainActivity.NOTIFICATION_MESSAGE,
                            "Time to add ${hop.amount_oz}oz of ${hop.name}")
                    _hopIntents.add(hopIntent)

                    _hopPendingIntents.add(PendingIntent.getBroadcast(
                            getApplication(),
                            MainActivity.HOP_REQUEST_CODE,
                            _hopIntents.last(),
                            PendingIntent.FLAG_UPDATE_CURRENT))

                    // The hop time is inverted, so subtract the input hop time from the boil time.
                    // For example, an addition time of 45 minutes happens 15 minutes into a 1 hour boil.
                    Log.d(TAG, "Setting timer for ${(timerLength -
                            TimeUnit.MINUTES.toMillis(hop.additionTime_min.toLong()))}ms from now")
                    AlarmManagerCompat.setExactAndAllowWhileIdle(
                            _alarmManager,
                            AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            SystemClock.elapsedRealtime() +
                                    (timerLength - TimeUnit.MINUTES.toMillis(hop.additionTime_min.toLong())),
                            _hopPendingIntents.last())
                }
            }

            viewModelScope.launch {
                saveTime(triggerTime)
            }
        }
        createTimer()
    }

    private fun createTimer() {
        viewModelScope.launch {
            val triggerTime = loadTime()
            _boilTimer = object : CountDownTimer(triggerTime, _second) {
                override fun onTick(millisUntilFinished: Long) {
                    _elapsedTime.value = triggerTime - SystemClock.elapsedRealtime()
                    if (_elapsedTime.value!! <= 0) {
                        resetTimer()
                    }
                }

                override fun onFinish() {
                    resetTimer()
                }
            }
            _boilTimer.start()
        }
    }

    private fun cancelNotification() {
        resetTimer()
        _alarmManager.cancel(_boilPendingIntent)

        for (hopIntent in _hopPendingIntents) {
            _alarmManager.cancel(hopIntent)
        }
    }

    private fun resetTimer() {
        _boilTimer.cancel()
        _elapsedTime.value = 0
        _alarmOn.value = false
    }

    private suspend fun saveTime(triggerTime: Long) =
            withContext(Dispatchers.IO) {
                prefs.edit().putLong(_triggerTime, triggerTime).apply()
            }

    private suspend fun loadTime(): Long =
            withContext(Dispatchers.IO) {
                prefs.getLong(_triggerTime, 0)
            }

    companion object {
        private const val TAG = "Testing"
    }
}
