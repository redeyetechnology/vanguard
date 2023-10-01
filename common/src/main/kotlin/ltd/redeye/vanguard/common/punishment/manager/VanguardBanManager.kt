/*
 * Vanguard
 * Copyright (C) 2023 RedEye Technologies Limited
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ltd.redeye.vanguard.common.punishment.manager

import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.manager.type.BanManager
import ltd.redeye.vanguard.common.punishment.type.Ban
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.Tag
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.*

class VanguardBanManager(private val core: VanguardCore) : BanManager {
    val logger = LoggerFactory.getLogger(VanguardBanManager::class.java)

    override fun getActiveBan(uuid: UUID, scope: String): Ban? {
        return core.storageDriver.getActiveBan(uuid, scope)
    }

    override fun isBanned(vanguardPlayer: VanguardPlayer, scope: String): Boolean {
        val activeBan = getActiveBan(vanguardPlayer.uuid, scope)
        return activeBan != null
    }

    override fun isIpBanned(address: String, scope: String): Boolean {
        val activeBan = getActiveBan(address, scope)
        return activeBan != null
    }

    override fun getActiveBan(vanguardPlayer: VanguardPlayer, scope: String): Ban? {
        return getActiveBan(vanguardPlayer.uuid, scope)
    }

    override fun getActiveBan(address: String, scope: String): Ban? {
        return core.storageDriver.getActiveBan(address, scope)
    }

    override fun ban(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin,
        duration: Duration?,
        scope: String
    ): Ban {
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
            source = source,
            created = Date(),
            updated = Date(),
            expires = expires,
            ip = false,
            active = true,
            scope = scope
        )

        core.storageDriver.addPunishment(ban).thenAccept {

            try {
                val banMessage = core.playerManager.generateBanMessage(ban)

                core.messagingProxy.kickPlayer(vanguardPlayer.uuid, banMessage, scope)
            } catch (throwable: Exception) {
                throwable.printStackTrace()
            }
        }

        return ban
    }

    override fun unban(vanguardPlayer: VanguardPlayer, source: VanguardOrigin, scope: String) {
        val activeBan = getActiveBan(vanguardPlayer, scope)
        if (activeBan != null) {
            activeBan.active = false
            activeBan.updated = Date()
            core.storageDriver.savePunishment(activeBan)
        }
    }

    override fun banIp(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin,
        duration: Duration?,
        scope: String
    ) : Set<Ban> {
        val bans = mutableSetOf<Ban>()
        vanguardPlayer.knownIps.forEach {
            bans.add(
                banIp(it, vanguardPlayer.lastKnownName ?: core.messages.unknown, reason, source, duration, scope)
            )
        }
        return bans
    }

    override fun banIp(
        address: String,
        targetName: String,
        reason: String?,
        source: VanguardOrigin,
        duration: Duration?,
        scope: String
    ) : Ban {
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
            source = source,
            created = Date(),
            updated = Date(),
            expires = expires,
            ip = true,
            active = true,
            scope = scope
        )

        core.storageDriver.addPunishment(ban).thenAccept {
            val banMessage = core.playerManager.generateBanMessage(ban)

            core.messagingProxy.kickPlayer(address, banMessage, scope)
        }

        return ban
    }

    override fun unbanIp(vanguardPlayer: VanguardPlayer, source: VanguardOrigin, scope: String) {
        vanguardPlayer.knownIps.forEach {
            unbanIp(it, source, scope)
        }
    }

    override fun unbanIp(address: String, source: VanguardOrigin, scope: String) {
        val activeBan = getActiveBan(address, scope)
        if (activeBan != null) {
            activeBan.active = false
            activeBan.updated = Date()
            core.storageDriver.savePunishment(activeBan)
        }
    }

    private fun insertingTag(value: String): Tag {
        return Tag.inserting(Component.text(value))
    }
}