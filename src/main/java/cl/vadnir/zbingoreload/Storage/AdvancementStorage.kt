package cl.vadnir.zbingoreload.Storage

import cl.vadnir.zbingoreload.Profiles.AdvancementProfile
import cl.vadnir.zbingoreload.Enums.AdvancementTypes
import de.leonhard.storage.Yaml

class AdvancementStorage(private val path: String) {

    private val advancementFile: Yaml = Yaml("objectives.yml", this.path)

    init {
        this.setDefault()
    }

    private fun setDefault() {

        for(i: String in arrayListOf<String>("mobs", "biomes", "items", "advancements")){

            val condition: String = when(i){
                "mobs" -> "PIG"
                "biomes" -> "DESERT"
                "items" -> "DIAMOND"
                "advancements" -> "Some_One"
                else -> continue
            }

            this.advancementFile.getOrSetDefault("objectives.${i}", "random_id")
            this.advancementFile.getOrSetDefault("objectives.${i}.random_id.condition", condition)
            this.advancementFile.getOrSetDefault("objectives.${i}.random_id.item_display", "")
            this.advancementFile.getOrSetDefault("objectives.${i}.random_id.description", "")
            this.advancementFile.getOrSetDefault("objectives.${i}.random_id.message_on_complete", "")
            this.advancementFile.getOrSetDefault("objectives.${i}.random_id.unicode_not_complete", "")
            this.advancementFile.getOrSetDefault("objectives.${i}.random_id.unicode_complete", "")
            this.advancementFile.getOrSetDefault("objectives.${i}.random_id.title", "")
            this.advancementFile.getOrSetDefault("objectives.${i}.random_id.description", "")
            this.advancementFile.getOrSetDefault("objectives.${i}.random_id.x", 1.0f)
            this.advancementFile.getOrSetDefault("objectives.${i}.random_id.y", 1.0f)
            this.advancementFile.getOrSetDefault("objectives.${i}.random_id.points", 1)

        }
    }

    public fun getObjectives(): ArrayList<AdvancementProfile> {
        val objectives: MutableMap<String, MutableMap<String, MutableMap<String, String>>> = this.advancementFile
            .getMapParameterized<String, MutableMap<String, MutableMap<String, String>>>("objectives")

        val advancementsProfiles: ArrayList<AdvancementProfile> = ArrayList()

        for (cat in objectives.keys) {//mobs, items...etc

            val type = when (cat) {
                "mobs" -> AdvancementTypes.Mobs
                "items" -> AdvancementTypes.Items
                "advancements" -> AdvancementTypes.Advancements
                "biomes" -> AdvancementTypes.Biomes
                else -> continue
            }

            for (id in objectives[cat]!!.keys) {
                val objective = objectives[cat]!![id]

                advancementsProfiles.add(
                    AdvancementProfile(
                        type, objective?.get("condition")!!, objective["message_on_complete"]!!,
                        objective["item_display"]!!, objective["unicode_not_complete"]!!,
                        objective["unicode_complete"]!!,
                        objective["title"]!!, objective["description"]!!, listOf(
                            objective["x"]!!.toFloat(),
                            objective["y"]!!.toFloat()
                        ), objective["points"]!!.toInt()
                    )
                )
            }
        }

        return advancementsProfiles
    }
}