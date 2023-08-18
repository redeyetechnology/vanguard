package ltd.redeye.vanguard.common.message.serialization

import ltd.redeye.vanguard.common.message.VanguardMessageBag
import net.kyori.adventure.audience.Audience

/**
 * Serialized Vanguard Message Implementation
 * used for sending over message brokers, no values are null, but can be empty.
 */
data class SerializedVanguardMessage(
    var chat: MutableList<String>,
    var actionBar: String,
    var title: SerializedMessageTitle,
    var bossBar: SerializedMessageBossBar,
    var sound: SerializedMessageSound
) : VanguardMessageBag() {

    fun send(audience: Audience) {

        if (chat.isNotEmpty()) {
            chat.forEach { message ->
                audience.sendMessage(deserializeGson(message))
            }
        }

        if (actionBar.isNotEmpty()) {
            audience.sendActionBar(deserializeGson(actionBar))
        }

        title.send(audience)
        bossBar.send(audience)
        sound.send(audience)

    }

    companion object {

    }

}