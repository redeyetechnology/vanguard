package ltd.redeye.vanguard.paper.network

import ltd.redeye.vanguard.common.message.VanguardMessage
import ltd.redeye.vanguard.common.network.messaging.MessagingProxy
import ltd.redeye.vanguard.common.util.Permissions
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import java.util.*

/**
 * A messaging proxy for when no message broker is available
 */
object PaperSingleMessagingProxy: MessagingProxy {

    override fun alertPlayer(uuid: UUID, message: VanguardMessage) {
        val player = Bukkit.getPlayer(uuid)
        if(player != null) {
            message.send(player)
        }
    }

    override fun kickPlayer(player: UUID, message: Component) {
        Bukkit.getPlayer(player)?.kick(message)
    }

    override fun alertStaff(message: VanguardMessage) {
        Bukkit.getOnlinePlayers().forEach { player ->
            if(player.hasPermission(Permissions.STAFF.permission())) {
                message.send(player)
            }
        }
    }

}