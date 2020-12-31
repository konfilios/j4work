package org.j4work.kotlin.extkotlin.kotlin

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.io.IOException

/**
 * Created by kif on 29/1/2017.
 */
class UseExtKtTest {
    @Test
    fun `return result value of body() when no error happens`() {
        Finishable().let {
            assertThat(it.use({ finish() }) { "success" })
                .isEqualTo("success")
        }
    }

    @Test
    fun `properly finish when no error happens in body()`() {
        Finishable().let {
            it.use({ finish() }) { "success" }

            assertThat(it.isFinished)
                .isEqualTo(true)
        }
    }

    @Test
    fun `properly finish when error happens in body()`() {
        Finishable().let {

            // Fail with an exception
            // IOException was chosen arbitrarily
            assertThatThrownBy {
                it.use({ finish() }) { throw IOException() }
            }
                .isInstanceOf(IOException::class.java)

            // Must be finished although an exception was thrown
            assertThat(it.isFinished)
                .isEqualTo(true)
        }
    }

    @Test
    fun `fail when finish fails`() {
        Finishable().let {

            assertThatThrownBy {
                it.use({ failToFinish() }) { "success" }
            }
                .isInstanceOf(Finishable.FailedToFinishException::class.java)

            assertThat(it.isFinished)
                .isEqualTo(false)
        }

    }

    /**
     * Finishable is like Closeable but API recognizes it,
     * so it is a good candidate for the generic use() extension function.
     */
    class Finishable {
        /**
         * Indicates whether finish() has been called successfully.
         */
        var isFinished = false
            private set

        /**
         * Finish successfully.
         */
        fun finish() {
            isFinished = true
        }

        /**
         * Throw an exception instead of finishing.
         */
        fun failToFinish() {
            throw FailedToFinishException()
        }

        /**
         * Exception thrown when finish fails.
         */
        class FailedToFinishException : Exception()
    }
}