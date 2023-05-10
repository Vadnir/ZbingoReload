package cl.vadnir.zbingoreload.Commands

import cl.vadnir.zbingoreload.Profiles.PlayerProfile
import cl.vadnir.zbingoreload.Profiles.TeamProfile
import cl.vadnir.zbingoreload.ZBingoReload
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.*
import dev.jorel.commandapi.executors.CommandExecutor
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender


class AdminCommands(private val plugin: ZBingoReload) {

    init {
        this.registerCommand()
    }

    private fun registerCommand(){

        CommandAPICommand("bingo")
            .withSubcommand(CommandAPICommand("team")
                    .withSubcommand(CommandAPICommand("chunk")
                        .withArguments(IntegerArgument("number"))
                        .executes(CommandExecutor { sender: CommandSender?, args: Array<Any> ->
                            run {
                                val players = this.plugin.getPlayerManager().getAllPlayers()
                                val playerTeams: List<List<PlayerProfile>> =
                                    players.chunked(args[0].toString().toInt()).toList()
                                val teamProfiles = arrayListOf<TeamProfile>()
                                for (teams: List<PlayerProfile> in playerTeams)
                                    teamProfiles.add(
                                        TeamProfile(
                                            teams[0].toString(),
                                            teams[0].toString(),
                                            playerList = arrayListOf(*teams.toTypedArray())
                                        )
                                    )
                                this.plugin.getTeamManager().setTeams(
                                    teamProfiles
                                )
                            }
                        }
                        )
                    )
                    .withSubcommand(CommandAPICommand("list")
                        .withArguments(
                            StringArgument("teams").replaceSuggestions(
                                ArgumentSuggestions.strings {
                                    this.plugin.getTeamManager().getAllTeam().map { it.getTeamId() }
                                        .toTypedArray() }))
                        .executes(CommandExecutor {
                                sender: CommandSender?, args: Array<Any> -> sender!!.sendMessage(
                            this.plugin.getTeamManager().getTeam(args[0].toString())!!.getPlayerList().toString()) }
                        )
                    )
                    .withSubcommand(CommandAPICommand("create")
                            .withArguments(StringArgument("teamId"))
                            .withArguments(StringArgument("teamDisplay"))
                            .executes(CommandExecutor {
                                    _: CommandSender?, args: Array<Any> -> this.plugin.getTeamManager()
                                        .addTeam(args[0].toString(), args[1].toString()) }
                            )
                    )
                    .withSubcommand(CommandAPICommand("add")
                            .withArguments(EntitySelectorArgument.ManyPlayers("players"))
                            .withArguments(
                                StringArgument("teams").replaceSuggestions(
                                    ArgumentSuggestions.strings {
                                        this.plugin.getTeamManager().getAllTeam().map { it.getTeamId() }
                                            .toTypedArray()
                                    }
                                )
                            )
                            .executes(CommandExecutor { sender: CommandSender?, args: Array<Any> ->
                                run {

                                    sender!!.sendMessage(args.toString())

                                    this.plugin.getTeamManager().getTeam(args[1].toString())!!
                                        .addPlayer(
                                            this.plugin.getPlayerManager().getPlayer(args[0].toString())
                                                ?: throw Exception("no player")
                                        )

                                }
                            }
                            )
                    )
                    .withSubcommand(CommandAPICommand("remove")
                        .withArguments(EntitySelectorArgument.ManyPlayers("players"))
                        .withArguments(
                            StringArgument("teams").replaceSuggestions(
                                ArgumentSuggestions.strings {
                                    this.plugin.getTeamManager().getAllTeam().map { it.getTeamNameDisplay() }
                                        .toTypedArray()
                                }
                            )
                        )
                        .executes(CommandExecutor {
                                _: CommandSender?, args: Array<Any> -> this.plugin.getTeamManager().getTeam(args[1].toString())!!
                            .removePlayer(
                                this.plugin.getPlayerManager().getPlayer(args[0].toString())?: throw Exception("no player")
                            ) }
                        )
                    )
            )
            .withSubcommand(CommandAPICommand("player")
                .withSubcommand(CommandAPICommand("create")
                    .withArguments(EntitySelectorArgument.ManyPlayers("players"))
                    .executes(CommandExecutor { _: CommandSender?, args: Array<Any> -> this.plugin.getPlayerManager().createPlayer(args[0].toString()) }
                    )
                )
                .withSubcommand(CommandAPICommand("team")
                    .withArguments(EntitySelectorArgument.ManyPlayers("players"))
                    .executes(CommandExecutor { sender: CommandSender?, args: Array<Any> -> sender!!.sendMessage(
                        this.plugin.getPlayerManager().getPlayer(args[0].toString())!!.getTeamId()!!) })
                )
            )
            .withSubcommand(CommandAPICommand("list")
                .withArguments(MultiLiteralArgument("players", "teams", "advancements"))
                .executes(CommandExecutor { sender: CommandSender?, args: Array<Any> ->
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
                })
            )
        .register()
    }
}