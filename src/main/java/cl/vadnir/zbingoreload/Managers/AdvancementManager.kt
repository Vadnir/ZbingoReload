package cl.vadnir.zbingoreload.Managers

import cl.vadnir.zbingoreload.Enums.AdvancementTypes
import cl.vadnir.zbingoreload.Profiles.AdvancementProfile

class AdvancementManager(private var advancementList: ArrayList<AdvancementProfile> = arrayListOf()) {

    private fun hasAdvancement(advancementName: String): Boolean {
        return this.advancementList.any {it.getName() == advancementName}
    }

    private fun hasAdvancement(advancement: AdvancementProfile): Boolean {
        return this.advancementList.any {it.getName() == advancement.getName()}
    }

    fun getAllAdvancements(): List<AdvancementProfile> {
        return this.advancementList.toList()
    }

    fun getAdvancementByType(advancementsType: AdvancementTypes): List<AdvancementProfile> {
        return this.advancementList.filter { it.getType() == advancementsType }.toList()
    }

    fun getAdvancement(condition: String, advancementsType: AdvancementTypes): AdvancementProfile? {
        return this.advancementList.firstOrNull {
            it.getType() == advancementsType && it.getCondition() == condition
        }
    }

    fun getAdvancement(advancementName: String): AdvancementProfile? {
        return this.advancementList.firstOrNull {
            it.getName() == advancementName
        }
    }

    fun setAdvancements(newAdvancements: List<AdvancementProfile>){
        this.advancementList = arrayListOf(*newAdvancements.toTypedArray())
    }

    fun addAdvancement(advancement: AdvancementProfile) {
        if(this.hasAdvancement(advancement)){
            throw Exception("this advancement Exist")
        }
        this.advancementList.add(advancement)
    }
}