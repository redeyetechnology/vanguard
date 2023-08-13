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
import ltd.redeye.vanguard.common.punishment.manager.type.BanManager
import ltd.redeye.vanguard.common.punishment.type.Ban
import ltd.redeye.vanguard.common.punishment.type.impl.Punishment
import java.time.Duration
import java.util.*

class VanguardPunishmentManager(private val core: VanguardCore) : BanManager {
    fun getPunishments(vanguardPlayer: VanguardPlayer): List<Punishment> {
        val punishments = core.storageDriver.getPunishments(vanguardPlayer)
        return punishments.sortedBy { it.created }
    }

    private val banManager = VanguardBanManager(core)

    override fun ban(vanguardPlayer: VanguardPlayer, reason: String?, source: VanguardOrigin?, duration: Duration?) {
        banManager.ban(vanguardPlayer, reason, source, duration)
    }

    override fun unban(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?) {
        banManager.unban(vanguardPlayer, source)
    }

    override fun banIp(vanguardPlayer: VanguardPlayer, reason: String?, source: VanguardOrigin?, duration: Duration?) {
        banManager.banIp(vanguardPlayer, reason, source, duration)
    }

    override fun banIp(
        address: String,
        targetName: String,
        reason: String?,
        source: VanguardOrigin?,
        duration: Duration?
    ) {
        banManager.banIp(address, targetName, reason, source, duration)
    }

    override fun unbanIp(vanguardPlayer: VanguardPlayer, source: VanguardOrigin?) {
        banManager.unbanIp(vanguardPlayer, source)
    }

    override fun unbanIp(address: String, source: VanguardOrigin?) {
        banManager.unbanIp(address, source)
    }

    override fun isBanned(vanguardPlayer: VanguardPlayer): Boolean {
        return banManager.isBanned(vanguardPlayer)
    }

    override fun isIpBanned(address: String): Boolean {
        return banManager.isIpBanned(address)
    }

    override fun getActiveBan(vanguardPlayer: VanguardPlayer): Ban? {
        return banManager.getActiveBan(vanguardPlayer)
    }

    override fun getActiveBan(address: String): Ban? {
        return banManager.getActiveBan(address)
    }

    /**
     * WARNING: This method blocks the thread it is called on, as it is designed for use in a player join event. You
     * should consider using the [VanguardPlayer] version of this method instead.
     */
    override fun getActiveBan(uuid: UUID): Ban? {
        return banManager.getActiveBan(uuid)
    }
}