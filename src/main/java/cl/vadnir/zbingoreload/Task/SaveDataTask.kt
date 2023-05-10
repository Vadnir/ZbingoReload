package cl.vadnir.zbingoreload.Task

import cl.vadnir.zbingoreload.ZBingoReload
import org.bukkit.Bukkit

class SaveDataTask(private var plugin: ZBingoReload) {

    init {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, Runnable {
                this.plugin.getStorageManager().saveTeams(
                    this.plugin.getTeamManager().getAllTeam()
                )
            })
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, Runnable {
                this.plugin.getStorageManager().savePlayers(
                    this.plugin.getPlayerManager().getAllPlayers()
                )
            })
        }, 0L, 200L)
    }
}