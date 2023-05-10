package cl.vadnir.zbingoreload.Utils

import cl.vadnir.zbingoreload.ZBingoReload
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.minimessage.MiniMessage

class MessageUtils(val plugin: ZBingoReload){

    private val bukkitAudiences: BukkitAudiences = BukkitAudiences.create(this.plugin)

    private val miniMessage: MiniMessage = MiniMessage.miniMessage()

    public fun dispatchMessage(message: String, name: String, team: Boolean){
        var finalMessage = message.replace("%name%", "name")
        if(team){
            this.bukkitAudiences.players().sendMessage(this.miniMessage.deserialize(message
                .replace("%prefix%", "[ZBingo] El Equipo ")))
        }else{
            this.bukkitAudiences.players().sendMessage(this.miniMessage.deserialize(message
                .replace("%prefix%", "[ZBingo] El Jugador ")))
        }
    }

    public fun dispatchMessage(message: String) {
        this.bukkitAudiences.players().sendMessage(this.miniMessage.deserialize(message))
    }

    public fun close(){
        this.bukkitAudiences.close()
    }
}