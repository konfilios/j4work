package org.j4work.kotlin.utils.regex

import java.util.*
import java.util.regex.Pattern

class VerbalExpression
/**
 * Use builder [.regex] (or [.regex])
 * to create new instance of VerbalExpression

 * @param pattern - [java.util.regex.Pattern] that constructed by builder
 */
private constructor(private val pattern: Pattern) {

    class Builder
    /**
     * Package private. Use [.regex] to build a new one

     * @since 1.2
     */
    internal constructor() {

        internal var prefixes = StringBuilder()
        internal val source = StringBuilder()
        internal var suffixes = StringBuilder()
        internal var modifiers = Pattern.MULTILINE

        /**
         * Escapes any non-word char with two backslashes
         * used by any method, except [.add]

         * @param pValue - the string for char escaping
         * *
         * @return sanitized string value
         */
        private fun sanitize(pValue: String): String {
            return pValue.replace("[\\W]".toRegex(), "\\\\$0")
        }

        /**
         * Counts occurrences of some substring in whole string
         * Same as org.apache.commons.lang3.StringUtils#countMatches(String, java.lang.String)
         * by effect. Used to count braces for [.or] method

         * @param where - where to find
         * *
         * @param what  - what needs to count matches
         * *
         * @return 0 if nothing found, count of occurrences instead
         */
        private fun countOccurrencesOf(where: String, what: String): Int {
            return (where.length - where.replace(what, "").length) / what.length
        }

        fun build(): VerbalExpression {
            return VerbalExpression(pattern())
        }

        fun pattern(): Pattern {
            return Pattern.compile(
                StringBuilder(prefixes)
                    .append(source)
                    .append(suffixes)
                    .toString(),
                modifiers
            )
        }

        /**
         * Append literal expression
         * Everything added to the expression should go trough this method
         * (keep in mind when creating your own methods).
         * All existing methods already use this, so for basic usage, you can just ignore this method.
         *
         *
         * Example:
         * regex().add("\n.*").build() // produce exact "\n.*" regexp

         * @param pValue - literal expression, not sanitized
         * *
         * @return this builder
         */
        fun add(pValue: String): Builder {
            this.source.append(pValue)
            return this
        }

        /**
         * Append a regex from builder and wrap it with unnamed group (?: ... )

         * @param regex - VerbalExpression.Builder, that not changed
         * *
         * @return this builder
         * *
         * @since 1.2
         */
        fun add(regex: Builder): Builder {
            return this.group().add(regex.build().toString()).endGr()
        }

        /**
         * Enable or disable the expression to start at the beginning of the line

         * @param pEnable - enables or disables the line starting
         * *
         * @return this builder
         */
        @JvmOverloads fun startOfLine(pEnable: Boolean = true): Builder {
            this.prefixes.append(if (pEnable) "^" else "")
            if (!pEnable) {
                this.prefixes = StringBuilder(this.prefixes.toString().replace("^", ""))
            }
            return this
        }

        /**
         * Enable or disable the expression to end at the last character of the line

         * @param pEnable - enables or disables the line ending
         * *
         * @return this builder
         */
        @JvmOverloads fun endOfLine(pEnable: Boolean = true): Builder {
            this.suffixes.append(if (pEnable) "$" else "")
            if (!pEnable) {
                this.suffixes = StringBuilder(this.suffixes.toString().replace("$", ""))
            }
            return this
        }

        /**
         * Add a string to the expression

         * @param pValue - the string to be looked for (sanitized)
         * *
         * @return this builder
         */
        fun then(pValue: String): Builder {
            return this.add("(?:" + sanitize(pValue) + ")")
        }

        /**
         * Add a string to the expression
         * Syntax sugar for [.then] - use it in case:
         * regex().find("string") // when it goes first

         * @param value - the string to be looked for (sanitized)
         * *
         * @return this builder
         */
        fun find(value: String): Builder {
            return this.then(value)
        }

        /**
         * Add a string to the expression that might appear once (or not)
         * Example:
         * The following matches all strings that contain http:// or https://
         * VerbalExpression regex = regex()
         * .find("http")
         * .maybe("s")
         * .then("://")
         * .anythingBut(" ").build();
         * regex.test("http://")    //true
         * regex.test("https://")   //true

         * @param pValue - the string to be looked for
         * *
         * @return this builder
         */
        fun maybe(pValue: String): Builder {
            return this.then(pValue).add("?")
        }

        /**
         * Add a regex to the expression that might appear once (or not)
         * Example:
         * The following matches all names that have a prefix or not.
         * VerbalExpression.Builder namePrefix = regex().oneOf("Mr.", "Ms.");
         * VerbalExpression name = regex()
         * .maybe(namePrefix)
         * .space()
         * .zeroOrMore()
         * .word()
         * .oneOrMore()
         * .build();
         * regex.test("Mr. Bond/")    //true
         * regex.test("James")   //true

         * @param pValue - the string to be looked for
         * *
         * @return this builder
         */
        fun maybe(regex: Builder): Builder {
            return this.group().add(regex).endGr().add("?")
        }

        /**
         * Add expression that matches anything (includes empty string)

         * @return this builder
         */
        fun anything(): Builder {
            return this.add("(?:.*)")
        }

        /**
         * Add expression that matches anything, but not passed argument

         * @param pValue - the string not to match
         * *
         * @return this builder
         */
        fun anythingBut(pValue: String): Builder {
            return this.add("(?:[^" + sanitize(pValue) + "]*)")
        }

        /**
         * Add expression that matches something that might appear once (or more)

         * @return this builder
         */
        fun something(): Builder {
            return this.add("(?:.+)")
        }

        fun somethingButNot(pValue: String): Builder {
            return this.add("(?:[^" + sanitize(pValue) + "]+)")
        }

        /**
         * Add universal line break expression

         * @return this builder
         */
        fun lineBreak(): Builder {
            return this.add("(?:\\n|(?:\\r\\n))")
        }

        /**
         * Shortcut for [.lineBreak]

         * @return this builder
         */
        fun br(): Builder {
            return this.lineBreak()
        }

        /**
         * Add expression to match a tab character ('\u0009')

         * @return this builder
         */
        fun tab(): Builder {
            return this.add("(?:\\t)")
        }

        /**
         * Add word, same as [a-zA-Z_0-9]+

         * @return this builder
         */
        fun word(): Builder {
            return this.add("(?:\\w+)")
        }


        /*
           --- Predefined character classes
         */

        /**
         * Add word character, same as [a-zA-Z_0-9]

         * @return this builder
         */
        fun wordChar(): Builder {
            return this.add("(?:\\w)")
        }


        /**
         * Add non-word character: [^\w]

         * @return this builder
         */
        fun nonWordChar(): Builder {
            return this.add("(?:\\W)")
        }

        /**
         * Add non-digit: [^0-9]

         * @return this builder
         */
        fun nonDigit(): Builder {
            return this.add("(?:\\D)")
        }

        /**
         * Add same as [0-9]

         * @return this builder
         */
        fun digit(): Builder {
            return this.add("(?:\\d)")
        }

        /**
         * Add whitespace character, same as [ \t\n\x0B\f\r]

         * @return this builder
         */
        fun space(): Builder {
            return this.add("(?:\\s)")
        }

        /**
         * Add non-whitespace character: [^\s]

         * @return this builder
         */
        fun nonSpace(): Builder {
            return this.add("(?:\\S)")
        }


        /*
           --- / end of predefined character classes
         */


        fun anyOf(pValue: String): Builder {
            this.add("[" + sanitize(pValue) + "]")
            return this
        }

        /**
         * Shortcut to [.anyOf]

         * @param value - CharSequence every char from can be matched
         * *
         * @return this builder
         */
        fun any(value: String): Builder {
            return this.anyOf(value)
        }

        /**
         * Add expression to match a range (or multiply ranges)
         * Usage: .range(from, to [, from, to ... ])
         * Example: The following matches a hexadecimal number:
         * regex().range( "0", "9", "a", "f") // produce [0-9a-f]

         * @param pArgs - pairs for range
         * *
         * @return this builder
         */
        fun range(vararg pArgs: String): Builder {
            val value = StringBuilder("[")
            var firstInPairPosition = 1
            while (firstInPairPosition < pArgs.size) {
                val from = sanitize(pArgs[firstInPairPosition - 1])
                val to = sanitize(pArgs[firstInPairPosition])

                value.append(from).append("-").append(to)
                firstInPairPosition += 2
            }
            value.append("]")

            return this.add(value.toString())
        }

        fun addModifier(pModifier: Char): Builder {
            when (pModifier) {
                'd' -> modifiers = modifiers or Pattern.UNIX_LINES
                'i' -> modifiers = modifiers or Pattern.CASE_INSENSITIVE
                'x' -> modifiers = modifiers or Pattern.COMMENTS
                'm' -> modifiers = modifiers or Pattern.MULTILINE
                's' -> modifiers = modifiers or Pattern.DOTALL
                'u' -> modifiers = modifiers or Pattern.UNICODE_CASE
                'U' -> modifiers = modifiers or Pattern.UNICODE_CHARACTER_CLASS
                else -> {
                }
            }

            return this
        }

        fun removeModifier(pModifier: Char): Builder {
            when (pModifier) {
                'd' -> modifiers = modifiers and Pattern.UNIX_LINES.inv()
                'i' -> modifiers = modifiers and Pattern.CASE_INSENSITIVE.inv()
                'x' -> modifiers = modifiers and Pattern.COMMENTS.inv()
                'm' -> modifiers = modifiers and Pattern.MULTILINE.inv()
                's' -> modifiers = modifiers and Pattern.DOTALL.inv()
                'u' -> modifiers = modifiers and Pattern.UNICODE_CASE.inv()
                'U' -> modifiers = modifiers and Pattern.UNICODE_CHARACTER_CLASS.inv()
                else -> {
                }
            }

            return this
        }

        @JvmOverloads fun withAnyCase(pEnable: Boolean = true): Builder {
            if (pEnable) {
                this.addModifier('i')
            } else {
                this.removeModifier('i')
            }
            return this
        }

        fun searchOneLine(pEnable: Boolean): Builder {
            if (pEnable) {
                this.removeModifier('m')
            } else {
                this.addModifier('m')
            }
            return this
        }

        /**
         * Convenient method to show that string usage count is exact count, range count or simply one or more
         * Usage:
         * regex().multiply("abc")                                  // Produce (?:abc)+
         * regex().multiply("abc", null)                            // Produce (?:abc)+
         * regex().multiply("abc", (int)from)                       // Produce (?:abc){from}
         * regex().multiply("abc", (int)from, (int)to)              // Produce (?:abc){from, to}
         * regex().multiply("abc", (int)from, (int)to, (int)...)    // Produce (?:abc)+

         * @param pValue - the string to be looked for
         * *
         * @param count  - (optional) if passed one or two numbers, it used to show count or range count
         * *
         * @return this builder
         * *
         * @see .oneOrMore
         * @see .then
         * @see .zeroOrMore
         */
        fun multiple(pValue: String, vararg count: Int): Builder {
            return when (count.size) {
                1 -> this.then(pValue).count(count[0])
                2 -> this.then(pValue).count(count[0], count[1])
                else -> this.then(pValue).oneOrMore()
            }
        }

        /**
         * Adds "+" char to regexp
         * Same effect as [.atLeast] with "1" argument
         * Also, used by [.multiple] when second argument is null, or have length more than 2

         * @return this builder
         * *
         * @since 1.2
         */
        fun oneOrMore(): Builder {
            return this.add("+")
        }

        /**
         * Adds "*" char to regexp, means zero or more times repeated
         * Same effect as [.atLeast] with "0" argument

         * @return this builder
         * *
         * @since 1.2
         */
        fun zeroOrMore(): Builder {
            return this.add("*")
        }

        /**
         * Add count of previous group
         * for example:
         * .find("w").count(3) // produce - (?:w){3}

         * @param count - number of occurrences of previous group in expression
         * *
         * @return this Builder
         */
        fun count(count: Int): Builder {
            this.source.append("{").append(count).append("}")
            return this
        }

        /**
         * Produce range count
         * for example:
         * .find("w").count(1, 3) // produce (?:w){1,3}

         * @param from - minimal number of occurrences
         * *
         * @param to   - max number of occurrences
         * *
         * @return this Builder
         * *
         * @see .count
         */
        fun count(from: Int, to: Int): Builder {
            this.source.append("{").append(from).append(",").append(to).append("}")
            return this
        }

        /**
         * Produce range count with only minimal number of occurrences
         * for example:
         * .find("w").atLeast(1) // produce (?:w){1,}

         * @param from - minimal number of occurrences
         * *
         * @return this Builder
         * *
         * @see .count
         * @see .oneOrMore
         * @see .zeroOrMore
         * @since 1.2
         */
        fun atLeast(from: Int): Builder {
            return this.add("{").add(from.toString()).add(",}")
        }

        /**
         * Add a alternative expression to be matched

         * Issue #32

         * @param pValue - the string to be looked for
         * *
         * @return this builder
         */
        fun or(pValue: String?): Builder {
            this.prefixes.append("(?:")

            val opened = countOccurrencesOf(this.prefixes.toString(), "(")
            val closed = countOccurrencesOf(this.suffixes.toString(), ")")

            if (opened >= closed) {
                this.suffixes = StringBuilder(")" + this.suffixes.toString())
            }

            this.add(")|(?:")
            if (pValue != null) {
                this.then(pValue)
            }
            return this
        }

        /**
         * Adds an alternative expression to be matched
         * based on an array of values

         * @param pValues - the strings to be looked for
         * *
         * @return this builder
         * *
         * @since 1.3
         */
        fun oneOf(vararg pValues: String): Builder {
            if (pValues != null && pValues.size > 0) {
                this.add("(?:")
                for (i in pValues.indices) {
                    val value = pValues[i]
                    this.add("(?:")
                    this.add(value)
                    this.add(")")
                    if (i < pValues.size - 1) {
                        this.add("|")
                    }
                }
                this.add(")")
            }
            return this
        }

        /**
         * Adds capture - open brace to current position and closed to suffixes

         * @return this builder
         */
        fun capture(): Builder {
            this.suffixes.append(")")
            return this.add("(")
        }

        /**
         * Shortcut for [.capture]

         * @return this builder
         * *
         * @since 1.2
         */
        fun capt(): Builder {
            return this.capture()
        }

        /**
         * Same as [.capture], but don't save result
         * May be used to set count of duplicated captures, without creating a new saved capture
         * Example:
         * // Without group() - count(2) applies only to second capture
         * regex().group()
         * .capt().range("0", "1").endCapt().tab()
         * .capt().digit().count(5).endCapt()
         * .endGr().count(2);

         * @return this builder
         * *
         * @since 1.2
         */
        fun group(): Builder {
            this.suffixes.append(")")
            return this.add("(?:")
        }

        /**
         * Close brace for previous capture and remove last closed brace from suffixes
         * Can be used to continue build regex after capture or to add multiply captures

         * @return this builder
         */
        fun endCapture(): Builder {
            if (this.suffixes.indexOf(")") != -1) {
                this.suffixes.setLength(suffixes.length - 1)
                return this.add(")")
            } else {
                throw IllegalStateException("Can't end capture (group) when it not started")
            }
        }

        /**
         * Shortcut for [.endCapture]

         * @return this builder
         * *
         * @since 1.2
         */
        fun endCapt(): Builder {
            return this.endCapture()
        }

        /**
         * Closes current unnamed and unmatching group
         * Shortcut for [.endCapture]
         * Use it with [.group] for prettify code
         * Example:
         * regex().group().maybe("word").count(2).endGr()

         * @return this builder
         * *
         * @since 1.2
         */
        fun endGr(): Builder {
            return this.endCapture()
        }
    }
    /**
     * Mark the expression to start at the beginning of the line
     * Same as [.startOfLine] with true arg

     * @return this builder
     */
    /**
     * Mark the expression to end at the last character of the line
     * Same as [.endOfLine] with true arg

     * @return this builder
     */
    /**
     * Turn ON matching with ignoring case
     * Example:
     * // matches "a"
     * // matches "A"
     * regex().find("a").withAnyCase()

     * @return this builder
     */

    /**
     * Test that full string matches regular expression

     * @param pToTest - string to check match
     * *
     * @return true if matches exact string, false otherwise
     */
    fun testExact(pToTest: String?): Boolean {
        var ret = false
        if (pToTest != null) {
            ret = pattern.matcher(pToTest).matches()
        }
        return ret
    }

    /**
     * Test that full string contains regex

     * @param pToTest - string to check match
     * *
     * @return true if string contains regex, false otherwise
     */
    fun test(pToTest: String?): Boolean {
        var ret = false
        if (pToTest != null) {
            ret = pattern.matcher(pToTest).find()
        }
        return ret
    }

    /**
     * Extract exact group from string

     * @param toTest - string to extract from
     * *
     * @param group  - group to extract
     * *
     * @return extracted group
     * *
     * @since 1.1
     */
    @JvmOverloads fun getText(toTest: String, group: Int = 0): String {
        val m = pattern.matcher(toTest)
        val result = StringBuilder()
        while (m.find()) {
            result.append(m.group(group))
        }
        return result.toString()
    }

    /**
     * Extract exact group from string and add it to list

     * @param toTest - string to extract from
     * *
     * @param group  - group to extract
     * *
     * @return list of extracted groups
     */
    fun getTextGroups(toTest: String, group: Int): List<String> {
        val groups = ArrayList<String>()
        val m = pattern.matcher(toTest)
        while (m.find()) {
            groups.add(m.group(group))
        }
        return groups
    }

    override fun toString(): String {
        return pattern.pattern()
    }

    companion object {

        /**
         * Creates new instance of VerbalExpression builder from cloned builder

         * @param pBuilder - instance to clone
         * *
         * @return new VerbalExpression.Builder copied from passed
         * *
         * @since 1.1
         */
        fun builder(pBuilder: Builder): Builder {
            val builder = Builder()

            //Using created StringBuilder
            builder.prefixes.append(pBuilder.prefixes)
            builder.source.append(pBuilder.source)
            builder.suffixes.append(pBuilder.suffixes)
            builder.modifiers = pBuilder.modifiers

            return builder
        }

        /**
         * Creates new instance of VerbalExpression builder

         * @return new VerbalExpression.Builder
         * *
         * @since 1.1
         */
        fun builder(): Builder {
            return Builder()
        }
    }
}
