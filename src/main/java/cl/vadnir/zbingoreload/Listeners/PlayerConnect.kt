package cl.vadnir.zbingoreload.Listeners

import cl.vadnir.zbingoreload.ZBingoReload
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent


class PlayerConnect(private val plugin: ZBingoReload) : Listener {

    @EventHandler
    public fun onPlayerJoinEvent(event: PlayerJoinEvent) {

        this.plugin.getUltimateAdvancementAPI().getApi().updatePlayer(event.player)

        if(this.plugin.getStorageManager().getPlayerData(event.player.name) == null){
            this.plugin.getPlayerManager().createPlayer(event.player)
        }else {
            this.plugin.getPlayerManager().addPlayer(
                this.plugin.getStorageManager().getPlayerData(event.player.name)?: throw Exception("some probleme")
            )
            val player = this.plugin.getPlayerManager().getPlayer(event.player.name)?: return

            if (player.getTeamId() == ""){
                return
            }

            this.plugin.getTeamManager().getTeam(player.getTeamId())!!.addPlayer(
               player
            )
        }
    }

    @EventHandler
    public fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        val playerName: String = event.player.name

        this.plugin.getPlayerManager().removePlayer(playerName)
    }
}