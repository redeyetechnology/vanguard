package ltd.redeye.vanguard.message

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

abstract class VanguardMessageBag {
    fun deserialize(message: String, tagResolver: TagResolver?): Component {
        return if (tagResolver != null) {
            MiniMessage.miniMessage()
                .deserialize(message, tagResolver)
                .decoration(TextDecoration.ITALIC, false)
        } else {
            MiniMessage.miniMessage()
                .deserialize(message)
                .decoration(TextDecoration.ITALIC, false)
        }
    }
}