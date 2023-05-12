package cl.vadnir.zbingoreload.External

import cl.vadnir.zbingoreload.ZBingoReload
import cl.vadnir.zbingoreload.Profiles.AdvancementProfile
import com.fren_gor.ultimateAdvancementAPI.AdvancementTab
import com.fren_gor.ultimateAdvancementAPI.UltimateAdvancementAPI
import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay
import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementFrameType
import org.bukkit.Material

class AdvancementApi(private val plugin: ZBingoReload) {

    private var ultimateAdvancementAPI: UltimateAdvancementAPI = UltimateAdvancementAPI.getInstance(this.plugin)

    private var advancementTab: AdvancementTab = this.ultimateAdvancementAPI.createAdvancementTab("bingo")


    public fun getApi(): UltimateAdvancementAPI {
        return this.ultimateAdvancementAPI
    }

    private fun createRootAdvancement(): RootAdvancement {
        return RootAdvancement(this.advancementTab, "root",
            AdvancementDisplay(
                Material.MAP, "Bingo", AdvancementFrameType.CHALLENGE,
                false, false, 0f, 3f,
                "Donde", "Todo Comienza"),
            "textures/block/gray_concrete.png")
    }

    fun registerAdvancements(advancements: List<AdvancementProfile>) {

        val root = this.createRootAdvancement()

        this.advancementTab.registerAdvancements(
            root, *advancements.map { it.getBaseAdvancement(root) }.toTypedArray()
        )
        this.advancementTab.automaticallyGrantRootAdvancement()
    }
}