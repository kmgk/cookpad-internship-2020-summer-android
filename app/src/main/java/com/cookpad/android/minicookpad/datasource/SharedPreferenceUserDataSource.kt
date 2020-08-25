package com.cookpad.android.minicookpad.datasource

import android.content.Context
import androidx.core.content.edit

class SharedPreferenceUserDataSource(context: Context) : LocalUserDataSource {
    private val preference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun fetch(): UserEntity? {
        val id = preference.getString(KEY_ID, null)
        val name = preference.getString(KEY_NAME, null)
        return if (id != null && name != null) {
            UserEntity(id, name)
        } else {
            null
        }
    }

    override fun save(user: UserEntity, onSuccess: () -> Unit, onFailed: (Throwable) -> Unit) {
        preference.edit {
            putString(KEY_ID, user.id)
            putString(KEY_NAME, user.name)
            onSuccess.invoke()
        }
    }

    private companion object {
        const val PREF_NAME = "LocalUserDataSource"
        const val KEY_ID = "user_id"
        const val KEY_NAME = "user_name"
    }
}