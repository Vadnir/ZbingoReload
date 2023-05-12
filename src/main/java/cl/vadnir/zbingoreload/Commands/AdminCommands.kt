package cl.vadnir.zbingoreload.Commands

import cl.vadnir.zbingoreload.Profiles.PlayerProfile
import cl.vadnir.zbingoreload.Profiles.TeamProfile
import cl.vadnir.zbingoreload.ZBingoReload
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.*
import dev.jorel.commandapi.executors.CommandExecutor
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


class AdminCommands(private val plugin: ZBingoReload) {

    init {
        this.registerCommand()
    }

    private fun getPlayerArgument(): Argument<String>? {
        return StringArgument("players").replaceSuggestions(
            ArgumentSuggestions.strings {
                this.plugin.getPlayerManager().getAllPlayers(). map {
                    it.getName() } .toTypedArray()
            }
        )
    }

    private fun getTeamArgument(): Argument<String>? {
        return StringArgument("teams").replaceSuggestions(
            ArgumentSuggestions.strings {
                this.plugin.getTeamManager().getAllTeam(). map {
                    it.getTeamId() } .toTypedArray()
            }
        )
    }

    private fun getAdvancementArgument(): Argument<String>? {
        return StringArgument("advancements").replaceSuggestions(
            ArgumentSuggestions.strings {
                this.plugin.getAdvancementManager().getAllAdvancements(). map {
                    it.getName() } .toTypedArray()
            }
        )
    }

    private fun listCommands(): CommandAPICommand {
        return CommandAPICommand("list")
            .withArguments(MultiLiteralArgument("players", "teams", "advancements"))
            .executes(CommandExecutor
            { sender: CommandSender?, args: Array<Any> ->
                when (args[0]) {
                    "players" -> sender!!.sendMessage(
                        this.plugin.getPlayerManager().getAllPlayers().toString()
                    )

                    "teams" -> sender!!.sendMessage(
                        this.plugin.getTeamManager().getAllTeam().toString()
                    )

                    "advancements" -> sender!!.sendMessage(
                        this.plugin.getAdvancementManager().getAllAdvancements().toString()
                    )
                }
            }
            )
    }

    private fun playerCommands(): CommandAPICommand {
        return CommandAPICommand("player")
            .withSubcommand(CommandAPICommand("create")
                .withArguments(
                    this.getPlayerArgument()
                )
                .executes(CommandExecutor{
                    sender: CommandSender?, args: Array<Any> -> run {
                        this.plugin.getPlayerManager().createPlayer(args[0].toString())
                }
                })
            )
            .withSubcommand(CommandAPICommand("team")
                .withArguments(
                    this.getPlayerArgument()
                )
                .executes(CommandExecutor { sender: CommandSender?, args: Array<Any> ->
                    sender!!.sendMessage(
                        this.plugin.getPlayerManager().getPlayer(args[0].toString())!!.getTeamId()) }
                )
            )
    }

    private fun adminCommand(): CommandAPICommand {
        return CommandAPICommand("admin")
            .withSubcommand(CommandAPICommand("givealladvancements")
                .withArguments(this.getTeamArgument())
                .executes(CommandExecutor {sender: CommandSender?, args: Array<out Any>? -> run {
                    val advancements = this.plugin.getAdvancementManager().getAllAdvancements()
                    val team = this.plugin.getTeamManager().getTeam(args?.get(0)?.toString() ?: throw Exception(""))
                    team!!.setAdvancementList(
                        advancements.map { it.getName() }.toList()
                    )
                    this.plugin.getMessageUtils().bingoMessage(team)
                } })
            )
    }


    private fun registerCommand() {

        CommandAPICommand("bingo")
            .withSubcommand(TeamCommands(this.plugin).getTeamCommands())
            .withSubcommand(this.playerCommands())
            .withSubcommand(this.listCommands())
            .withSubcommand(this.adminCommand())
            .register()
    }
}


