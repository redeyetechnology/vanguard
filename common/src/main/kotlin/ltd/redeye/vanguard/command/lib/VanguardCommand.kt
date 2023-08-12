package ltd.redeye.vanguard.command.lib

import ltd.redeye.vanguard.VanguardCore
import ltd.redeye.vanguard.config.file.MessagesConfig
import ltd.redeye.vanguard.player.VanguardPlayer

abstract class VanguardCommand {
    fun message(player: VanguardPlayer, message: String) {

    }

    fun getMessages(): MessagesConfig {
        return VanguardCore.instance.messages

    }
}