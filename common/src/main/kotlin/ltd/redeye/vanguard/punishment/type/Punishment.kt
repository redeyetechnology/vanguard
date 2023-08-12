package uk.co.redeyetechnologies.vanguard.punishment.type

import java.util.Date
import java.util.UUID

interface Punishment {
    val id: UUID
    val target: String
    val targetName: String
    val reason: String?
    val source: String?
    val created: Date
    val updated: Date
}
