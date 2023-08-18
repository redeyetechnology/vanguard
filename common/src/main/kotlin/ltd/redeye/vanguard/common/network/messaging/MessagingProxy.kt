package ltd.redeye.vanguard.common.network.messaging

import ltd.redeye.vanguard.common.message.VanguardMessage
import net.kyori.adventure.text.Component
import java.util.UUID

interface MessagingProxy {

    fun alertPlayer(uuid: UUID, message: VanguardMessage)

    fun kickPlayer(player: UUID, message: Component)

    fun alertStaff(message: VanguardMessage)

}