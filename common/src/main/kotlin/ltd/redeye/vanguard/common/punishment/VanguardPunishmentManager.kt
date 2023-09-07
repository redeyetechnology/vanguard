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

package ltd.redeye.vanguard.common.punishment

import ltd.redeye.vanguard.common.VanguardCore
import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.manager.VanguardBanManager
import ltd.redeye.vanguard.common.punishment.manager.VanguardKickManager
import ltd.redeye.vanguard.common.punishment.manager.VanguardMuteManager
import ltd.redeye.vanguard.common.punishment.manager.VanguardWarnManager
import ltd.redeye.vanguard.common.punishment.manager.type.BanManager
import ltd.redeye.vanguard.common.punishment.manager.type.KickManager
import ltd.redeye.vanguard.common.punishment.manager.type.MuteManager
import ltd.redeye.vanguard.common.punishment.manager.type.WarnManager
import ltd.redeye.vanguard.common.punishment.type.Ban
import ltd.redeye.vanguard.common.punishment.type.Mute
import ltd.redeye.vanguard.common.punishment.type.Warning
import ltd.redeye.vanguard.common.punishment.type.impl.Punishment
import net.kyori.adventure.text.Component
import java.time.Duration
import java.util.*

class VanguardPunishmentManager(private val core: VanguardCore) : BanManager, MuteManager, KickManager, WarnManager {

    companion object {
        const val GLOBAL_SCOPE = "global"
    }

    fun getPunishments(vanguardPlayer: VanguardPlayer): List<Punishment> {
        val punishments = core.storageDriver.getPunishments(vanguardPlayer, core.config.serverName)
        return punishments.sortedBy { it.created }
    }

    private val banManager = VanguardBanManager(core)
    private val muteManager = VanguardMuteManager(core)
    private val kickManager = VanguardKickManager(core)
    private val warnManager = VanguardWarnManager(core)

    override fun ban(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    ) {
        banManager.ban(vanguardPlayer, reason, source, duration, scope)
    }

    override fun unban(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?, scope: String) {
        banManager.unban(vanguardPlayer, source, scope)
    }

    override fun banIp(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    ) {
        banManager.banIp(vanguardPlayer, reason, source, duration, scope)
    }

    override fun banIp(
        address: String,
        targetName: String,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    ) {
        banManager.banIp(address, targetName, reason, source, duration, scope)
    }

    override fun unbanIp(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?, scope: String) {
        banManager.unbanIp(vanguardPlayer, source, scope)
    }

    override fun unbanIp(address: String, source: VanguardOrigin?, scope: String) {
        banManager.unbanIp(address, source, scope)
    }

    override fun isBanned(vanguardPlayer: VanguardPlayer, scope: String): Boolean {
        return banManager.isBanned(vanguardPlayer, scope)
    }

    override fun isIpBanned(address: String, scope: String): Boolean {
        return banManager.isIpBanned(address, scope)
    }

    override fun getActiveBan(vanguardPlayer: VanguardPlayer, scope: String): Ban? {
        return banManager.getActiveBan(vanguardPlayer, scope)
    }

    override fun getActiveBan(address: String, scope: String): Ban? {
        return banManager.getActiveBan(address, scope)
    }

    /**
     * WARNING: This method blocks the thread it is called on, as it is designed for use in a player join event. You
     * should consider using the [VanguardPlayer] version of this method instead.
     */
    override fun getActiveBan(uuid: UUID, scope: String): Ban? {
        return banManager.getActiveBan(uuid, scope)
    }

    override fun mute(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    ) {
        muteManager.mute(vanguardPlayer, reason, source, duration, scope)
    }

    override fun unmute(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?, scope: String) {
        muteManager.unmute(vanguardPlayer, source, scope)
    }

    override fun muteIp(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    ) {
        muteManager.muteIp(vanguardPlayer, reason, source, duration, scope)
    }

    override fun muteIp(
        address: String,
        targetName: String,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    ) {
        muteManager.muteIp(address, targetName, reason, source, duration, scope)
    }

    override fun unmuteIp(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?, scope: String) {
        muteManager.unmuteIp(vanguardPlayer, source, scope)
    }

    override fun unmuteIp(address: String, source: VanguardOrigin?, scope: String) {
        muteManager.unmuteIp(address, source, scope)
    }

    override fun isMuted(vanguardPlayer: VanguardPlayer, scope: String): Boolean {
        return muteManager.isMuted(vanguardPlayer, scope)
    }

    override fun isIpMuted(address: String, scope: String): Boolean {
        return muteManager.isIpMuted(address, scope)
    }

    override fun getActiveMute(vanguardPlayer: VanguardPlayer, scope: String): Mute? {
        return muteManager.getActiveMute(vanguardPlayer, scope)
    }

    override fun getActiveMute(address: String, scope: String): Mute? {
        return muteManager.getActiveMute(address, scope)
    }

    override fun getActiveMute(uuid: UUID, scope: String): Mute? {
        return muteManager.getActiveMute(uuid, scope)
    }

    override fun kick(vanguardPlayer: VanguardPlayer, message: Component, scope: String): Boolean {
        return kickManager.kick(vanguardPlayer, message, scope)
    }

    override fun kick(uuid: UUID, message: Component, scope: String): Boolean {
        return kickManager.kick(uuid, message, scope)
    }

    override fun warn(
        vanguardPlayer: VanguardPlayer,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?,
        scope: String
    ) {
        return warnManager.warn(vanguardPlayer, reason, source, duration, scope)
    }

    override fun getWarns(vanguardPlayer: VanguardPlayer, scope: String): Set<Warning> {
        return warnManager.getWarns(vanguardPlayer, scope)
    }

    override fun getWarns(uuid: UUID, scope: String): Set<Warning> {
        return warnManager.getWarns(uuid, scope)
    }
    
}