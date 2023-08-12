package ltd.redeye.vanguard.message

import ltd.redeye.vanguard.player.VanguardPlayer
import ltd.redeye.vanguard.message.section.MessageBossBar
import ltd.redeye.vanguard.message.section.MessageSound
import ltd.redeye.vanguard.message.section.MessageTitle
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import java.io.Serializable

@ConfigSerializable
data class VanguardMessage(
    var chat: MutableList<String>? = mutableListOf(""),
    var actionbar: String?,
    var title: MessageTitle?,
    var bossbar: MessageBossBar?,
    var sound: MessageSound?
) : Serializable, VanguardMessageBag() {

    fun send(target: Audience, tagResolver: TagResolver? = null) {
        if (chat != null && chat!!.size > 0) {
            for (message in chat!!) {
                target.sendMessage(deserialize(message, tagResolver))
            }
        }

        if (actionbar != null) {
            target.sendActionBar(deserialize(actionbar!!, tagResolver))
        }

        if (title != null) {
            title!!.send(target, tagResolver)
        }

        if (bossbar != null) {
            bossbar!!.send(target, tagResolver)
        }

        if (sound != null) {
            sound!!.send(target)
        }
    }

    fun send(target: VanguardPlayer, tagResolver: TagResolver? = null) {
        send(target.audience(), tagResolver)
    }
}