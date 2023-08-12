package uk.co.redeyetechnologies.vanguard.punishment

import uk.co.redeyetechnologies.vanguard.punishment.type.Punishment
import java.util.*

data class Warning(
    override val id: UUID,
    override val target: String,
    override val targetName: String,
    override val reason: String?,
    override val source: String?,
    override val created: Date,
    override val updated: Date,
) : Punishment
