package com.cookpad.android.minicookpad.datastore

import com.cookpad.android.minicookpad.datasource.*

class UserDataStoreImpl(
    private val localUserDataSource: LocalUserDataSource,
    private val remoteUserDataSource: RemoteUserDataSource
) : UserDataStore {
    override fun currentUser() = localUserDataSource.fetch()


    override fun fetch(
        name: String,
        onSuccess: (UserEntity?) -> Unit,
        onFailed: (Throwable) -> Unit
    ) {
        remoteUserDataSource.fetch(
            name,
            onSuccess = {
                if (it != null) {
                    localUserDataSource.save(
                        it,
                        onSuccess = {
                            onSuccess
                        },
                        onFailed = onFailed
                    )
                }
            },
            onFailed = onFailed
        )
    }

    override fun register(user: UserEntity, onSuccess: () -> Unit, onFailed: (Throwable) -> Unit) {
        remoteUserDataSource.save(
            user,
            onSuccess = {
                localUserDataSource.save(it, onSuccess, onFailed)
            },
            onFailed = onFailed
        )
    }
}