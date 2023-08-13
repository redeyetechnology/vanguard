/*
 * Vanguard
 * Copyright (C) 2023 RedEye Technologies Limited
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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