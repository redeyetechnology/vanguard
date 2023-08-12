package ltd.redeye.vanguard.api.command

import net.kyori.adventure.audience.ForwardingAudience

/**
 * Represents a source of a command.
 * This can be a player or the console
 */
interface VanguardCommandSource<Source>: ForwardingAudience.Single {

    /**
     * Checks if the source has the given permission.
     * @param permission The permission to check.
     * @return `true` if the source has the permission, `false` otherwise.
     */
    fun hasPermission(permission: String): Boolean

    /**
     * Checks if the source is the console.
     * @return `true` if the source is the console, `false` otherwise.
     */
    fun isConsole(): Boolean

}

/**
 * Represents a source of a command that is a player.
 * @param PLAYER The type of player.
 * @see VanguardCommandSource - The base command source.
 */
interface VanguardPlayerCommandSource<Player>: VanguardCommandSource<Player> {

    /**
     * Gets the player associated with this source.
     * @return The player.
     */
    fun player(): Player

}