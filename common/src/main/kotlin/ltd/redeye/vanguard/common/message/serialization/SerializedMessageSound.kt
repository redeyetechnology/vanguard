package ltd.redeye.vanguard.common.message.serialization

import ltd.redeye.vanguard.common.message.VanguardMessageBag
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound

/**
 * Serialized Message Sound Implementation
 * used for sending over message brokers, no values are null, but can be empty.
 * In the event of volume and pitch, they are set to 1.0 if not present.
 */
data class SerializedMessageSound(
    var sound: String,
    var volume: Float = 1.0F,
    var pitch: Float = 1.0F
) : VanguardMessageBag() {

    fun send(audience: Audience) {

        if (sound.isEmpty()) {
            return
        }

        audience.playSound(
            Sound.sound(
                Key.key(sound),
                Sound.Source.MASTER,
                volume,
                pitch
            )
        )
    }
}