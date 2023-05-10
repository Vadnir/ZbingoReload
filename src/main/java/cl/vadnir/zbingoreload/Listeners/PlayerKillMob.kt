package cl.vadnir.zbingoreload.Listeners

import cl.vadnir.zbingoreload.ZBingoReload
import cl.vadnir.zbingoreload.Profiles.AdvancementProfile
import cl.vadnir.zbingoreload.Profiles.PlayerProfile
import cl.vadnir.zbingoreload.Profiles.TeamProfile
import cl.vadnir.zbingoreload.Enums.AdvancementTypes
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class PlayerKillMob(private val plugin: ZBingoReload) : Listener {

    private val advancements: List<AdvancementProfile> = this.plugin.getAdvancementManager().getAdvancementByType(
        AdvancementTypes.Mobs)

    @EventHandler
    public fun onEntityDeath(event: EntityDeathEvent){

        val entityType: String = event.entityType.name

        if(event.entity.killer == null){
            return
        }

        val player: PlayerProfile = this.plugin.getPlayerManager()
            .getPlayer(event.entity.killer!!) ?: return

        if((this.advancements.filter { it.getCondition() == entityType }).isEmpty()){
            return
        }

        val team: TeamProfile = this.plugin.getTeamManager().getTeam(
            player.getTeamId()!!)?: return

        val advancement: AdvancementProfile = this.plugin.getAdvancementManager().getAdvancement(
            event.entity.type.toString(), AdvancementTypes.Mobs) ?: return

        if(team.hasAdvancement(advancement)){
            return
        }
        team.addAdvancement(advancement)
        //todo: añadir sistema para dar el mns logro al jugador
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