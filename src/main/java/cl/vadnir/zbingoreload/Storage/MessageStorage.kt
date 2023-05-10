package cl.vadnir.zbingoreload.Storage

import de.leonhard.storage.Yaml

class MessageStorage(private val path: String) {

    private val advancementFile: Yaml = Yaml("messages.yml", this.path)
}