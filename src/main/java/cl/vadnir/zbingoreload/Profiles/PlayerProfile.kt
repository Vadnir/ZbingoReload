package cl.vadnir.zbingoreload.Profiles

class PlayerProfile(
    private val name: String,
    private var teamId: String = "") {

    override fun toString(): String {
        return "Player(name=$name, teamId=$teamId)"
    }

    public fun getName(): String {
        return this.name
    }

    public fun getTeamId(): String {
        return this.teamId
    }

    public fun setTeamId(newTeamId: String){
        this.teamId = newTeamId
    }
}