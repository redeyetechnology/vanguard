package ltd.redeye.vanguard.command.lib.type

import cloud.commandframework.arguments.parser.ArgumentParseResult
import cloud.commandframework.arguments.parser.ArgumentParser
import cloud.commandframework.context.CommandContext
import cloud.commandframework.exceptions.parsing.NoInputProvidedException
import ltd.redeye.vanguard.VanguardCore
import ltd.redeye.vanguard.exception.VanguardPlayerParseException
import ltd.redeye.vanguard.player.VanguardPlayer
import java.util.*

class VanguardPlayerParser<C> : ArgumentParser<C, VanguardPlayer> {
    override fun parse(
        commandContext: CommandContext<C & Any>,
        inputQueue: Queue<String>
    ): ArgumentParseResult<VanguardPlayer> {
        val input = inputQueue.peek()
            ?: return ArgumentParseResult.failure(
                NoInputProvidedException(
                    VanguardPlayerParser::class.java,
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
        val playerManager = VanguardCore.instance.playerManager
        val onlinePlayers = playerManager.getCachedOnlinePlayers()

        return onlinePlayers.map {
            it.lastKnownName ?: it.uuid.toString()
        }.toMutableList()
    }
}