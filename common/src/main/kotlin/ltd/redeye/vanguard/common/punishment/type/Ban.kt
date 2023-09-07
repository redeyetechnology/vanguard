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

package ltd.redeye.vanguard.common.punishment.type

import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import ltd.redeye.vanguard.common.punishment.type.impl.ActivePunishment
import ltd.redeye.vanguard.common.punishment.type.impl.Punishment
import java.util.UUID
import java.util.Date

@Entity
data class Ban(
    val ip: Boolean,
    override var expires: Date,
    override var active: Boolean,
    @Id
    override var id: UUID,
    override var target: String,
    override var targetName: String,
    override var reason: String?,
    override var source: String?,
    override var created: Date,
    override var updated: Date,
    override val scope: String
) : ActivePunishment, Punishment
