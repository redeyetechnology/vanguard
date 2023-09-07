package ltd.redeye.vanguard.common.punishment.manager.type

import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.type.Warning
import java.time.Duration
import java.util.*

interface WarnManager {

    fun warn(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    )

    fun getWarns(vanguardPlayer: VanguardPlayer, scope: String): Set<Warning>

    fun getWarns(uuid: UUID, scope: String): Set<Warning>

}