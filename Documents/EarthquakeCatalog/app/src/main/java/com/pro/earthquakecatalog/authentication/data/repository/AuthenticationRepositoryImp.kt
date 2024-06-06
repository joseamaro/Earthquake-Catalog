package com.pro.earthquakecatalog.authentication.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pro.earthquakecatalog.utils.resultOf
import kotlinx.coroutines.tasks.await

class AuthenticationRepositoryImp : AuthenticationRepository{
    override suspend fun login(email: String, password: String): Result<Unit> {
        return resultOf {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun registerUser(email: String, password: String): Result<Unit> {
        return resultOf {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        }
    }

    override fun getUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    override suspend fun logOut() {
        Firebase.auth.signOut()
    }
}