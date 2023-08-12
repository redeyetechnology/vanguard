package ltd.redeye.vanguard.common.network.messaging

import com.google.gson.Gson
import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.network.messaging.internal.InternalRedisMessage
import org.redisson.api.RTopic
import org.redisson.api.RedissonClient
import java.util.function.Consumer

class RedisChannelImpl<T>(
    private val redissonClient: RedissonClient,
    private val gson: Gson,
    private val channel: String,
    private val type: Class<T>
) : RedisChannel<T> {

    private val rTopic: RTopic = redissonClient.getTopic(channel)
    private val channelListeners: MutableList<RedisChannelListener<T>> = mutableListOf()

    override fun channel(): String {
        return channel
    }

    override fun type(): Class<T> {
        return type
    }

    override fun client(): RedissonClient {
        return redissonClient
    }

    override fun topic(): RTopic {
        return rTopic
    }

    override fun gson(): Gson {
        return this.gson
    }

    override fun send(message: T) {
        this.send(emptyList(), message)
    }

    override fun send(server: String, message: T) {
        this.send(listOf(server), message)
    }

    override fun send(servers: List<String>, message: T) {
        this.send(InternalRedisMessage(VanguardCore.instance.config.serverName, servers, channel, gson.toJson(message)))
    }

    private fun send(internalRedisMessage: InternalRedisMessage) {
        this.send(gson.toJson(internalRedisMessage))
    }

    private fun send(message: String) {
        try {
            topic().publish(message)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }

    override fun listen(consumer: Consumer<RedisMessage<T>>) {
        channelListeners.add(RedisChannelListener(this, consumer))
    }

    override fun listen(selfSend: Boolean, consumer: Consumer<RedisMessage<T>>) {
        channelListeners.add(RedisChannelListener(this, consumer, selfSend))
    }

    override fun close() {
        topic().removeAllListeners()
    }
}