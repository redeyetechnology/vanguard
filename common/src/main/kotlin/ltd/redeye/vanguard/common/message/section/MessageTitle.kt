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
import ltd.redeye.vanguard.common.message.serialization.SerializedMessageTitle
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.title.Title
import net.kyori.adventure.util.Ticks
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.io.Serializable

@ConfigSerializable
data class MessageTitle(
    var title: String? = null,
    var subtitle: String? = null,
    var fadeIn: Long? = 20,
    var stay: Long? = 60,
    var fadeOut: Long? = 20
) : Serializable, VanguardMessageBag() {
    fun send(target: Audience, tagResolver: TagResolver? = null) {
        if (title != null) {
            target.showTitle(
                Title.title(
                    deserialize(title!!, tagResolver),
                    deserialize(subtitle!!, tagResolver),
                    Title.Times.times(
                        Ticks.duration(fadeIn!!),
                        Ticks.duration(stay!!),
                        Ticks.duration(fadeOut!!)
                    )
                )
            )
        }
    }

    fun serialize(tagResolver: TagResolver): SerializedMessageTitle {
        return SerializedMessageTitle(
            if(title!!.isNotEmpty()) parseToGson(title!!, tagResolver) else "",
            if(subtitle!!.isNotEmpty()) parseToGson(subtitle!!, tagResolver) else "",
            fadeIn!!,
            stay!!,
            fadeOut!!
        )
    }
}