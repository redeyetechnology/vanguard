package ltd.redeye.vanguard.message.section

import ltd.redeye.vanguard.message.VanguardMessageBag
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.io.Serializable

@ConfigSerializable
data class MessageBossBar(
    var title: String? = null,
    var color: String? = "blue",
    var style: String? = "progress",
    var progress: Float? = 1.0F,
) : Serializable, VanguardMessageBag() {
    fun send(target: Audience, tagResolver: TagResolver?) {
        val title = deserialize(title!!, tagResolver)

        target.showBossBar(
            BossBar.bossBar(
                title,
                progress!!,
                BossBar.Color.valueOf(color!!.uppercase()),
                BossBar.Overlay.valueOf(style!!.uppercase())
            )
        )
    }
}