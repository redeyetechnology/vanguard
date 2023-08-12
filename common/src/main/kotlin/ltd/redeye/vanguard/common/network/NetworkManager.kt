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

package ltd.redeye.vanguard.common.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.network.messaging.RedisChannel
import ltd.redeye.vanguard.common.network.messaging.RedisChannelImpl
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.client.codec.StringCodec
import org.redisson.config.Config
import kotlin.reflect.KClass

class NetworkManager {

    val redisson: RedissonClient
    val gson: Gson = GsonBuilder().create()

    init {
        val networkConfig = VanguardCore.instance.config.network

        val config = Config()
        val singleServerConfig = config.useSingleServer()
        singleServerConfig.address = networkConfig.host

        if (networkConfig.password.isNotEmpty()) singleServerConfig.password = networkConfig.password

        config.codec = StringCodec()

        redisson = Redisson.create(config)
    }

    fun <T : Any> register(channel: String, type: KClass<T>): RedisChannel<T> {
        return register(channel, type.java)
    }

    fun <T : Any> register(channel: String, type: Class<T>): RedisChannel<T> {
        return RedisChannelImpl(redisson, gson, channel, type)
    }

}