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

import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.punishment.type.impl.Punishment
import java.util.*

data class Warning(
    override val id: UUID,
    override val target: String,
    override val targetName: String,
    override val reason: String?,
    override val source: VanguardOrigin,
    override val created: Date,
    override val updated: Date,
    override val scope: String
) : Punishment {
    constructor() : this(UUID(0, 0), "", "", "", VanguardOrigin(), Date(), Date(), "")
}
