package org.j4work.kotlin.extkotlin.kotlin

import java.io.Closeable

/**
 * Executes the given [block] function on this resource and then
 * closes it down correctly whether an exception is thrown or not.
 *
 * @param block a function to process this [Closeable] resource.
 * @return the result of [block] function invoked on this resource.
 */
inline fun <T : Any?, R> T.use(
    close: T.() -> Any,
    block: (T) -> R
): R {
    // Keep track of block's exception
    var cause: Throwable? = null

    try {
        // Run block with `this` as `it` and return result to caller
        return block(this)

    } catch (e: Throwable) {
        // Keep error for use in the finally block and re-throw
        cause = e

        throw e

    } finally {
        when {

            this == null ->
                // Nothing to close
            {
            }

            cause == null ->
                // Block completed without errors
                this.close()

            else ->
                // Block completed with errors, try to close
                try {
                    this.close()
                } catch (closeException: Throwable) {
                    cause.addSuppressed(closeException)
                }
        }
    }
}
