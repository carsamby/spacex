package es.carlos.spacex.domain

import es.carlos.spacex.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

fun <T> exceptionSafeFlow(run: suspend FlowCollector<Resource<T>>.() -> Unit): Flow<Resource<T>> =
    flow {
        try {
            run.invoke(this)
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error (HTTP)"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your connection and try again"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message))
        }

    }

