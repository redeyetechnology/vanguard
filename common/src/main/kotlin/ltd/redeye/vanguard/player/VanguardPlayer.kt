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

package ltd.redeye.vanguard.player

import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import ltd.redeye.vanguard.VanguardCore
import ltd.redeye.vanguard.punishment.type.Punishment
import net.kyori.adventure.audience.Audience
import java.util.UUID

@Entity
data class VanguardPlayer(
    @Id
    val uuid: UUID,
    val knownNames: MutableSet<String> = mutableSetOf(),
    val knownIps: MutableSet<String> = mutableSetOf(),
    val lastKnownName: String? = null,
) {

    fun isOnline(): Boolean {
        return VanguardCore.instance.playerManager.isOnline(this)
    }
    fun audience(): Audience {
        return VanguardCore.instance.playerAdapter.audience(this)
    }

    fun punishments(): List<Punishment> {
        return VanguardCore.instance.punishmentManager.getPunishments(this)
    }

    companion object {
        fun parse(input: String): VanguardPlayer? {
            return null
        }
    }
}
