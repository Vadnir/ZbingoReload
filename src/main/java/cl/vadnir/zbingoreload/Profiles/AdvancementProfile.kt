package cl.vadnir.zbingoreload.Profiles

import cl.vadnir.zbingoreload.Enums.AdvancementTypes
import com.fren_gor.ultimateAdvancementAPI.advancement.BaseAdvancement
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType
import org.bukkit.Material

class AdvancementProfile(
    private val type: AdvancementTypes,
    private val condition: String,
    private val completeMessage: String,
    private val itemDisplayName: String,
    private val unicodeComplete: String,
    private val unicodeNotComplete: String,
    private val title: String,
    private val description: String,
    private val cord: List<Float> = arrayListOf(),
    private val points: Int) {

    private val itemDisplay: Material = Material.getMaterial(this.itemDisplayName)
        ?: throw Exception("Invalid Material Name")

    init {
         if(cord.size != 2) {
             throw Exception("Invalid Cord Format")
         }
    }

    public override fun toString(): String {
        return "Advancement(name=${this.getName()})"
    }

    public fun getName(): String {
        return "${this.type}_${condition}".lowercase()
    }

    public fun getType(): AdvancementTypes {
        return this.type
    }

    public fun getCondition(): String {
        return this.condition
    }

    public fun getCompleteMessage(): String {
        return this.completeMessage
    }

    public fun getUnicodeComplete(): String {
        return this.unicodeComplete
    }

    public fun getUnicodeNotComplete(): String {
        return this.unicodeNotComplete
    }

    public fun getTitle(): String {
        return this.title
    }

    public fun getDescription(): String {
        return this.description
    }

    public fun getCord(): List<Float> {
        return this.cord
    }

    public fun getPoints(): Int {
        return this.points
    }

    public fun getItemDisplay(): Material{
        return this.itemDisplay
    }

    public fun getBaseAdvancement(rootAdvancement: RootAdvancement): BaseAdvancement {
        val advancementDisplay: AdvancementDisplay = AdvancementDisplay(
            this.itemDisplay, this.title, AdvancementFrameType.TASK,
            false, false, cord[0], cord[1],
            description)

        return BaseAdvancement(this.getName(), advancementDisplay, rootAdvancement)
    }
}