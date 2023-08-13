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