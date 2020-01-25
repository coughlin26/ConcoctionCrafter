/* Copyright Matthew Coughlin 2018, 2020 */

package dev.mattcoughlin.concoctioncrafter

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat {
    constructor() : super()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}