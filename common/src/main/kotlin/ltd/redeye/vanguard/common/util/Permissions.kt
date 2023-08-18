package ltd.redeye.vanguard.common.util

enum class Permissions {
    STAFF;

    fun permission(): String {
        return "vanguard.${this.name.lowercase()}"
    }
}