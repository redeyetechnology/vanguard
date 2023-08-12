package ltd.redeye.vanguard.common.network

import ltd.redeye.vanguard.common.VanguardCore
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.client.codec.StringCodec
import org.redisson.config.Config

class NetworkManager {

    val redisson: RedissonClient

    init {
        val networkConfig = VanguardCore.instance.config.network

        val config = Config()
        val singleServerConfig = config.useSingleServer()
        singleServerConfig.address = networkConfig.host

        if (networkConfig.password.isNotEmpty()) singleServerConfig.password = networkConfig.password

        config.codec = StringCodec()

        redisson = Redisson.create(config)
    }

}