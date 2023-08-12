package ltd.redeye.vanguard.common.network.messaging.internal

internal class InternalRedisMessage(
    val origin: String,
    val destinations: List<String>,
    val channel: String,
    val messageAsString: String
)