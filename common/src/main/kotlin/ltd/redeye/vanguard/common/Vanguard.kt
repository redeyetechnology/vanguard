package ltd.redeye.vanguard.common

import ltd.redeye.vanguard.common.api.VanguardApi

object Vanguard {
    fun api(): VanguardApi {
        return VanguardCore.instance.api
    }
}