package ltd.redeye.vanguard.common.plugin

import ltd.redeye.vanguard.common.command.lib.types.PlatformCommandInitializer
import ltd.redeye.vanguard.common.network.messaging.MessagingProxy
import ltd.redeye.vanguard.common.player.VanguardPlayerAdapter

/**
 * An interface for Vanguard plugins to fetch information for the common module.
 * Initially wasn't required, but we only want to create some factories/services if enabled in core.
 */
interface VanguardPlugin {

    fun createVanguardPlayerAdapter(): VanguardPlayerAdapter<*>
    fun slf4jLogger(): org.slf4j.Logger
    fun version(): String
    fun dataFolder(): java.io.File
    fun createPlatformCommandInitializer(): PlatformCommandInitializer
    fun defaultMessagingProxy(): MessagingProxy
    fun createRedisMessagingProxy(): MessagingProxy

}