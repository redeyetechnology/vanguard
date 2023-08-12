package ltd.redeye.vanguard.api.origin

import java.util.UUID

/**
 * A VanguardOrigin is a representation of any entity which can perform a Vanguard action, such as a player, console,
 * external service, etc.
 *
 * @param uuid The UUID of the origin. For non-player entities, including consoles, this should be the zero UUID.
 * @param name The display name/affiliated name of the origin.
 */
class VanguardOrigin(val uuid: UUID, val name: String) {
    constructor(name: String) : this(UUID(0, 0), name)
}