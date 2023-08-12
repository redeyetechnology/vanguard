package uk.co.redeyetechnologies.vanguard.punishment

import uk.co.redeyetechnologies.vanguard.punishment.type.ActivePunishment
import uk.co.redeyetechnologies.vanguard.punishment.type.Punishment
import java.util.*

data class Mute(
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
