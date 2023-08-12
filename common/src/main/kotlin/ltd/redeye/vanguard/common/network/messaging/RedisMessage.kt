package ltd.redeye.vanguard.common.network.messaging

import com.google.gson.Gson
import ltd.redeye.vanguard.common.network.messaging.internal.InternalRedisMessage

class RedisMessage<T>(val origin: String, val destinations: List<String>, val channel: String, val message: T) {

    companion object {
        internal fun <T> from(gson: Gson, message: InternalRedisMessage, type: Class<T>?): RedisMessage<T> {
            return RedisMessage(
                message.origin,
                message.destinations,
                message.channel,
                gson.fromJson(message.messageAsString, type)
            )
        }
    }
}