package cl.vadnir.zbingoreload.Storage

import de.leonhard.storage.Yaml

class MessageStorage(private val path: String) {

    private val messageFile: Yaml = Yaml("messages.yml", this.path)

    init {

    }

    private fun setDefaults(){
        this.messageFile.getOrSetDefault("messages.bingo_start", "")
        this.messageFile.getOrSetDefault("messages.bingo_end", "")
        this.messageFile.getOrSetDefault("messages.bingo_start", "")
        this.messageFile.getOrSetDefault("messages.bingo_start", "")
        this.messageFile.getOrSetDefault("messages.bingo_start", "")
        this.messageFile.getOrSetDefault("messages.bingo_start", "")
        this.messageFile.getOrSetDefault("messages.bingo_start", "")
        this.messageFile.getOrSetDefault("messages.bingo_start", "")
        this.messageFile.getOrSetDefault("messages.bingo_start", "")
        this.messageFile.getOrSetDefault("messages.bingo_start", "")

    }
}