package ltd.redeye.vanguard.common.network.messaging

import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.network.messaging.internal.InternalRedisMessage
import org.redisson.api.listener.MessageListener
import java.util.function.Consumer

class RedisChannelListener<T> constructor(
    val redisChannel: RedisChannelImpl<T>,
    val consumer: Consumer<RedisMessage<T>>,
    val selfSend: Boolean = false
) {

    init {
        this.redisChannel.topic()
            .addListener(
                String::class.java,
                MessageListener { channel: CharSequence?, msg: String? ->
                    try {
                        val internalRedisMessage = redisChannel.gson().fromJson(msg, InternalRedisMessage::class.java)
                        if (!selfSend && !internalRedisMessage.destinations.contains(VanguardCore.instance.config.serverName)) {
                            return@MessageListener   // we do not want this
                        }
                        val redisMessage: RedisMessage<T> =
                            RedisMessage.from(redisChannel.gson(), internalRedisMessage, redisChannel.type())
                        consumer.accept(redisMessage)
                    } catch (throwable: Throwable) {
                        VanguardCore.instance.logger.error(
                            "Error while handling Redis Message for channel {}",
                            redisChannel.channel(),
                            throwable
                        )
                    }
                }
            )
    }
}