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

import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.command.lib.VanguardCommand
import ltd.redeye.vanguard.common.command.lib.types.VanguardCommandSource

class CommandInfo : VanguardCommand() {

    @CommandMethod("vanguard")
    @CommandDescription("Displays information about Vanguard.")
    fun baseInfo(sender: VanguardCommandSource<*>) {
        val version = VanguardCore.instance.version
        message(
            sender,
            "Running <#22D3EE>Vanguard</#22D3EE> <white>${version}</white> by <#22D3EE>RedEye Technologies</#22D3EE>"
        )

        if (sender.hasPermission("vanguard.command.help")) {
            message(sender, "Type <#22D3EE>/vanguard help</#22D3EE> for a list of commands.")
        } else {
            message(sender, "You do not have permission to interact with this command.")
        }
    }

    @CommandMethod("vanguard info")
    @CommandDescription("Displays detailed information about Vanguard.")
    @CommandPermission("vanguard.command.info")
    fun detailView(sender: VanguardCommandSource<*>) {
        val version = VanguardCore.instance.version

        message(sender, "Vanguard Version: <#22D3EE>${version}</#22D3EE>")
        message(sender, "  DB Driver: <#22D3EE>${VanguardCore.instance.config.database.driver}</#22D3EE>")
        message(
            sender,
            "  Network Mode: <#22D3EE>${if (VanguardCore.instance.config.network.enabled) "Redis Cross-Server" else "Standalone"}</#22D3EE>"
        )
        message(sender, "  Cache Size: <#22D3EE>0</#22D3EE>")
        message(sender, "  Punishments: <#22D3EE>0</#22D3EE>")
        message(sender, "Updates/Docs at <#22D3EE>https://vanguard.redeye.ltd</#22D3EE>")
    }

}