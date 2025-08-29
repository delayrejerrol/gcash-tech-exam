package com.jnda.openweatherapp.feature.login.repository

import com.jnda.openweatherapp.network.IoDispatcher
import com.jnda.openweatherapp.storage.dao.UserDAO
import com.jnda.openweatherapp.storage.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LoginRepository {
    suspend fun findByAccount(username: String, password: String): UserEntity?
}

class LoginRepositoryImpl @Inject constructor(
    private val userDAO: UserDAO,
    @param:IoDispatcher
    private val dispatcher: CoroutineDispatcher
): LoginRepository {
    override suspend fun findByAccount(
        username: String,
        password: String
    ): UserEntity? {
        return withContext(dispatcher) {
            userDAO.findByAccount(username, password)
        }
    }

}