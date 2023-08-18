package ltd.redeye.vanguard.common.network.messaging

import ltd.redeye.vanguard.common.message.VanguardMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import java.util.UUID

interface MessagingProxy {

    fun alertPlayer(uuid: UUID, message: VanguardMessage, placeholders: TagResolver?)

    fun kickPlayer(player: UUID, message: Component)

    fun alertStaff(message: VanguardMessage, placeholders: TagResolver?)

}