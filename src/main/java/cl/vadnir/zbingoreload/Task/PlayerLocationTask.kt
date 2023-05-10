package cl.vadnir.zbingoreload.Task

import cl.vadnir.zbingoreload.ZBingoReload
import cl.vadnir.zbingoreload.Profiles.AdvancementProfile
import cl.vadnir.zbingoreload.Profiles.TeamProfile
import cl.vadnir.zbingoreload.Enums.AdvancementTypes
import org.bukkit.Bukkit


class PlayerLocationTask(private var plugin: ZBingoReload) {
    init {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
            for (player in this.plugin.getPlayerManager().getAllPlayers()){

                if(!Bukkit.getPlayer(player.getName())!!.isOnline){
                    continue
                }

                if(player.getTeamId().isEmpty()){
                    continue
                }

                val team: TeamProfile = this.plugin.getTeamManager().getTeam(
                    player.getTeamId()
                )?: continue

                val advancement: AdvancementProfile = this.plugin.getAdvancementManager().getAdvancement(
                    Bukkit.getPlayer(player.getName())!!.location.block.biome.name, AdvancementTypes.Biomes) ?: continue

                if(team.hasAdvancement(advancement)){
                    continue
                }
                team.addAdvancement(advancement)
                team.getPlayerList().forEach {
                    Bukkit.getPlayer(it.getName())?.let { it ->
                        this.plugin.getUltimateAdvancementAPI().getApi().getAdvancement(
                            "bingo:${advancement.getName()}"
                        )!!.grant(
                            it
                        )
                    }
                }
                this.plugin.getMessageUtils().dispatchMessage(advancement.getCompleteMessage())
            }
        }, 0L, 40L)
    }
}

