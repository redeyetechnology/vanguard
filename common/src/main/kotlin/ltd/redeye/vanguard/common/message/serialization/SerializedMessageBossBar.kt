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
import net.kyori.adventure.bossbar.BossBar

/**
 * Serialized Message BossBar Implementation
 * used for sending over message brokers, no values are null, but can be empty.
 * In the event of progress, it is set to 1.0 if not present.
 */
data class SerializedMessageBossBar(
    var title: String,
    var progress: Float = 1.0F,
    var style: BossBar.Overlay,
    var color: BossBar.Color
) : VanguardMessageBag() {

    fun send(audience: Audience) {

        if (title.isEmpty()) {
            return
        }

        audience.showBossBar(
            BossBar.bossBar(
                deserializeGson(title),
                progress,
                color,
                style
            )
        )
    }
}