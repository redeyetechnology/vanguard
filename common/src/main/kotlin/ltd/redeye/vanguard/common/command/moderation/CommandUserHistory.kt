package ltd.redeye.vanguard.common.command.moderation

import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import cloud.commandframework.annotations.ProxiedBy
import ltd.redeye.vanguard.common.command.lib.VanguardCommand

class CommandUserHistory : VanguardCommand() {

    @ProxiedBy("history")
    @CommandMethod("userhistory <player> [page]")
    @CommandDescription("View a player's punishment history.")
    @CommandPermission("vanguard.command.userhistory")
    fun userHistory() {
    }

}