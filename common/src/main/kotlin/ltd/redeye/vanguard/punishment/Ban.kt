package ltd.redeye.vanguard.punishment

import ltd.redeye.vanguard.punishment.type.ActivePunishment
import ltd.redeye.vanguard.punishment.type.Punishment
import java.util.*

data class Ban(
    val ip: Boolean,
    override val expires: Date,
    override val active: Boolean,
    override val id: UUID,
    override val target: String,
    override val targetName: String,
    override val reason: String?,
    override val source: String?,
    override val created: Date,
    override val updated: Date,
) : ActivePunishment, Punishment
