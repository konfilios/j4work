package org.j4work.kotlin.extjava.javax.crypto

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * javax.crypto.Mac extensions
 */
object Macs {
    const val HMAC_SHA256 = "HmacSHA256"

    /**
     * Calculates MAC of message with given hash algorithm and key.
     *
     * [See more](https://en.wikipedia.org/wiki/Hash-based_message_authentication_code)
     *
     * @param hashAlgorithm
     * @param key The facebook app secret for which the proof will be created
     * @param message The facebook access token which will be used as the signing key
     * @return Hash result as hexadecimal string
     */
    fun hash(hashAlgorithm: String, key: String, message: String): ByteArray = Mac.getInstance(
        hashAlgorithm).run {
        // Set key
        init(SecretKeySpec(key.toByteArray(), hashAlgorithm))
        // Calculate hash
        doFinal(message.toByteArray())
    }

}