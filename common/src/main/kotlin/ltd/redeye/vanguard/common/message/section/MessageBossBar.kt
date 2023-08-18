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

package ltd.redeye.vanguard.common.message.section

import ltd.redeye.vanguard.common.message.VanguardMessageBag
import ltd.redeye.vanguard.common.message.serialization.SerializedMessageBossBar
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.io.Serializable

@ConfigSerializable
data class MessageBossBar(
    var title: String? = null,
    var color: BossBar.Color? = BossBar.Color.BLUE,
    var style: BossBar.Overlay? = BossBar.Overlay.PROGRESS,
    var progress: Float? = 1.0F,
) : Serializable, VanguardMessageBag() {
    fun send(target: Audience, tagResolver: TagResolver?) {
        val title = deserialize(title!!, tagResolver)

        target.showBossBar(
            BossBar.bossBar(
                title,
                progress!!,
                color!!,
                style!!
            )
        )
    }

    fun serialize(tagResolver: TagResolver): SerializedMessageBossBar {
        return SerializedMessageBossBar(
            if(title!!.isNotEmpty()) parseToGson(title.orEmpty(), tagResolver) else "",
            progress!!,
            style!!,
            color!!
        )
    }
}