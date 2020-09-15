package com.justai.jaicf.channel.jaicp.reactions

import com.justai.jaicf.channel.jaicp.dto.Button
import com.justai.jaicf.channel.jaicp.dto.ButtonsReply
import com.justai.jaicf.channel.jaicp.dto.ImageReply
import com.justai.jaicf.reactions.ButtonsReaction
import com.justai.jaicf.reactions.ImageReaction
import com.justai.jaicf.reactions.Reactions

val Reactions.chatwidget
    get() = this as? ChatWidgetReactions

class ChatWidgetReactions : JaicpReactions() {
    override fun image(url: String): ImageReaction {
        replies.add(ImageReply(url))
        return ImageReaction.create(url)
    }

    fun image(imageUrl: String, caption: String? = null): ImageReaction {
        replies.add(ImageReply(imageUrl, caption))
        return ImageReaction.create(imageUrl)
    }

    fun button(text: String, transition: String? = null): ButtonsReaction {
        replies.add(ButtonsReply(Button(text, transition)))
        return ButtonsReaction.create(listOf(text))
    }

    override fun buttons(vararg buttons: String): ButtonsReaction {
        replies.add(ButtonsReply(buttons.map { Button(it) }))
        return ButtonsReaction.create(buttons.asList())
    }

    fun buttons(buttons: List<String>): ButtonsReaction {
        replies.add(ButtonsReply(buttons.map { Button(it) }))
        return ButtonsReaction.create(buttons)
    }
}