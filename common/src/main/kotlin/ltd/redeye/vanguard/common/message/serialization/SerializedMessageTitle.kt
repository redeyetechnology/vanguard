package ltd.redeye.vanguard.common.message.serialization

import ltd.redeye.vanguard.common.message.VanguardMessageBag
import ltd.redeye.vanguard.common.message.section.MessageTitle
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import net.kyori.adventure.util.Ticks

/**
 * Serialized Message Title Implementation
 * used for sending over message brokers, no values are null, but can be empty.
 * In the event of fade numbers, they are set to 0 if not present.
 */
data class SerializedMessageTitle(
    var title: String,
    var subtitle: String,
    var fadeIn: Long,
    var stay: Long,
    var fadeOut: Long
) : VanguardMessageBag() {

    fun send(audience: Audience) {

        if (title.isEmpty() && subtitle.isEmpty()) {
            return
        }

        audience.showTitle(
            Title.title(
                if (title.isNotEmpty()) deserializeGson(title) else Component.empty(),
                if (subtitle.isNotEmpty()) deserializeGson(subtitle) else Component.empty(),
                Title.Times.times(
                    Ticks.duration(fadeIn),
                    Ticks.duration(stay),
                    Ticks.duration(fadeOut)
                )
            )
        )
    }

    fun deserialize(): MessageTitle {

        return MessageTitle(
            title,
            subtitle,
            fadeIn,
            stay,
            fadeOut
        )
    }
}