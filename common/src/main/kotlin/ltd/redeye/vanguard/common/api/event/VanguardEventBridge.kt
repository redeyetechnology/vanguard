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

package ltd.redeye.vanguard.common.api.event

import ltd.redeye.vanguard.common.punishment.type.Ban
import ltd.redeye.vanguard.common.punishment.type.Kick
import ltd.redeye.vanguard.common.punishment.type.Mute
import ltd.redeye.vanguard.common.punishment.type.Warning

/**
 * The Vanguard Event Bridge is the bridge between the Vanguard API and the Vanguard Plugin. Essentially, it allows
 * server-isolated events to be sent to the Vanguard Plugin, which then handles them accordingly. These can be triggered
 * from this server, or from another using the Redis RPC system.
 */
interface VanguardEventBridge {
    /**
     * This function is called when a player is banned on the server. It will send a message to the Vanguard Plugin
     * which will then handle it accordingly.
     *
     * @param ban The ban object, which contains all the information about the ban.
     */
    fun playerBanned(ban: Ban)

    /**
     * This function is called when a player is kicked on the server. It will send a message to the Vanguard Plugin
     * which will then handle it accordingly.
     *
     * @param kick The kick object, which contains all the information about the kick.
     */
    fun playerKicked(kick: Kick)

    /**
     * This function is called when a player is muted on the server. It will send a message to the Vanguard Plugin
     * which will then handle it accordingly.
     *
     * @param mute The mute object, which contains all the information about the mute.
     */
    fun playerMuted(mute: Mute)

    /**
     * This function is called when a player is unbanned on the server. It will send a message to the Vanguard Plugin
     * which will then handle it accordingly.
     *
     * @param ban The ban object, which contains all the information about the ban.
     */
    fun playerUnbanned(ban: Ban)

    /**
     * This function is called when a player is unmuted on the server. It will send a message to the Vanguard Plugin
     * which will then handle it accordingly.
     *
     * @param mute The mute object, which contains all the information about the mute.
     */
    fun playerUnmuted(mute: Mute)

    /**
     * This function is called when a player is warned on the server. It will send a message to the Vanguard Plugin
     * which will then handle it accordingly.
     *
     * @param warn The warn object, which contains all the information about to warn.
     */
    fun playerWarned(warn: Warning)
}