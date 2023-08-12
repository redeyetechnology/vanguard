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

package ltd.redeye.vanguard.common.storage

import ltd.redeye.vanguard.common.api.origin.VanguardOrigin
import ltd.redeye.vanguard.common.player.VanguardPlayer
import ltd.redeye.vanguard.common.punishment.type.*
import java.util.UUID

/**
 * This interface represents a storage driver for Vanguard.
 *
 * All the necessary methods to interact with the underlying database or storage system are
 * provided in this interface. Different implementations of this driver can be created based on
 * different storage mechanisms (e.g. MongoDB, SQL).
 */
interface VanguardStorageDriver {

    /**
     * Enum that represents the supported types of storage drivers for Vanguard.
     */
    enum class DriverType {
        MONGO
    }

    /**
     * Initialises the storage driver. This is usually where the connection to the storage system
     * would be established.
     */
    fun initialise()

    /**
     * Shuts down the storage driver, releasing any resources and closing any open connections.
     */
    fun shutdown()

    /**
     * Loads a player's data from the storage system.
     *
     * @param uuid The UUID of the player.
     * @return The [VanguardPlayer] data associated with the given UUID.
     */
    fun loadPlayer(uuid: UUID): VanguardPlayer

    /**
     * Saves a player's data to the storage system.
     *
     * @param player The [VanguardPlayer] data to be saved.
     */
    fun savePlayer(player: VanguardPlayer)

    /**
     * @return The name of the storage driver.
     */
    fun driverName(): String

    /**
     * Retrieves all punishments associated with a player.
     *
     * @param vanguardPlayer The player in question.
     * @return A set of [Punishment] objects.
     */
    fun getPunishments(vanguardPlayer: VanguardPlayer): Set<Punishment>

    /**
     * Retrieves all bans associated with a player.
     *
     * @param vanguardPlayer The player in question.
     * @return A set of [Ban] objects representing all bans tied to the specified player.
     */
    fun getBans(vanguardPlayer: VanguardPlayer): Set<Ban>

    /**
     * Retrieves all mutes associated with a player.
     *
     * @param vanguardPlayer The player in question.
     * @return A set of [Mute] objects representing all mutes tied to the specified player.
     */
    fun getMutes(vanguardPlayer: VanguardPlayer): Set<Mute>

    /**
     * Retrieves all kicks associated with a player.
     *
     * @param vanguardPlayer The player in question.
     * @return A set of [Kick] objects representing all kicks tied to the specified player.
     */
    fun getKicks(vanguardPlayer: VanguardPlayer): Set<Kick>

    /**
     * Retrieves all warnings associated with a player.
     *
     * @param vanguardPlayer The player in question.
     * @return A set of [Warning] objects representing all warnings tied to the specified player.
     */
    fun getWarns(vanguardPlayer: VanguardPlayer): Set<Warning>

    /**
     * Retrieves all currently active punishments associated with a player.
     *
     * @param vanguardPlayer The player in question.
     * @return A set of [ActivePunishment] objects representing all active punishments for the specified player.
     */
    fun getActivePunishments(vanguardPlayer: VanguardPlayer): Set<ActivePunishment>

    /**
     * Retrieves the currently active ban, if any, associated with a player.
     *
     * @param vanguardPlayer The player in question.
     * @return An optional [Ban] object representing the active ban for the specified player, or null if none is present.
     */
    fun getActiveBan(vanguardPlayer: VanguardPlayer): Ban?

    /**
     * Retrieves the currently active ban, if any, associated with a player. This specific method is used on a prejoin
     * event, where the player has not yet been loaded into the Vanguard system.
     *
     * @param uuid The player in question.
     * @return An optional [Ban] object representing the active ban for the specified player, or null if none is present.
     */
    fun getActiveBan(uuid: UUID): Ban?

    /**
     * Retrieves the currently active mute, if any, associated with a player.
     *
     * @param vanguardPlayer The player in question.
     * @return An optional [Mute] object representing the active mute for the specified player, or null if none is present.
     */
    fun getActiveMute(vanguardPlayer: VanguardPlayer): Mute?


    /**
     * Adds a punishment to the storage system.
     *
     * @param punishment The [Punishment] object to be added.
     * @return Whether the punishment was successfully added.
     */
    fun addPunishment(punishment: Punishment): Boolean

    /**
     * Removes a punishment from the storage system, lifting it and removing it from any logs.
     *
     * @param punishment The [Punishment] object to be removed.
     * @return Whether the punishment was successfully removed.
     */
    fun removePunishment(punishment: Punishment): Boolean

    /**
     * Retrieves the audit log associated with an operator.
     *
     * @param operator The [VanguardOrigin] representing the operator.
     * @return The audit log for the given operator.
     */
    fun getAuditLog(operator: VanguardOrigin): List<Punishment>

    /**
     * Retrieves the audit log associated with an operator and a specific player.
     *
     * @param operator The [VanguardOrigin] representing the operator.
     * @param vanguardPlayer The [VanguardPlayer] in question.
     * @return The audit log for the given operator and player.
     */
    fun getAuditLog(operator: VanguardOrigin, vanguardPlayer: VanguardPlayer): List<Punishment>

    /**
     * Retrieves any ban on the specified IP address.
     *
     * @param address The IP address in question.
     * @return The [Ban] object representing the ban on the given IP address, or null if none is present.
     */
    fun getActiveBan(address: String): Ban?

    /**
     * Save a pre-existing punishment to the storage system.
     *
     * @param punishment The [Punishment] object to be saved.
     */
    fun savePunishment(punishment: Punishment)
}