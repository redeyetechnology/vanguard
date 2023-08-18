package ltd.redeye.vanguard.common.message.serialization

import ltd.redeye.vanguard.common.message.VanguardMessageBag
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.bossbar.BossBar

/**
 * Serialized Message BossBar Implementation
 * used for sending over message brokers, no values are null, but can be empty.
 * In the event of progress, it is set to 1.0 if not present.
 */
data class SerializedMessageBossBar(
    var title: String,
    var progress: Float = 1.0F,
    var style: BossBar.Overlay,
    var color: BossBar.Color
) : VanguardMessageBag() {

    fun send(audience: Audience) {

        if (title.isEmpty()) {
            return
        }

        audience.showBossBar(
            BossBar.bossBar(
                deserializeGson(title),
                progress,
                color,
                style
            )
        )
    }
}