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

package ltd.redeye.vanguard.command

import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import ltd.redeye.vanguard.command.lib.types.VanguardCommandSource
import ltd.redeye.vanguard.command.lib.VanguardCommand

class CommandShowExampleMessage : VanguardCommand() {
    @CommandMethod("vanguard showexamplemessage")
    @CommandPermission("vanguard.command.showexamplemessage")
    @CommandDescription("Show the example message, as defined in messages.yml")
    fun showExampleMessage(sender: VanguardCommandSource<*>) {
        getMessages().exampleMessage.send(sender)
    }
}