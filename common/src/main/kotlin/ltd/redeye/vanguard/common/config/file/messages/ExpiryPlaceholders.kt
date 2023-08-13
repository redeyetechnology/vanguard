package ltd.redeye.vanguard.common.config.file.messages

import org.spongepowered.configurate.objectmapping.ConfigSerializable


@ConfigSerializable
data class ExpiryPlaceholders(
    var temporary: String = "<date> (<countdown>)",
    var permanent: String = "no expiry (permanent)"
)