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