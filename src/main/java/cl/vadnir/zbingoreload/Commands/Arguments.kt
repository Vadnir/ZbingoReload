package cl.vadnir.zbingoreload.Commands

import cl.vadnir.zbingoreload.ZBingoReload
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.StringArgument

fun getPlayerArgument(plugin: ZBingoReload): Argument<String>? {
    return StringArgument("players").replaceSuggestions(
        ArgumentSuggestions.strings {
            plugin.getPlayerManager().getAllPlayers(). map {
                it.getName() } .toTypedArray()
        }
    )
}

fun getTeamArgument(plugin: ZBingoReload): Argument<String>? {
    return StringArgument("teams").replaceSuggestions(
        ArgumentSuggestions.strings {
            plugin.getTeamManager().getAllTeam(). map {
                it.getTeamId() } .toTypedArray()
        }
    )
}

fun getAdvancementArgument(plugin: ZBingoReload): Argument<String>? {
    return StringArgument("advancements").replaceSuggestions(
        ArgumentSuggestions.strings {
            plugin.getAdvancementManager().getAllAdvancements(). map {
                it.getName() } .toTypedArray()
        }
    )
}