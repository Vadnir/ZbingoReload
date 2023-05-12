package cl.vadnir.zbingoreload.Utils

class ColorUtils {

    private var colorsUsed: ArrayList<String> = arrayListOf()

    private val chars: List<Char> = ('0'..'9').toList() + ('a'..'f').toList()

    fun getColor(): String {
        val color = this.genColor()
        if (!this.colorsUsed.contains(color)){
            this.colorsUsed.add(color)
            return color
        }
        return getColor()
    }

    private fun genColor(): String {
        var color = "#"
        for (i in 0..5){
            color += this.chars.random()
        }
        return color
    }
}