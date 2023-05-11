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

    @EventHandler
    public fun onEntityDeath(event: EntityDeathEvent) {

        if (event.entity.killer == null) {
            return
        }

        val player: PlayerProfile = this.plugin.getPlayerManager()
            .getPlayer(event.entity.killer!!) ?: return

        if (player.getTeamId() == "") {
            return
        }

        val team: TeamProfile = this.plugin.getTeamManager().getTeam(
            player.getTeamId()
        ) ?: return

        val advancement: AdvancementProfile = this.plugin.getAdvancementManager().getAdvancement(
            event.entity.type.toString(), AdvancementTypes.Mobs
        ) ?: return

        if (team.hasAdvancement(advancement)) {
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
        this.plugin.getMessageUtils().dispatchMessage(advancement.getCompleteMessage())
    }
}