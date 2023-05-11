package cl.vadnir.zbingoreload.Managers

import cl.vadnir.zbingoreload.ZBingoReload
import cl.vadnir.zbingoreload.Storage.PlayerStorage
import cl.vadnir.zbingoreload.Profiles.AdvancementProfile
import cl.vadnir.zbingoreload.Profiles.PlayerProfile
import cl.vadnir.zbingoreload.Profiles.TeamProfile
import cl.vadnir.zbingoreload.Storage.AdvancementStorage
import cl.vadnir.zbingoreload.Storage.TeamStorage

class StorageManager(private val plugin: ZBingoReload) {

    private var playerStorage: PlayerStorage = PlayerStorage(this.plugin.dataFolder.path.toString())
    private var teamStorage: TeamStorage = TeamStorage(this.plugin.dataFolder.path.toString())
    private var advancementStorage: AdvancementStorage = AdvancementStorage(this.plugin.dataFolder.path.toString())

    public fun getPlayerData(playerName: String): PlayerProfile?{
        return playerStorage.getPlayerData(playerName)
    }

    public fun getTeamData(teamId: String): TeamProfile? {
        return this.teamStorage.getTeamProfile(teamId)
    }

    public fun getAllTeamData(): List<TeamProfile>? {
        return this.teamStorage.getAllTeams()
    }

    public fun getAdvancements(): List<AdvancementProfile> {
        return this.advancementStorage.getObjectives()
    }

    public fun saveTeams(teams: List<TeamProfile>){
        for ( team in teams ) {
            this.teamStorage.saveTeamData(team)
        }
    }

    public fun savePlayers(players: List<PlayerProfile>){
        for (player in players) {
            this.playerStorage.savePlayerData(player)
        }
    }

}