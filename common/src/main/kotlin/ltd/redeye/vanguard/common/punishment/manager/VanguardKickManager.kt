package ltd.redeye.vanguard.common.punishment.manager

import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.manager.type.KickManager
import net.kyori.adventure.text.Component
import java.util.*

class VanguardKickManager(private val core: VanguardCore) : KickManager {

    override fun kick(vanguardPlayer: VanguardPlayer, message: Component, scope: String): Boolean {
        return core.messagingProxy.kickPlayer(vanguardPlayer.uuid, message, scope)
    }

    override fun kick(uuid: UUID, message: Component, scope: String): Boolean {
        return core.messagingProxy.kickPlayer(uuid, message, scope)
    }

}