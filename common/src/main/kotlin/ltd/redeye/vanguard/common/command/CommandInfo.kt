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

package ltd.redeye.vanguard.common.command

import cloud.commandframework.annotations.CommandMethod
import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.command.lib.VanguardCommand
import ltd.redeye.vanguard.common.command.lib.types.VanguardCommandSource

class CommandInfo : VanguardCommand() {

    @CommandMethod("vanguard")
    fun info(sender: VanguardCommandSource<*>) {
        val version = VanguardCore.instance.version
        message(sender, "Running <#22D3EE>Vanguard</#22D3EE> <white>${version}</white> by <#22D3EE>RedEye Technologies</#22D3EE>")

        if (sender.hasPermission("vanguard.command.help")) {
            message(sender, "Type <#22D3EE>/vanguard help</#22D3EE> for a list of commands.")
        } else {
            message(sender, "You do not have permission to interact with this command.")
        }
    }

}