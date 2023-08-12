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

package ltd.redeye.vanguard.common.command.lib

import cloud.commandframework.arguments.parser.ArgumentParseResult
import cloud.commandframework.arguments.parser.ArgumentParser
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.NoInputProvidedException
import ltd.redeye.vanguard.common.exception.VanguardPlayerParseException
import ltd.redeye.vanguard.common.player.VanguardPlayer
import java.util.*

class VanguardPlayerParser<C> : ArgumentParser<C, VanguardPlayer> {
    override fun parse(
        commandContext: CommandContext<C & Any>,
        inputQueue: Queue<String>
    ): ArgumentParseResult<VanguardPlayer> {
        val input = inputQueue.peek()
            ?: return ArgumentParseResult.failure(
                NoInputProvidedException(
                    ltd.redeye.vanguard.common.command.lib.VanguardPlayerParser::class.java,
                    commandContext
                )
            )

        val player = VanguardPlayer.parse(input)

        return if (player == null) {
            ArgumentParseResult.failure(VanguardPlayerParseException(input, commandContext))
        } else {
            ArgumentParseResult.success(player)
        }
    }

    override fun suggestions(commandContext: CommandContext<C>, input: String): MutableList<String> {
        val playerManager = ltd.redeye.vanguard.common.VanguardCore.instance.playerManager
        val onlinePlayers = playerManager.getCachedOnlinePlayers()

        return onlinePlayers.map {
            it.lastKnownName ?: it.uuid.toString()
        }.toMutableList()
    }
}