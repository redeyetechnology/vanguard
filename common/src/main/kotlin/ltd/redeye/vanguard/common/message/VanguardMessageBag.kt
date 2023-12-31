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

package ltd.redeye.vanguard.common.message

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

abstract class VanguardMessageBag {
    fun deserialize(message: String, tagResolver: TagResolver?): Component {
        return if (tagResolver != null) {
            MiniMessage.miniMessage()
                .deserialize(message, tagResolver)
                .decoration(TextDecoration.ITALIC, false)
        } else {
            MiniMessage.miniMessage()
                .deserialize(message)
                .decoration(TextDecoration.ITALIC, false)
        }
    }

    fun parseToGson(message: String, tagResolver: TagResolver?): String {
        val parsed = deserialize(message, tagResolver)
        return GsonComponentSerializer.gson().serialize(parsed)
    }

    fun deserializeGson(message: String): Component {
        return GsonComponentSerializer.gson().deserialize(message)
    }
}