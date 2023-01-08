package com.github.elvirka.libraries

import com.github.benmanes.caffeine.cache.AsyncCache
import com.github.benmanes.caffeine.cache.AsyncLoadingCache
import com.github.benmanes.caffeine.cache.Caffeine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class Object(val access: String) {
    companion object {
        private var objectCounter = 0

        fun get(data: String): Object {
            objectCounter++
            return Object(data)
        }
    }
}

fun main(): Unit = runBlocking {
    val cache: AsyncLoadingCache<String, Object> = Caffeine.newBuilder()
        .maximumSize(100)
        .expireAfterWrite(1, TimeUnit.MINUTES)
        .buildAsync { k: String -> Object.get("Data for $k") }

    val key = "A"

    val token = cache.get(key) { k, _ ->
        future {
            doSomethingAsync(k)
        }
    }.await().access
    println(token)

    val value = cache.get(key).thenAccept { dataObject ->
        assertNotNull(dataObject)
        assertEquals("Data for $key", dataObject.access)
    }
    println(value)

    cache.getAll(listOf("A", "B", "C"))
        .thenAccept { dataObjectMap -> assertEquals(3, dataObjectMap.size) }
}

internal suspend fun doSomethingAsync(k: String): Object = coroutineScope {
    async { Object.get("Data for $k") }
}.await()

internal class SuspendingCache(private val asyncCache: AsyncCache<String, Object>) {
    suspend fun get(key: String): Object = supervisorScope {
        getAsync(key).await()
    }

    private fun CoroutineScope.getAsync(key: String): CompletableFuture<Object> = asyncCache.get(key) { k, _ ->
        future {
            loadValue(k)
        }
    }

    private suspend fun loadValue(key: String): Object = coroutineScope {
        async { Object.get("Data for $key") }
    }.await()
}