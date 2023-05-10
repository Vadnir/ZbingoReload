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
        if(this.plugin.getStorageManager().getPlayerData(event.player.name) == null){
            Bukkit.getConsoleSender().sendMessage("no data")
            this.plugin.getPlayerManager().createPlayer(event.player)
        }else {
            Bukkit.getConsoleSender().sendMessage("si data")
            this.plugin.getPlayerManager().addPlayer(
                this.plugin.getStorageManager().getPlayerData(event.player.name)!!
            )
            //val player = this.plugin.getPlayerManager().getPlayer(event.player.name)
            //this.plugin.getTeamManager().getTeam(player!!.getTeamId()!!)!!.addPlayer(
            //   player
            //)
        }
        this.plugin.getUltimateAdvancementAPI().getApi().updatePlayer(event.player)
    }

    @EventHandler
    public fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        val playerName: String = event.player.name

        this.plugin.getPlayerManager().removePlayer(playerName)
    }
}