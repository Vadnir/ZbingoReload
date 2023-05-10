package cl.vadnir.zbingoreload.Managers

import cl.vadnir.zbingoreload.Profiles.PlayerProfile
import cl.vadnir.zbingoreload.Profiles.TeamProfile

class TeamManager(private var teamList: ArrayList<TeamProfile> = ArrayList()) {

    private fun hasTeam(teamId: String): Boolean {
        return this.teamList.any { it.getTeamId() == teamId}
    }

    public fun setTeams(newTeams: List<TeamProfile>){
        this.teamList = arrayListOf(*newTeams.toTypedArray())
    }

    public fun getAllTeam(): List<TeamProfile> {
        return this.teamList
    }

    public fun getTeam(teamId: String): TeamProfile? {
        if (!this.hasTeam(teamId)){
            throw Exception("this team not exist")
        }

        return this.teamList.firstOrNull { it.getTeamId() == teamId }
    }

    public fun getPlayerByTeamId(teamId: String): List<PlayerProfile>{
        if (!this.hasTeam(teamId)){
            throw Exception("this team not exist")
        }

        return this.teamList.first { it.getTeamId() == teamId }.getPlayerList()
    }

    public fun addTeam(teamId: String, teamDisplayName: String) {
        if (this.hasTeam(teamId)){
            throw Exception("this team exist")
        }

        this.teamList.add(TeamProfile(teamId, teamDisplayName))
    }

    public fun removeTeam(teamId: String) {
        if (!this.hasTeam(teamId)){
            throw Exception("this team not exist")
        }

        this.teamList.remove(this.getTeam(teamId))
    }
}