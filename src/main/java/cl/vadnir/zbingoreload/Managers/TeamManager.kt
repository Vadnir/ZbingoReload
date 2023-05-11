package cl.vadnir.zbingoreload.Managers

import cl.vadnir.zbingoreload.Profiles.PlayerProfile
import cl.vadnir.zbingoreload.Profiles.TeamProfile

class TeamManager(private var teamList: HashMap<String, TeamProfile> = hashMapOf<String, TeamProfile>()) {

    private fun hasTeam(teamId: String): Boolean {
        return this.teamList.containsKey(teamId)
    }

    public fun setTeams(newTeams: List<TeamProfile>){
        newTeams.associateByTo(this.teamList) {it.getTeamId() }
    }

    public fun getAllTeam(): List<TeamProfile> {
        return this.teamList.values.toList()
    }

    public fun getTeam(teamId: String): TeamProfile? {
        if (!this.hasTeam(teamId)){
            throw Exception("this team not exist")
        }

        return this.teamList[teamId]
    }

    public fun getPlayerByTeamId(teamId: String): List<PlayerProfile>{
        if (!this.hasTeam(teamId)){
            throw Exception("this team not exist")
        }

        return this.teamList[teamId]!!.getPlayerList()
    }

    public fun addTeam(teamId: String, teamDisplayName: String) {
        if (this.hasTeam(teamId)){
            throw Exception("this team exist")
        }

        this.teamList[teamId] = TeamProfile(teamId, teamDisplayName)
    }

    public fun removeTeam(teamId: String) {
        if (!this.hasTeam(teamId)){
            throw Exception("this team not exist")
        }

        this.teamList.remove(teamId)
    }
}


