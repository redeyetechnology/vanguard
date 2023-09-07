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

package ltd.redeye.vanguard.common.api.origin

import dev.morphia.annotations.Entity
import java.util.*

/**
 * A VanguardOrigin is a representation of any entity which can perform a Vanguard action, such as a player, console,
 * external service, etc.
 *
 * @param uuid The UUID of the origin. For non-player entities, including consoles, this should be the zero UUID.
 * @param name The display name/affiliated name of the origin.
 */
@Entity
class VanguardOrigin(var uuid: UUID, var name: String) {
    companion object {
        val CONSOLE = VanguardOrigin(UUID(0, 0), "CONSOLE")
    }

    constructor(name: String) : this(UUID(0, 0), name)
    constructor() : this(UUID(0, 0), "")
}