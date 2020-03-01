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
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.mattcoughlin.concoctioncrafter.BoilTimerReceiver
import dev.mattcoughlin.concoctioncrafter.MainActivity
import dev.mattcoughlin.concoctioncrafter.cancelNotifications
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    val recipeList: LiveData<List<Recipe>>
    var recipe: Recipe? = null

    private val TRIGGER_TIME = "TRIGGER_AT"

    private val _second = 1000L
    private val _minute = 60L * _second

    private val _recipeRepository: RecipeRepository = RecipeRepository(application)
    private val _notifyPendingIntent: PendingIntent
    private val _alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val prefs = application.getSharedPreferences("dev.mattcoughlin.concoctioncrafter", Context.MODE_PRIVATE)
    private val _notifyIntent = Intent(application, BoilTimerReceiver::class.java)

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
                MainActivity.REQUEST_CODE,
                _notifyIntent,
                PendingIntent.FLAG_NO_CREATE
        ) != null

        _notifyPendingIntent = PendingIntent.getBroadcast(
                getApplication(),
                MainActivity.REQUEST_CODE,
                _notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

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
        Log.d("TESTING", "Retrieved: $recipe")
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

    fun setAlarm(isChecked: Boolean) {
        when (isChecked) {
            true -> startTimer(_minute * 60)
            false -> cancelNotification()
        }
    }

    private fun startTimer(timerLength: Long) {
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
                    _notifyPendingIntent)

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
                    Log.d("TESTING", "onTick: $millisUntilFinished")
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
        _alarmManager.cancel(_notifyPendingIntent)
    }

    private fun resetTimer() {
        _boilTimer.cancel()
        _elapsedTime.value = 0
        _alarmOn.value = false
    }

    private suspend fun saveTime(triggerTime: Long) =
            withContext(Dispatchers.IO) {
                prefs.edit().putLong(TRIGGER_TIME, triggerTime).apply()
            }

    private suspend fun loadTime(): Long =
            withContext(Dispatchers.IO) {
                prefs.getLong(TRIGGER_TIME, 0)
            }

    companion object {
        private const val TAG = "Testing"
    }
}
