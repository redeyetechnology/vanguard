package ltd.redeye.vanguard.common.punishment.manager

import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.manager.type.WarnManager
import ltd.redeye.vanguard.common.punishment.type.Warning
import java.time.Duration
import java.util.*

class VanguardWarnManager(private val core: VanguardCore) : WarnManager {

    override fun warn(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    ) {

        val warning = Warning(
            id = UUID.randomUUID(),
            target = vanguardPlayer.uuid.toString(),
            targetName = vanguardPlayer.knownNames.first(),
            reason = reason,
            source = source.toString(),
            created = Date(),
            updated = Date(),
            scope = scope
        )

        core.storageDriver.addPunishment(warning)

    }

    override fun getWarns(vanguardPlayer: VanguardPlayer, scope: String): Set<Warning> {
        return core.storageDriver.getWarns(vanguardPlayer, scope)
    }

    override fun getWarns(uuid: UUID, scope: String): Set<Warning> {
        return core.storageDriver.getWarns(uuid, scope)
    }

}