package ltd.redeye.vanguard.common.network.messaging.proxy.internal

import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
data class KickAddressMessage(val address: String, val message: String, val scope: String)