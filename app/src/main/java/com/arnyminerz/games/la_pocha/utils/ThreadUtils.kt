package com.arnyminerz.games.la_pocha.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Runs the code at [block] in the IO thread.
 * @author Arnau Mora
 * @since 20220821
 * @see Dispatchers.IO
 */
suspend fun <T> io(block: suspend CoroutineScope.() -> T): T =
    withContext(Dispatchers.IO, block)

/**
 * Runs the code at [block] in the UI thread.
 * @author Arnau Mora
 * @since 20220821
 * @see Dispatchers.Main
 */
suspend fun <T> ui(block: suspend CoroutineScope.() -> T): T =
    withContext(Dispatchers.Main, block)
