package ltd.redeye.vanguard.common.network.messaging

import com.google.gson.Gson
import org.redisson.api.RTopic
import org.redisson.api.RedissonClient
import java.util.function.Consumer

interface RedisChannel<T> : AutoCloseable {
    fun channel(): String
    fun type(): Class<T>
    fun client(): RedissonClient
    fun topic(): RTopic
    fun gson(): Gson
    fun send(message: T)
    fun send(server: String, message: T)
    fun send(servers: List<String>, message: T)
    fun listen(consumer: Consumer<RedisMessage<T>>)
    fun listen(selfSend: Boolean, consumer: Consumer<RedisMessage<T>>)
}