package ltd.redeye.vanguard.punishment

import ltd.redeye.vanguard.VanguardCore
import ltd.redeye.vanguard.player.VanguardPlayer
import ltd.redeye.vanguard.punishment.type.Punishment

class VanguardPunishmentManager(private val core: VanguardCore) {
    fun getPunishments(vanguardPlayer: VanguardPlayer): List<Punishment> {
        val punishments = core.storageDriver.getPunishments(vanguardPlayer)
        return punishments.sortedBy { it.created }
    }
}