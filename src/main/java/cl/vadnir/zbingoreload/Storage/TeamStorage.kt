package cl.vadnir.zbingoreload.Storage

import cl.vadnir.zbingoreload.Profiles.AdvancementProfile
import cl.vadnir.zbingoreload.Profiles.TeamProfile
import de.leonhard.storage.Yaml
import org.bukkit.Bukkit

class TeamStorage(private val basePath: String) {

    private var teamsDataFile: Yaml = Yaml("teamsData", this.basePath)

    private fun hasTeamData(teamId: String): Boolean{
        this.teamsDataFile.getOrSetDefault("teams.${teamId}.new", true)
        return !this.teamsDataFile.getBoolean("teams.${teamId}.new")
    }

    public fun getTeamProfile(teamId: String): TeamProfile? {
        if (!this.hasTeamData(teamId)) {
            return null
        }

        return TeamProfile(
            teamsDataFile.getString("teams.${teamId}.id"),
            teamsDataFile.getString("teams.${teamId}.displayName"),
            advancementList=arrayListOf(*teamsDataFile.getStringList("teams.${teamId}.advancements").toTypedArray())
        )
    }

    public fun createTeamData(team: TeamProfile){
        this.teamsDataFile.set("teams.${team.getTeamId()}.new", false)
        this.teamsDataFile.set("teams.${team.getTeamId()}.id", team.getTeamId())
        this.teamsDataFile.set("teams.${team.getTeamId()}.displayName", team.getTeamNameDisplay())
        this.teamsDataFile.set("teams.${team.getTeamId()}.players", team.getPlayerList().map {
            it.getName() }.toList())
        this.teamsDataFile.set("teams.${team.getTeamId()}.advancements", team.getAdvancementList().toList())
    }

    public fun saveTeamData(team: TeamProfile) {
        this.teamsDataFile.set("teams.${team.getTeamId()}.new", false)
        this.teamsDataFile.set("teams.${team.getTeamId()}.id", team.getTeamId())
        this.teamsDataFile.set("teams.${team.getTeamId()}.displayName", team.getTeamNameDisplay())
        this.teamsDataFile.set("teams.${team.getTeamId()}.players", team.getPlayerList().map {
            it.getName() }.toList())
        this.teamsDataFile.set("teams.${team.getTeamId()}.advancements", team.getAdvancementList().toList())
    }

    public fun getAllTeams(): ArrayList<TeamProfile>? {
        val teams: ArrayList<TeamProfile> = ArrayList()

        Bukkit.getConsoleSender().sendMessage(this.teamsDataFile.getMapParameterized<String, Any>("teams").keys.toString())
        for (team: String in this.teamsDataFile.getMapParameterized<String, Any>("teams").keys){
            val teamProfile = this.getTeamProfile(team)?:continue
            Bukkit.getConsoleSender().sendMessage(teamProfile.toString())
            teams.add(teamProfile)
        }

        if(teams.isEmpty()) {
            return null
        }

        return teams
    }

}