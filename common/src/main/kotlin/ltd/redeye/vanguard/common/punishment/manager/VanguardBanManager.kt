package ltd.redeye.vanguard.common.punishment.manager

import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.manager.type.BanManager
import ltd.redeye.vanguard.common.punishment.type.Ban
import java.time.Duration
import java.util.*

class VanguardBanManager(val core: VanguardCore) : BanManager {
    fun getActiveBan(uniqueId: UUID): Ban? {
        return core.storageDriver.getActiveBan(uniqueId)
    }

    override fun isBanned(vanguardPlayer: VanguardPlayer): Boolean {
        val activeBan = getActiveBan(vanguardPlayer.uuid)
        return activeBan != null
    }

    override fun isIpBanned(address: String): Boolean {
        val activeBan = getActiveBan(address)
        return activeBan != null
    }

    override fun getActiveBan(vanguardPlayer: VanguardPlayer): Ban? {
        return getActiveBan(vanguardPlayer.uuid)
    }

    override fun getActiveBan(address: String): Ban? {
        return core.storageDriver.getActiveBan(address)
    }

    override fun ban(vanguardPlayer: VanguardPlayer, reason: String?, source: VanguardOrigin?, duration: Duration?) {

        val expires: Date = if (duration != null) {
            Date.from(java.time.Instant.now().plus(duration))
        } else {
            Date(0)
        }

        val ban = Ban(
            id = UUID.randomUUID(),
            target = vanguardPlayer.uuid.toString(),
            targetName = vanguardPlayer.knownNames.first(),
            reason = reason,
            source = source.toString(),
            created = Date(),
            updated = Date(),
            expires = expires,
            ip = false,
            active = true
        )

        core.storageDriver.addPunishment(ban)
    }

    override fun unban(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?) {
        val activeBan = getActiveBan(vanguardPlayer)
        if (activeBan != null) {
            activeBan.active = false
            activeBan.updated = Date()
            core.storageDriver.savePunishment(activeBan)
        }
    }

    override fun banIp(vanguardPlayer: VanguardPlayer, reason: String?, source: VanguardOrigin?, duration: Duration?) {
        vanguardPlayer.knownIps.forEach {
            banIp(it, vanguardPlayer.lastKnownName ?: core.messages.unknown, reason, source, duration)
        }
    }

    override fun banIp(
        address: String,
        targetName: String,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?
    ) {
        val expires: Date = if (duration != null) {
            Date.from(java.time.Instant.now().plus(duration))
        } else {
            Date(0)
        }

        val ban = Ban(
            id = UUID.randomUUID(),
            target = address,
            targetName = targetName,
            reason = reason,
            source = source.toString(),
            created = Date(),
            updated = Date(),
            expires = expires,
            ip = true,
            active = true
        )

        core.storageDriver.addPunishment(ban)
    }

    override fun unbanIp(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?) {
        vanguardPlayer.knownIps.forEach {
            unbanIp(it, source)
        }
    }

    override fun unbanIp(address: String, source: VanguardOrigin?) {
        val activeBan = getActiveBan(address)
        if (activeBan != null) {
            activeBan.active = false
            activeBan.updated = Date()
            core.storageDriver.savePunishment(activeBan)
        }
    }
}