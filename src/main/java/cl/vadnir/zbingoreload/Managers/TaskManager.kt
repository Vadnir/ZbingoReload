package cl.vadnir.zbingoreload.Managers

import cl.vadnir.zbingoreload.ZBingoReload
import cl.vadnir.zbingoreload.Task.ItemHandTask
import cl.vadnir.zbingoreload.Task.PlayerLocationTask
import cl.vadnir.zbingoreload.Task.SaveDataTask


class TaskManager(private val plugin: ZBingoReload) {

    init {
        PlayerLocationTask(this.plugin)
        ItemHandTask(this.plugin)
        SaveDataTask(this.plugin)
    }

}