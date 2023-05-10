package cl.vadnir.zbingoreload

import cl.vadnir.zbingoreload.Commands.AdminCommands
import cl.vadnir.zbingoreload.Utils.MessageUtils
import cl.vadnir.zbingoreload.External.AdvancementApi
import cl.vadnir.zbingoreload.External.PlaceholderApi
import cl.vadnir.zbingoreload.Listeners.*
import cl.vadnir.zbingoreload.Managers.*
import me.clip.placeholderapi.expansion.PlaceholderExpansion

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class ZBingoReload : JavaPlugin() {

    private lateinit var teamManager: TeamManager

    private lateinit var playerManager: PlayerManager

    private lateinit var taskManager: TaskManager

    private lateinit var advancementManager: AdvancementManager

    private lateinit var storageManager: StorageManager

    private lateinit var messageUtils: MessageUtils

    private lateinit var placeholderExpansion: PlaceholderExpansion

    private lateinit var ultimateAdvancementAPI: AdvancementApi

    public var debug: Boolean = false

    override fun onEnable() {

        this.messageUtils =  MessageUtils(this)
        this.storageManager = StorageManager(this)
        this.advancementManager = AdvancementManager()
        this.playerManager = PlayerManager()
        this.teamManager = TeamManager()
        this.taskManager = TaskManager(this)

        this.registerCommands()
        this.registerListeners()

        this.advancementManager.setAdvancements(
            this.storageManager.getAdvancements()
        )

        this.teamManager.setTeams(
            this.storageManager.getAllTeamData(
                this.advancementManager.getAllAdvancements()
            )?: arrayListOf()
        )

        //apis
        this.ultimateAdvancementAPI = AdvancementApi(plugin = this)
        this.ultimateAdvancementAPI.registerAdvancements(this.advancementManager.getAllAdvancements())

        //placeholders
        this.placeholderExpansion = PlaceholderApi(this)
        this.placeholderExpansion.register()
    }

    override fun onDisable() {
        this.placeholderExpansion.unregister()
        this.storageManager.saveTeams(this.teamManager.getAllTeam())
        this.storageManager.savePlayers(this.playerManager.getAllPlayers())
        this.messageUtils.close()
    }

    private fun registerCommands(){
        val command = AdminCommands(this)
        //this.getCommand("bingo")?.setExecutor(AdminCommands(this))
    }

    private fun registerListeners(){
        Bukkit.getPluginManager().registerEvents(PlayerConnect(this), this)
        Bukkit.getPluginManager().registerEvents(PlayerKillMob(this), this)
        Bukkit.getPluginManager().registerEvents(PlayerCompleteAdvancement(this), this)
    }

    public fun getPlayerManager(): PlayerManager {
        return this.playerManager
    }

    public fun getAdvancementManager(): AdvancementManager {
        return this.advancementManager
    }

    public fun getTeamManager(): TeamManager {
        return this.teamManager
    }

    public fun getStorageManager(): StorageManager {
        return this.storageManager
    }

    public fun getMessageUtils(): MessageUtils {
        return this.messageUtils
    }

    public fun getUltimateAdvancementAPI(): AdvancementApi {
        return this.ultimateAdvancementAPI
    }
}