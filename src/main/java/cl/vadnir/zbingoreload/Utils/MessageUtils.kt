package cl.vadnir.zbingoreload.Utils

import cl.vadnir.zbingoreload.Profiles.AdvancementProfile
import cl.vadnir.zbingoreload.Profiles.PlayerProfile
import cl.vadnir.zbingoreload.Profiles.TeamProfile
import cl.vadnir.zbingoreload.ZBingoReload
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.TitlePart
import org.bukkit.Bukkit

class MessageUtils(val plugin: ZBingoReload) {

    private val bukkitAudiences: BukkitAudiences = BukkitAudiences.create(this.plugin)

    private val miniMessage: MiniMessage = MiniMessage.miniMessage()

    public fun playerAddTeam(playerProfile: PlayerProfile, team: TeamProfile) {
        this.dispatchPrivateMessage("<color:#98fb98>Te uniste al equipo</color> <color:#dcd0ff>${team.getTeamNameDisplay()}</color>",
            playerProfile
        )
        for (player in team.getPlayerList()) {
            this.dispatchPrivateMessage("<color:#98fb98>El Jugador</color> <color:#dcd0ff>${playerProfile.getName()}</color> <color:#98fb98>se unio al equipo </color><color:#dcd0ff>${team.getTeamNameDisplay()}</color>",
                player)
        }
    }

    public fun completeAdvancement(advancementProfile: AdvancementProfile, team: TeamProfile) {
        if (team.getPlayerList().isEmpty()) {
            return
        }

        var finalMessage = advancementProfile.getCompleteMessage()
        finalMessage = if (team.getPlayerList().size > 1) {
            finalMessage.replace("%name%", team.getTeamNameDisplay())
                .replace("%prefix%", "<color:#dcd0ff>El Equipo</color> ")
                .replace("%unicode%", advancementProfile.getUnicodeComplete())
        } else {
            finalMessage.replace("%name%", team.getPlayerList().first().getName())
                .replace("%prefix%", "<color:#dcd0ff>El Jugador</color> ")
                .replace("%unicode%", advancementProfile.getUnicodeComplete())
        }
        this.dispatchMessage(finalMessage)
    }

    public fun bingoMessage(team: TeamProfile) {
        var title = "<color:#eee8aa>%prefix% %name%</color>"
        var subtitle = "<color:#eee8aa>Completo el Bingo</color>"

        title =
            if (team.getPlayerList().size > 1) {
                title
                    .replace("%name%", team.getTeamNameDisplay())
                    .replace("%prefix%", "El Equipo")
            } else {
                title
                    .replace("%name%", team.getPlayerList().first().getName())
                    .replace("%prefix%", "El Jugador")
            }
        this.dispatchTitle(title)
        this.dispatchSubTitle(subtitle)
        this.dispatchMessage("$title $subtitle")
    }

    public fun dispatchPrivateMessage(message: String, player: PlayerProfile) {
        this.bukkitAudiences
            .player(Bukkit.getPlayer(player.getName()) ?: return)
            .sendMessage(this.miniMessage.deserialize(message))
    }

    private fun dispatchMessage(message: String) {
        this.bukkitAudiences.players().sendMessage(this.miniMessage.deserialize(message))
    }

    private fun dispatchTitle(message: String) {
        this.bukkitAudiences.players().sendTitlePart(
            TitlePart.TITLE, this.miniMessage.deserialize(message))
    }

    private fun dispatchSubTitle(message: String) {
        this.bukkitAudiences.players().sendTitlePart(
            TitlePart.SUBTITLE, this.miniMessage.deserialize(message))
    }

    public fun close() {
        this.bukkitAudiences.close()
    }
}
