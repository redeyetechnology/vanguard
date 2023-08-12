package ltd.redeye.vanguard

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class VanguardPlugin : JavaPlugin() {

    lateinit var vanguard: VanguardCore<Player>

    override fun onEnable() {
        vanguard = VanguardCore()
    }

    override fun onDisable() {
    }

}