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
import ltd.redeye.vanguard.common.message.section.MessageTitle
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import net.kyori.adventure.util.Ticks

/**
 * Serialized Message Title Implementation
 * used for sending over message brokers, no values are null, but can be empty.
 * In the event of fade numbers, they are set to 0 if not present.
 */
data class SerializedMessageTitle(
    var title: String,
    var subtitle: String,
    var fadeIn: Long,
    var stay: Long,
    var fadeOut: Long
) : VanguardMessageBag() {

    fun send(audience: Audience) {

        if (title.isEmpty() && subtitle.isEmpty()) {
            return
        }

        audience.showTitle(
            Title.title(
                if (title.isNotEmpty()) deserializeGson(title) else Component.empty(),
                if (subtitle.isNotEmpty()) deserializeGson(subtitle) else Component.empty(),
                Title.Times.times(
                    Ticks.duration(fadeIn),
                    Ticks.duration(stay),
                    Ticks.duration(fadeOut)
                )
            )
        )
    }

    fun deserialize(): MessageTitle {

        return MessageTitle(
            title,
            subtitle,
            fadeIn,
            stay,
            fadeOut
        )
    }
}