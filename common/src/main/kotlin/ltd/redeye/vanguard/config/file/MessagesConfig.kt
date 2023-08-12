package ltd.redeye.vanguard.config.file

import ltd.redeye.vanguard.message.VanguardMessage
import ltd.redeye.vanguard.message.section.MessageBossBar
import ltd.redeye.vanguard.message.section.MessageSound
import ltd.redeye.vanguard.message.section.MessageTitle
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
class MessagesConfig(
    @Comment("This is an example message, it demonstrates how each message in this configuration file is formatted. Each section ('chat', 'actionbar', etc) is optional, and can be entirely omitted. Each text component uses the MiniMessage messaging format to format text. You can see this message in-game by running /vanguard showexamplemessage")
    var exampleMessage: VanguardMessage = VanguardMessage(
        chat = mutableListOf("<red>Message One", "<#00ff00><bold>Message Two", "<gradient:red:blue>Message Three"),
        "Example Actionbar",
        MessageTitle("Example Title", "Example Subtitle", 10, 10, 10),
        MessageBossBar("Example Bossbar", "blue", "notched_20", 1.0F),
        MessageSound("minecraft:entity.experience_orb.pickup", 1.0F, 1.0F)
    )
)