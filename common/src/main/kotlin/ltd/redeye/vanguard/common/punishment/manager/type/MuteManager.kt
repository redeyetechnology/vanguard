package ltd.redeye.vanguard.common.punishment.manager.type

import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.type.Mute
import java.time.Duration
import java.util.*

interface MuteManager {
    fun mute(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    )

    fun unmute(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?, scope: String)

    fun muteIp(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    )

    fun unmuteIp(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?, scope: String)

    fun muteIp(
        address: String,
        targetName: String,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    )

    fun unmuteIp(address: String, source: VanguardOrigin?, scope: String)

    fun isMuted(vanguardPlayer: VanguardPlayer, scope: String): Boolean

    fun isIpMuted(address: String, scope: String): Boolean

    fun getActiveMute(vanguardPlayer: VanguardPlayer, scope: String): Mute?

    fun getActiveMute(address: String, scope: String): Mute?
    fun getActiveMute(uuid: UUID, scope: String): Mute?
}