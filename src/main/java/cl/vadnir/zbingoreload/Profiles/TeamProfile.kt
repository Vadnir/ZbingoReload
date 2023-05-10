package cl.vadnir.zbingoreload.Profiles

class TeamProfile(
    private val teamId: String,
    private var teamNameDisplay: String = "",
    private var advancementList: ArrayList<AdvancementProfile> = arrayListOf(),
    private var playerList: ArrayList<PlayerProfile> = arrayListOf()) {

    public override fun toString(): String {
        return "Team(teamId=${teamId}, teamNameDisplay=${teamNameDisplay})"
    }

    public fun getTeamId(): String {
        return this.teamId
    }

    public fun getTeamNameDisplay(): String {
        return this.teamNameDisplay
    }

    public fun getAdvancementList(): List<AdvancementProfile> {
        return this.advancementList.toList()
    }

    public fun getPlayerList(): List<PlayerProfile> {
        return this.playerList.toList()
    }

    public fun getPoints(): Int {
        return this.advancementList.sumOf { it.getPoints() }
    }

    public fun setTeamNameDisplay(newTeamNameDisplay: String) {
        this.teamNameDisplay = newTeamNameDisplay
    }

    public fun setAdvancementList(newAdvancementList: List<AdvancementProfile>) {
        this.advancementList = arrayListOf(*newAdvancementList.toTypedArray())
    }

    public fun setPlayerList(newPlayerList: List<PlayerProfile>) {
        this.playerList = arrayListOf(*newPlayerList.toTypedArray())
    }

    private fun hasPlayer(player: PlayerProfile): Boolean {
        return this.playerList.any { it.getName() == player.getName() }
    }

    public fun hasAdvancement(advancement: AdvancementProfile): Boolean {
        return this.advancementList.any {it.getName() == advancement.getName() }
    }

    public fun addPlayer(player: PlayerProfile) {
        if (this.hasPlayer(player)) {
            throw Exception("This player is on this team")
        }

        if (player.getTeamId()!!.isEmpty()){
            throw Exception("This player has a team")
        }

        this.playerList.add(player)
    }

    public fun addAdvancement(advancement: AdvancementProfile) {
        if(this.hasAdvancement(advancement)){
            throw Exception("this team has the same advancement")
        }

        this.advancementList.add(advancement)
    }

    public fun removePlayer(player: PlayerProfile) {
        if(!this.hasPlayer(player)){
            throw Exception("this team not has this player")
        }

        this.playerList.remove(player)
    }

    public fun removeAdvancement(advancement: AdvancementProfile) {
        if(!this.hasAdvancement(advancement)){
            throw Exception("this team not has the same advancement")
        }

        this.advancementList.remove(advancement)
    }

}