package ltd.redeye.vanguard.paper.event

import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import org.bukkit.event.HandlerList
import java.time.Duration
import java.util.UUID

/**
 * This event is emitted if a player is IP banned from the server. Developers should consider subscribing to the event bus
 * for Vanguard's common module, as this event originates from there. However, this Bukkit event is provided for the
 * convenience of developers who do not wish to use the common module, and also allows internal mechanisms within
 * Vanguard to subscribe to the event and handle it appropriately.
 * <br />
 * As IP bans can be issued without a player, the player or UUID fields may be null. However, the IP address will always
 * be present.
 *
 * @param uuid The UUID of the player.
 * @param player The VanguardPlayer object of the player, if they are online.
 * @param ip The IP address of the player.
 * @param originName The name of the origin of the ban.
 * @param origin The VanguardOrigin object of the origin of the ban.
 * @param reason The reason for the ban.
 * @param duration The duration of the ban.
 * @param server The server the warning was issued on (as defined in the server's configuration).
 */
data class VanguardIpBanEvent(
    val uuid: UUID?,
    val player: VanguardPlayer?,
    val ip: String,
    val originName: String,
    val origin: VanguardOrigin?,
    val reason: String,
    val duration: Duration?,
    val server: String
) : VanguardEvent() {
    companion object {
        val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}