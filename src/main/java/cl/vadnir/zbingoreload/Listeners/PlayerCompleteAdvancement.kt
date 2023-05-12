package cl.vadnir.zbingoreload.Listeners

import cl.vadnir.zbingoreload.ZBingoReload
import cl.vadnir.zbingoreload.Profiles.AdvancementProfile
import cl.vadnir.zbingoreload.Profiles.TeamProfile
import cl.vadnir.zbingoreload.Enums.AdvancementTypes
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

import org.bukkit.event.player.PlayerAdvancementDoneEvent

class PlayerCompleteAdvancement(private val plugin: ZBingoReload): Listener {


    @EventHandler
    public fun onPlayerCompleteAdvancement(event: PlayerAdvancementDoneEvent){

        val advancementKey = event.advancement.key.key

        val player = this.plugin.getPlayerManager().getPlayer(event.player)?: return

        if (player.getTeamId() == "") {
            return
        }

        val team: TeamProfile = this.plugin.getTeamManager().getTeam(
            player.getTeamId())?: return

        val advancement: AdvancementProfile = this.plugin.getAdvancementManager().getAdvancement(
            advancementKey, AdvancementTypes.Advancements) ?: return

        if(team.hasAdvancement(advancement)){
            return
        }
        team.addAdvancement(advancement)
        team.getPlayerList().forEach { it ->
            Bukkit.getPlayer(it.getName())?.let {
                this.plugin.getUltimateAdvancementAPI().getApi().getAdvancement(
                    "bingo:${advancement.getName()}"
                )!!.grant(
                    it
                )
            }
        }
        this.plugin.getMessageUtils().completeAdvancement(advancement, team)
        if(team.getAdvancementList().size >= this.plugin.getAdvancementManager().getAllAdvancements().size){
            this.plugin.getMessageUtils().bingoMessage(team)
        }
    }
}