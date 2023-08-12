package ltd.redeye.vanguard.common.punishment.manager.type

import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.type.Ban
import java.time.Duration

interface BanManager {
    fun ban(vanguardPlayer: VanguardPlayer, reason: String?, source: VanguardOrigin?, duration: Duration?)

    fun unban(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?)

    fun banIp(vanguardPlayer: VanguardPlayer, reason: String?, source: VanguardOrigin?, duration: Duration?)

    fun unbanIp(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?)

    fun banIp(address: String, targetName: String, reason: String?, source: VanguardOrigin?, duration: Duration?)

    fun unbanIp(address: String, source: VanguardOrigin?)

    fun isBanned(vanguardPlayer: VanguardPlayer): Boolean

    fun isIpBanned(address: String): Boolean

    fun getActiveBan(vanguardPlayer: VanguardPlayer): Ban?

    fun getActiveBan(address: String): Ban?
}