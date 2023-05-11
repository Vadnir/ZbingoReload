package cl.vadnir.zbingoreload.Managers

import cl.vadnir.zbingoreload.Enums.AdvancementTypes
import cl.vadnir.zbingoreload.Profiles.AdvancementProfile

class AdvancementManager(private var advancementList: HashMap<String, AdvancementProfile> = hashMapOf<String ,AdvancementProfile>()) {

    private fun hasAdvancement(advancementName: String): Boolean {
        return this.advancementList.containsKey(advancementName)
    }

    private fun hasAdvancement(advancement: AdvancementProfile): Boolean {
        return this.advancementList.containsKey(advancement.getName())
    }

    fun getAllAdvancements(): List<AdvancementProfile> {
        return this.advancementList.values.toList()
    }

    fun getAdvancementByType(advancementsType: AdvancementTypes): List<AdvancementProfile> {
        return this.advancementList.values.filter { it.getType() == advancementsType }.toList()
    }

    fun getAdvancement(condition: String, advancementsType: AdvancementTypes): AdvancementProfile? {
        return this.advancementList.values.firstOrNull {
            it.getType() == advancementsType && it.getCondition() == condition
        }
    }

    fun getAdvancement(advancementName: String): AdvancementProfile? {
        if(!this.hasAdvancement(advancementName)){
            throw Exception("not exist this a advancement with this name")
        }
        return this.advancementList[advancementName]

    }

    fun setAdvancements(newAdvancements: List<AdvancementProfile>){
        newAdvancements.associateByTo(this.advancementList) { it.getName() }
    }

    fun addAdvancement(advancement: AdvancementProfile) {
        if(this.hasAdvancement(advancement)){
            throw Exception("this advancement Exist")
        }
        this.advancementList[advancement.getName()] = advancement
    }
}