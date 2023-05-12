package cl.vadnir.zbingoreload.Commands

import cl.vadnir.zbingoreload.Profiles.PlayerProfile
import cl.vadnir.zbingoreload.Profiles.TeamProfile
import cl.vadnir.zbingoreload.ZBingoReload
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.IntegerArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.CommandExecutor
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class TeamCommands(private val plugin: ZBingoReload) {
    private fun teamRemoveCommand(): CommandAPICommand {
        return CommandAPICommand("remove")
            .withSubcommand(
                CommandAPICommand("advancement")
                    .withArguments(getAdvancementArgument(this.plugin))
                    .withArguments(getTeamArgument(this.plugin))
                    .executes(
                        CommandExecutor { sender: CommandSender?, args: Array<Any> ->
                            run {
                                this.plugin
                                    .getTeamManager()
                                    .getTeam(args[1].toString())!!
                                    .removeAdvancement(
                                        this.plugin
                                            .getAdvancementManager()
                                            .getAdvancement(args[0].toString())
                                            ?: throw Exception("no existe advancement")
                                    )
                            }
                        }
                    )
            )
            .withSubcommand(
                CommandAPICommand("player")
                    .withArguments(getPlayerArgument(this.plugin))
                    .withArguments(getTeamArgument(this.plugin))
                    .executes(
                        CommandExecutor { sender: CommandSender?, args: Array<Any> ->
                            run {
                                this.plugin
                                    .getTeamManager()
                                    .getTeam(args[1].toString())!!
                                    .removePlayer(
                                        this.plugin.getPlayerManager().getPlayer(args[0].toString())
                                            ?: throw Exception("no existe jugador")
                                    )
                                    this.plugin.getPlayerManager().getPlayer(args[0].toString())!!.setTeamId("")
                            }
                        }
                    )
            )
    }

    private fun teamAddCommand(): CommandAPICommand {
        return CommandAPICommand("add")
            .withSubcommand(
                CommandAPICommand("advancement")
                    .withArguments(getAdvancementArgument(this.plugin))
                    .withArguments(getTeamArgument(this.plugin))
                    .executes(
                        CommandExecutor { sender: CommandSender?, args: Array<Any> ->
                            run {
                                this.plugin
                                    .getTeamManager()
                                    .getTeam(args[1].toString())!!
                                    .addAdvancement(
                                        this.plugin
                                            .getAdvancementManager()
                                            .getAdvancement(args[0].toString())
                                            ?: throw Exception("no existe advancement")
                                    )
                            }
                        }
                    )
            )
            .withSubcommand(
                CommandAPICommand("player")
                    .withArguments(getPlayerArgument(this.plugin))
                    .withArguments(getTeamArgument(this.plugin))
                    .executes(
                        CommandExecutor { sender: CommandSender?, args: Array<Any> ->
                            run {
                                this.plugin
                                    .getTeamManager()
                                    .getTeam(args[1].toString())!!
                                    .addPlayer(
                                        this.plugin.getPlayerManager().getPlayer(args[0].toString())
                                            ?: throw Exception("no existe jugador")
                                    )
                                this.plugin
                                    .getMessageUtils()
                                    .playerAddTeam(
                                        this.plugin
                                            .getPlayerManager()
                                            .getPlayer(args[0].toString())!!,
                                        this.plugin.getTeamManager().getTeam(args[1].toString())!!
                                    )
                            }
                        }
                    )
            )
    }

    private fun teamGetCommand(): CommandAPICommand {
        return CommandAPICommand("get")
            .withSubcommand(
                CommandAPICommand("points")
                    .withArguments(getTeamArgument(this.plugin))
                    .executes(
                        CommandExecutor { sender: CommandSender?, args: Array<Any> ->
                            run {
                                sender!!.sendMessage(
                                    this.plugin
                                        .getTeamManager()
                                        .getTeam(args[0].toString())!!
                                        .getPoints(
                                            this.plugin.getAdvancementManager().getAllAdvancements()
                                        )
                                        .toString()
                                )
                            }
                        }
                    )
            )
            .withSubcommand(
                CommandAPICommand("advancements")
                    .withArguments(getTeamArgument(this.plugin))
                    .executes(
                        CommandExecutor { sender: CommandSender?, args: Array<Any> ->
                            run {
                                sender!!.sendMessage(
                                    this.plugin
                                        .getTeamManager()
                                        .getTeam(args[0].toString())!!
                                        .getAdvancementList()
                                        .toString()
                                )
                            }
                        }
                    )
            )
            .withSubcommand(
                CommandAPICommand("players")
                    .withArguments(getTeamArgument(this.plugin))
                    .executes(
                        CommandExecutor { sender: CommandSender?, args: Array<Any> ->
                            run {
                                sender!!.sendMessage(
                                    this.plugin
                                        .getTeamManager()
                                        .getTeam(args[0].toString())!!
                                        .getPlayerList()
                                        .toString()
                                )
                            }
                        }
                    )
            )
    }

    private fun teamChunkCommand(): CommandAPICommand {
        return CommandAPICommand("chunk")
            .withArguments(IntegerArgument("Number"))
            .executes(
                CommandExecutor { sender: CommandSender?, args: Array<Any> ->
                    run {
                        val players = this.plugin.getPlayerManager().getAllPlayers()
                        val playerTeams: List<List<PlayerProfile>> =
                            players.chunked(args[0].toString().toInt()).toList()
                        val teamProfiles = arrayListOf<TeamProfile>()
                        var contador = 0
                        for (teams: List<PlayerProfile> in playerTeams) {
                            val temp: TeamProfile = TeamProfile(
                                    contador.toString(),
                                    contador.toString(),
                                    playerList = arrayListOf(*teams.toTypedArray())
                            )
                            contador++
                            for (player in temp.getPlayerList()){
                                player.setTeamId(temp.getTeamId())
                            }
                            teamProfiles.add(temp)
                        }
                        this.plugin.getTeamManager().setTeams(teamProfiles)
                    }
                }
            )
    }

    private fun teamCreateCommand(): CommandAPICommand {
        return CommandAPICommand("create")
            .withArguments(StringArgument("teamId"))
            .withArguments(StringArgument("teamDisplay"))
            .executes(
                CommandExecutor { _: CommandSender?, args: Array<Any> ->
                    this.plugin.getTeamManager().addTeam(args[0].toString(), args[1].toString())
                }
            )
    }

    private fun teamDeleteCommand(): CommandAPICommand{
        return CommandAPICommand("delete")
            .withArguments(getAdvancementArgument(this.plugin))
            .executes(
                CommandExecutor { _: CommandSender?, args: Array<Any> -> run {
                    val team = this.plugin.getTeamManager().getTeam(args[0].toString())?: throw Exception("some error")

                    val players = team.getPlayerList()

                    this.plugin.getTeamManager().removeTeam(args[0].toString())
                    for ( player in players) {
                        player.setTeamId("")
                    }
                } }
            )
    }

    public fun getTeamCommands(): CommandAPICommand {
        return CommandAPICommand("team")
            .withSubcommand(this.teamDeleteCommand())
            .withSubcommand(this.teamRemoveCommand())
            .withSubcommand(this.teamAddCommand())
            .withSubcommand(this.teamCreateCommand())
            .withSubcommand(this.teamChunkCommand())
            .withSubcommand(this.teamGetCommand())
    }
}
