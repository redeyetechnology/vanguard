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

package ltd.redeye.vanguard.common.punishment.type.impl

import ltd.redeye.vanguard.common.VanguardCore
import java.text.SimpleDateFormat
import java.util.Date

interface ActivePunishment : Punishment {
    val expires: Date
    val active: Boolean

    /**
     * Returns the formatted remaining duration of the punishment
     */
    fun getFormattedDuration(): String {
        val end = this.expires
        val start = this.created
        val diff = end.time - start.time
        val seconds = diff / 1000 % 60
        val minutes = diff / (60 * 1000) % 60
        val hours = diff / (60 * 60 * 1000) % 24
        val days = diff / (24 * 60 * 60 * 1000)
        return "${days}d ${hours}h ${minutes}m ${seconds}s"
    }

    /**
     * Returns the formatted expiry date/time of the punishment
     */
    fun getFormattedExpiry(): String {
        return if (this.expires == Date(0)) {
            "Never"
        } else {
            // format expiry date/time as
            // 01 Jan 2021 11:00PM GMT
            val date = this.expires
            SimpleDateFormat(VanguardCore.instance.config.dateFormat).format(date)
        }
    }

}