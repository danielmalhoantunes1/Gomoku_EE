package pt.isel.pdm.gomoku_ee

import android.app.Application
import pt.isel.pdm.gomoku_ee.Service.GomokuFakeService
import pt.isel.pdm.gomoku_ee.Service.GomokuService

interface GomokuDependenciesContainer {
    val gomokuService : GomokuService
}

class GomokuApplication: Application(), GomokuDependenciesContainer {
    override val gomokuService: GomokuService = GomokuFakeService()
}
