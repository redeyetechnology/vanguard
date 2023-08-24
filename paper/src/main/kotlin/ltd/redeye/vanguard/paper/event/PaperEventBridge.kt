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

package ltd.redeye.vanguard.paper.event

import ltd.redeye.vanguard.common.api.event.VanguardEventBridge
import ltd.redeye.vanguard.common.punishment.type.Ban
import ltd.redeye.vanguard.common.punishment.type.Kick
import ltd.redeye.vanguard.common.punishment.type.Mute
import ltd.redeye.vanguard.common.punishment.type.Warning
import ltd.redeye.vanguard.paper.VanguardPaperPlugin

class PaperEventBridge : VanguardEventBridge {
    private fun fireEvent(event: VanguardEvent) {
        VanguardPaperPlugin.instance.server.pluginManager.callEvent(event)
    }

    override fun playerBanned(ban: Ban) {
        fireEvent(VanguardBanEvent(ban))
    }

    override fun playerKicked(kick: Kick) {
        fireEvent(VanguardKickEvent(kick))
    }

    override fun playerMuted(mute: Mute) {
        fireEvent(VanguardMuteEvent(mute))
    }

    override fun playerUnbanned(ban: Ban){
        fireEvent(VanguardBanCancelledEvent(ban))
    }

    override fun playerUnmuted(mute: Mute) {
        fireEvent(VanguardMuteCancelledEvent(mute))
    }

    override fun playerWarned(warn: Warning) {
        fireEvent(VanguardWarningEvent(warn))
    }
}