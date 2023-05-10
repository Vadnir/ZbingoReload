package cl.vadnir.zbingoreload.Managers

import cl.vadnir.zbingoreload.Profiles.PlayerProfile
import org.bukkit.entity.Player

class PlayerManager(private val playerList: HashMap<String, PlayerProfile> = hashMapOf()) {

    private fun hasPlayer(player: Player): Boolean{
        return this.playerList.containsKey(player.name)
    }

    private fun hasPlayer(player: PlayerProfile): Boolean{
        return this.playerList.containsKey(player.getName())
    }

    private fun hasPlayer(playerName: String): Boolean{

        return this.playerList.containsKey(playerName)
    }

    fun getAllPlayers(): List<PlayerProfile> {
        return this.playerList.values.toList()
    }

    fun getPlayer(playerName: String): PlayerProfile? {
        if(!hasPlayer(playerName)){
            throw Exception("This player $playerName not has a PlayerProfile")
        }
        return this.playerList[playerName]
    }

    fun getPlayer(player: Player): PlayerProfile? {
        if(!hasPlayer(player)){
            throw Exception("This player $player not has a PlayerProfile")
        }
        return this.playerList[player.name]
    }

    fun createPlayer(player: Player) {
        if(hasPlayer(player)){
            throw Exception("This player has a PlayerProfile")
        }
        this.playerList[player.name] = PlayerProfile(player.name)
    }

    fun createPlayer(playerName: String) {
        if(hasPlayer(playerName)){
            throw Exception("This player has a PlayerProfile")
        }
        this.playerList[playerName] = PlayerProfile(playerName)
    }

    fun addPlayer(player: PlayerProfile){
        if(hasPlayer(player)){
            throw Exception("This player has a PlayerProfile")
        }
        this.playerList[player.getName()] = player
    }

    fun removePlayer(player: PlayerProfile) {
        if(!hasPlayer(player)){
            throw Exception("This player not has a PlayerProfile")
        }
        this.playerList.remove(player.getName())
    }

    fun removePlayer(player: Player) {
        if(!hasPlayer(player)){
            throw Exception("This player not has a PlayerProfile")
        }
        this.playerList.remove(player.name)
    }

    fun removePlayer(playerName: String) {
        if(!hasPlayer(playerName)){
            throw Exception("This player not has a PlayerProfile")
        }
        this.playerList.remove(playerName)
    }
}