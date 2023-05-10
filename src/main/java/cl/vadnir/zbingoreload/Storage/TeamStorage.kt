package cl.vadnir.zbingoreload.Storage

import cl.vadnir.zbingoreload.Profiles.AdvancementProfile
import cl.vadnir.zbingoreload.Profiles.TeamProfile
import de.leonhard.storage.Yaml

class TeamStorage(private val basePath: String) {

    private var teamsDataFile: Yaml = Yaml("teamsData", this.basePath)

    public fun hasTeamData(teamName: String): Boolean{
        this.teamsDataFile.getOrSetDefault("teams.${teamName}.new", true)
        return !this.teamsDataFile.getBoolean("teams.${teamName}.new")
    }

    public fun getTeamProfile(teamId: String): TeamProfile? {
        if (!this.hasTeamData(teamId)) {
            return null
        }

        return TeamProfile(
            teamsDataFile.getString("teams.${teamId}.id"),
            teamsDataFile.getString("teams.${teamId}.displayName")
        )
    }

    public fun createTeamData(team: TeamProfile){
        this.teamsDataFile.set("teams.${team.getTeamId()}.new", false)
        this.teamsDataFile.set("teams.${team.getTeamId()}.id", team.getTeamId())
        this.teamsDataFile.set("teams.${team.getTeamId()}.displayName", team.getTeamNameDisplay())
        this.teamsDataFile.set("teams.${team.getTeamId()}.players", team.getPlayerList().map {
            it.getName() }.toList())
        this.teamsDataFile.set("teams.${team.getTeamId()}.advancements", team.getAdvancementList().map {
            it.getName() }.toList())
    }

    public fun saveTeamData(team: TeamProfile) {
        this.teamsDataFile.set("teams.${team.getTeamId()}.new", true)
        this.teamsDataFile.set("teams.${team.getTeamId()}.id", team.getTeamId())
        this.teamsDataFile.set("teams.${team.getTeamId()}.displayName", team.getTeamNameDisplay())
        this.teamsDataFile.set("teams.${team.getTeamId()}.players", team.getPlayerList().map {
            it.getName() }.toList())
        this.teamsDataFile.set("teams.${team.getTeamId()}.advancements", team.getAdvancementList().map {
            it.getName() }.toList())
    }

    public fun getAllTeams(advancementList: List<AdvancementProfile>): ArrayList<TeamProfile>? {
        val teams: ArrayList<TeamProfile> = ArrayList()

        for (team: String in this.teamsDataFile.getMapParameterized<String, Any>("teams").keys){
            val team = this.getTeamProfile(team)?:continue
            team.setAdvancementList(
                advancementList.filter {
                    this.getAdvancementNamesOfTeam(team.getTeamId()).contains(it.getName())
                }.toList()
            )

        }

        if(teams.isEmpty()) {
            return null
        }

        return teams
    }

    private fun getAdvancementNamesOfTeam(teamId: String): List<String>{
        return this.teamsDataFile.getStringList("teams.${teamId}.advancements")
    }

}