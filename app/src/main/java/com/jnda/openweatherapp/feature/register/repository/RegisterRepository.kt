package com.jnda.openweatherapp.feature.register.repository

import com.jnda.openweatherapp.network.IoDispatcher
import com.jnda.openweatherapp.storage.dao.UserDAO
import com.jnda.openweatherapp.storage.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

interface RegisterRepository {
    suspend fun insertUser(username: String, password: String): Long
}

class RegisterRepositoryImpl @Inject constructor(
    private val userDao: UserDAO,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
): RegisterRepository {
    override suspend fun insertUser(username: String, password: String): Long {
        return with(ioDispatcher) {
            val userEntity = UserEntity(
                username = username,
                password = password
            )
            userDao.insert(userEntity)
        }
    }
}
