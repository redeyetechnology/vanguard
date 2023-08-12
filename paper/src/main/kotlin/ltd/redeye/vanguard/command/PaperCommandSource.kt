package ltd.redeye.vanguard.command

import ltd.redeye.vanguard.api.command.types.VanguardCommandSource
import ltd.redeye.vanguard.api.command.types.VanguardPlayerCommandSource
import net.kyori.adventure.audience.Audience
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

open class PaperCommandSource(val sender: CommandSender): VanguardCommandSource<CommandSender> {

    override fun hasPermission(permission: String): Boolean {
        return this.sender.hasPermission(permission)
    }

    override fun isConsole(): Boolean {
        return this.sender is ConsoleCommandSender
    }

    override fun audience(): Audience {
        return this.sender
    }

    companion object {
        fun from(sender: CommandSender): PaperCommandSource {
            if(sender is Player) {
                return PaperPlayerCommandSource(sender)
            }
            return PaperCommandSource(sender)
        }
    }

}