package ltd.redeye.vanguard.common.punishment.manager.type

import ltd.redeye.vanguard.common.player.VanguardPlayer
import net.kyori.adventure.text.Component
import java.util.UUID

interface KickManager {

    fun kick(vanguardPlayer: VanguardPlayer, message: Component, scope: String): Boolean

    fun kick(uuid: UUID, message: Component, scope: String): Boolean

}