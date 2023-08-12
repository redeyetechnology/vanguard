package uk.co.redeyetechnologies.vanguard.punishment.type

import java.util.Date

interface ActivePunishment {
    val expires: Date
    val active: Boolean
}