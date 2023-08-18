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

package ltd.redeye.vanguard.common.message.serialization

import ltd.redeye.vanguard.common.message.VanguardMessageBag
import net.kyori.adventure.audience.Audience

/**
 * Serialized Vanguard Message Implementation
 * used for sending over message brokers, no values are null, but can be empty.
 */
data class SerializedVanguardMessage(
    var chat: MutableList<String>,
    var actionBar: String,
    var title: SerializedMessageTitle,
    var sound: SerializedMessageSound
) : VanguardMessageBag() {

    fun send(audience: Audience) {

        if (chat.isNotEmpty()) {
            chat.forEach { message ->
                audience.sendMessage(deserializeGson(message))
            }
        }

        if (actionBar.isNotEmpty()) {
            audience.sendActionBar(deserializeGson(actionBar))
        }

        title.send(audience)
        sound.send(audience)
    }

    companion object {

    }

}