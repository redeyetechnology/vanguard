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

package ltd.redeye.vanguard.common.network.messaging.proxy

import ltd.redeye.vanguard.common.message.VanguardMessage
import ltd.redeye.vanguard.common.message.serialization.SerializedVanguardMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import java.util.UUID

interface MessagingProxy {

    fun alertPlayer(uuid: UUID, message: VanguardMessage, placeholders: TagResolver?): Boolean

    fun kickPlayer(player: UUID, message: Component, scope: String): Boolean

    fun alertStaff(message: VanguardMessage, placeholders: TagResolver?)

    fun alertPlayer(uuid: UUID, message: SerializedVanguardMessage)

    fun alertStaff(message: SerializedVanguardMessage)

}