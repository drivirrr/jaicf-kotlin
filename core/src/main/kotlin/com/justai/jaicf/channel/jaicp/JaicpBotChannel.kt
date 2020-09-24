package com.justai.jaicf.channel.jaicp

import com.justai.jaicf.api.BotApi
import com.justai.jaicf.channel.BotChannel
import com.justai.jaicf.channel.http.HttpBotChannel

/**
 * Basic interface for all (native or compatible) JAICP channels.
 */
interface JaicpBotChannel : BotChannel

/**
 * Basic interface for external channels compatible with JAICP channels.
 * These channel can use channel-specific reactions.
 *
 * @see HttpBotChannel
 */
interface JaicpCompatibleBotChannel : JaicpBotChannel, HttpBotChannel

/**
 * Basic interface for external asynchronous channels compatible with JAICP channels.
 * These channel can use channel-specific reactions.
 *
 * @see HttpBotChannel
 */
interface JaicpCompatibleAsyncBotChannel : JaicpBotChannel, HttpBotChannel

/**
 * Basic interface for JAICP-provided channel factories.
 */
interface JaicpChannelFactory {
    /**
     * Type of JAICP channel like "telegram", "facebook", "alexa" and etc.
     */
    val channelType: String
}

/**
 * Channel created with this factory works as a synchronous webhook.
 */
interface JaicpCompatibleChannelFactory : JaicpChannelFactory {
    /**
     * Creates a new [JaicpCompatibleBotChannel] instance.
     *
     * @param botApi the [BotApi] implementation used to process the requests to this channel
     * @see JaicpCompatibleBotChannel
     * @see BotApi
     */
    fun create(botApi: BotApi): JaicpCompatibleBotChannel
}

/**
 * Channel created with this factory receives requests from JAICP and replies asynchronously to the provided API URL.
 */
interface JaicpCompatibleAsyncChannelFactory : JaicpChannelFactory {
    /**
     * Creates a new [JaicpCompatibleAsyncChannelFactory] instance.
     *
     * @param botApi a [BotApi] implementation used to process the requests to this channel
     * @param apiUrl an URL where to send a replies
     */
    fun create(botApi: BotApi, apiUrl: String): JaicpCompatibleAsyncBotChannel
}

