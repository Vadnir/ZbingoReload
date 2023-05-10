package cl.vadnir.zbingoreload.Storage

import cl.vadnir.zbingoreload.Profiles.PlayerProfile
import de.leonhard.storage.Yaml

class PlayerStorage(private val basePath: String) {

    private var playersDataFile: Yaml = Yaml("playerData", this.basePath)

    private fun hasPlayerData(playerName: String): Boolean {
        this.playersDataFile.getOrSetDefault("players.${playerName}.new", true)
        return !this.playersDataFile.getBoolean("players.${playerName}.new")
    }

    public fun getPlayerData(playerName: String): PlayerProfile? {
        if(!this.hasPlayerData(playerName)){
            return null
        }
        return PlayerProfile(
            this.playersDataFile.getString("players.${playerName}.name"),
            this.playersDataFile.getString("players.${playerName}.teamId")
        )
    }

    public fun createPlayerData(player: PlayerProfile) {
        this.playersDataFile.set("players.${player.getName()}.new", true)
        this.playersDataFile.set("players.${player.getName()}.name", player.getName())
        this.playersDataFile.set("players.${player.getName()}.team_name", player.getTeamId())
    }

    public fun savePlayerData(player: PlayerProfile) {
        this.playersDataFile.set("players.${player.getName()}.new", false)
        this.playersDataFile.set("players.${player.getName()}.name", player.getName())
        this.playersDataFile.set("players.${player.getName()}.team_name", player.getTeamId())
    }
}