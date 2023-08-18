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
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound

/**
 * Serialized Message Sound Implementation
 * used for sending over message brokers, no values are null, but can be empty.
 * In the event of volume and pitch, they are set to 1.0 if not present.
 */
data class SerializedMessageSound(
    var sound: String,
    var volume: Float = 1.0F,
    var pitch: Float = 1.0F
) : VanguardMessageBag() {

    fun send(audience: Audience) {

        if (sound.isEmpty()) {
            return
        }

        audience.playSound(
            Sound.sound(
                Key.key(sound),
                Sound.Source.MASTER,
                volume,
                pitch
            )
        )
    }
}