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

package ltd.redeye.vanguard.storage.mongo

import com.mongodb.client.MongoClients
import dev.morphia.Datastore
import dev.morphia.Morphia
import dev.morphia.query.filters.Filters
import ltd.redeye.vanguard.VanguardCore
import ltd.redeye.vanguard.api.origin.VanguardOrigin
import ltd.redeye.vanguard.player.VanguardPlayer
import ltd.redeye.vanguard.punishment.type.*
import ltd.redeye.vanguard.storage.VanguardStorageDriver
import java.util.UUID

class MongoStorageDriver : VanguardStorageDriver {
    private lateinit var datastore: Datastore
    override fun initialise() {
        val config = VanguardCore.instance.config.database.mongo

        val credentials = if (config.username.isNotEmpty()) {
            "${config.username}:${config.password}@"
        } else {
            ""
        }

        val connectionString = "mongodb://${credentials}${config.host}:${config.port}?authSource=${config.authSource}"

        datastore = Morphia.createDatastore(MongoClients.create(connectionString))

        // We're using a deprecated statement here due to the configuration requirements for a plugin.
        @Suppress("removal", "DEPRECATION")
        datastore.mapper.mapPackage("ltd.redeye.vanguard")
    }

    override fun shutdown() {
        TODO("Not yet implemented")
    }

    override fun loadPlayer(uuid: UUID): VanguardPlayer {
        val player = datastore.find(VanguardPlayer::class.java).filter(Filters.eq("uuid", uuid)).first()

        if (player == null) {
            val newPlayer = VanguardPlayer(uuid)
            datastore.save(newPlayer)
            return newPlayer
        }

        return player
    }

    override fun savePlayer(player: VanguardPlayer) {
        datastore.save(player)
    }

    override fun driverName(): String {
        return "MongoDB"
    }

    override fun getPunishments(vanguardPlayer: VanguardPlayer): Set<Punishment> {
        val punishments = mutableSetOf<Punishment>()

        val bans = datastore.find(Ban::class.java).filter(Filters.eq("target", vanguardPlayer.uuid)).toList()
        val mutes = datastore.find(Ban::class.java).filter(Filters.eq("target", vanguardPlayer.uuid)).toList()
        val warns = datastore.find(Ban::class.java).filter(Filters.eq("target", vanguardPlayer.uuid)).toList()

        punishments.addAll(bans)
        punishments.addAll(mutes)
        punishments.addAll(warns)

        return punishments
    }

    override fun addPunishment(punishment: Punishment): Boolean {
        datastore.save(punishment)
        return true
    }

    override fun removePunishment(punishment: Punishment): Boolean {
        datastore.delete(punishment)
        return true
    }

    override fun getAuditLog(operator: VanguardOrigin): List<Punishment> {
        return datastore.find(Punishment::class.java).filter(Filters.eq("source", operator.uuid)).toList()
    }

    override fun getAuditLog(operator: VanguardOrigin, vanguardPlayer: VanguardPlayer): List<Punishment> {
        return datastore.find(Punishment::class.java)
            .filter(Filters.eq("source", operator.uuid), Filters.eq("target", vanguardPlayer.uuid)).toList()
    }

    override fun getBans(vanguardPlayer: VanguardPlayer): Set<Ban> {
        return datastore.find(Ban::class.java).filter(Filters.eq("target", vanguardPlayer.uuid)).toSet()
    }

    override fun getMutes(vanguardPlayer: VanguardPlayer): Set<Mute> {
        return datastore.find(Mute::class.java).filter(Filters.eq("target", vanguardPlayer.uuid)).toSet()
    }

    override fun getKicks(vanguardPlayer: VanguardPlayer): Set<Kick> {
        return datastore.find(Kick::class.java).filter(Filters.eq("target", vanguardPlayer.uuid)).toSet()
    }

    override fun getWarns(vanguardPlayer: VanguardPlayer): Set<Warning> {
        return datastore.find(Warning::class.java).filter(Filters.eq("target", vanguardPlayer.uuid)).toSet()
    }

    override fun getActivePunishments(vanguardPlayer: VanguardPlayer): Set<ActivePunishment> {
        val activeMutes = datastore.find(Mute::class.java)
            .filter(Filters.eq("target", vanguardPlayer.uuid), Filters.eq("active", true)).toList()
        val activeBans = datastore.find(Ban::class.java)
            .filter(Filters.eq("target", vanguardPlayer.uuid), Filters.eq("active", true)).toList()

        val activePunishments = mutableSetOf<ActivePunishment>()
        activePunishments.addAll(activeMutes)
        activePunishments.addAll(activeBans)

        return activePunishments
    }

    override fun getActiveBan(vanguardPlayer: VanguardPlayer): Ban? {
        return datastore.find(Ban::class.java)
            .filter(Filters.eq("target", vanguardPlayer.uuid), Filters.eq("active", true)).first()
    }

    override fun getActiveMute(vanguardPlayer: VanguardPlayer): Mute? {
        return datastore.find(Mute::class.java)
            .filter(Filters.eq("target", vanguardPlayer.uuid), Filters.eq("active", true)).first()
    }
}