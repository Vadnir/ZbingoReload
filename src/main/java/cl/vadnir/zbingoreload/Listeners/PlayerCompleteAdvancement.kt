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

    private val advancements: List<AdvancementProfile> = this.plugin.getAdvancementManager()
        .getAdvancementByType(AdvancementTypes.Advancements)

    @EventHandler
    public fun onPlayerCompleteAdvancement(event: PlayerAdvancementDoneEvent){

        val advancement_key = event.advancement.key.key

        if((this.advancements.filter { it.getCondition() == advancement_key }).isEmpty()){
            return
        }

        val team: TeamProfile = this.plugin.getTeamManager().getTeam(
            this.plugin.getPlayerManager().getPlayer(event.player.name)!!.getTeamId()!!)?: return

        val advancement: AdvancementProfile = this.plugin.getAdvancementManager().getAdvancement(
            advancement_key, AdvancementTypes.Advancements) ?: return

        if(team.hasAdvancement(advancement)){
            return
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
}