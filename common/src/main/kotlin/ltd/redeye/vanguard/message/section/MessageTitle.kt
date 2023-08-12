package ltd.redeye.vanguard.message.section

import ltd.redeye.vanguard.message.VanguardMessageBag
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.title.Title
import net.kyori.adventure.util.Ticks
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.io.Serializable

@ConfigSerializable
data class MessageTitle(
    var title: String? = null,
    var subtitle: String? = null,
    var fadeIn: Long? = 20,
    var stay: Long? = 60,
    var fadeOut: Long? = 20
) : Serializable, VanguardMessageBag() {
    fun send(target: Audience, tagResolver: TagResolver? = null) {
        if (title != null) {
            target.showTitle(
                Title.title(
                    deserialize(title!!, tagResolver),
                    deserialize(subtitle!!, tagResolver),
                    Title.Times.times(
                        Ticks.duration(fadeIn!!),
                        Ticks.duration(stay!!),
                        Ticks.duration(fadeOut!!)
                    )
                )
            )
        }
    }
}