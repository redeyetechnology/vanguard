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