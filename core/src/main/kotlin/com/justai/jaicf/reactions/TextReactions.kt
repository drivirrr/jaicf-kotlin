package com.justai.jaicf.reactions

import com.justai.jaicf.api.TextResponse

/**
 * A simple [Reactions] implementation that builds a [TextResponse].
 *
 * @property response a text response that should be filled by scenario logic
 * @see TextResponse
 */
class TextReactions(
    override val response: TextResponse
): ResponseReactions<TextResponse>(response) {

    /**
     * Fills the response with provided text.
     * Appends a new line to the response on each invocation.
     *
     * @param text a raw text to append to the response
     */
    override fun say(text: String) {
        response.text = when {
            response.text.isNullOrBlank() -> text
            else -> "${response.text}\n$text"
        }
    }

}

val Reactions.text
    get() = this as? TextReactions