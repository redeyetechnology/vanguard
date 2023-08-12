package ltd.redeye.vanguard.command

import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import ltd.redeye.vanguard.command.lib.VanguardCommand

class CommandShowExampleMessage : VanguardCommand() {
    @CommandMethod("vanguard showexamplemessage")
    @CommandPermission("vanguard.command.showexamplemessage")
    @CommandDescription("Show the example message, as defined in messages.yml")
    fun showExampleMessage(sender: Any) {
    }
}