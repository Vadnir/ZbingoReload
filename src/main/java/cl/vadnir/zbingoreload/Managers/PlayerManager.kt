package cl.vadnir.zbingoreload.Managers

import cl.vadnir.zbingoreload.Profiles.PlayerProfile
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class PlayerManager(private val playerList: ArrayList<PlayerProfile> = ArrayList()) {

    private fun hasPlayer(player: Player): Boolean{

        Bukkit.getConsoleSender().sendMessage(
            "${ this.playerList }  $player"
        )

        for ( player1 in this.playerList ) {
            if (player.name == player1.getName()) {
                return true
            }
        }
        return false
    }

    private fun hasPlayer(player: PlayerProfile): Boolean{
        Bukkit.getConsoleSender().sendMessage(
            "${ this.playerList.toString() }  $player"
        )

        for ( player1 in this.playerList ) {
            if (player1.getName() == player.getName()) {
                return true
            }
        }
        return false
    }

    private fun hasPlayer(playerName: String): Boolean{

        Bukkit.getConsoleSender().sendMessage(
            "${ this.playerList.toString() }  $playerName"
        )

        for ( player in this.playerList ) {
            if (playerName == player.getName()) {
                return true
            }
        }
        return false
    }

    fun getAllPlayers(): List<PlayerProfile> {
        return this.playerList
    }

    fun getPlayer(playerName: String): PlayerProfile? {
        if(!hasPlayer(playerName)){
            throw Exception("This player $playerName not has a PlayerProfile")
        }
        return this.playerList.firstOrNull { it.getName() == playerName }
    }

    fun getPlayer(player: Player): PlayerProfile? {
        if(!hasPlayer(player)){
            throw Exception("This player $player not has a PlayerProfile")
        }
        return this.playerList.firstOrNull { it.getName() == player.name }
    }

    fun createPlayer(player: Player) {
        if(hasPlayer(player)){
            throw Exception("This player has a PlayerProfile")
        }
        this.playerList.add(PlayerProfile(player.name, ""))
    }

    fun createPlayer(playerName: String) {
        if(hasPlayer(playerName)){
            throw Exception("This player has a PlayerProfile")
        }
        this.playerList.add(PlayerProfile(playerName, ""))
    }

    fun addPlayer(player: PlayerProfile){
        if(hasPlayer(player)){
            throw Exception("This player has a PlayerProfile")
        }
        this.playerList.add(player)
    }

    fun removePlayer(player: PlayerProfile) {
        if(!hasPlayer(player)){
            throw Exception("This player not has a PlayerProfile")
        }
        this.playerList.remove(player)
    }

    fun removePlayer(player: Player) {
        if(!hasPlayer(player)){
            throw Exception("This player not has a PlayerProfile")
        }
        this.playerList.remove(this.getPlayer(player))
    }

    fun removePlayer(playerName: String) {
        if(!hasPlayer(playerName)){
            throw Exception("This player not has a PlayerProfile")
        }
        this.playerList.remove(this.getPlayer(playerName))
    }
}