package ltd.redeye.vanguard.message.section

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.io.Serializable

@ConfigSerializable
data class MessageSound (
    var sound: String = "experience_orb.pickup",
    var pitch: Float = 1.0F,
    var volume: Float = 1.0F
): Serializable {
    fun send(target: Audience) {
        target.playSound(
            Sound.sound(
                Key.key(sound),
                Sound.Source.MASTER,
                volume,
                pitch
            ),
        )
    }
}