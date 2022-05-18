package view

import service.RootService
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ImageVisual

/**
 * This is the main thing for the Swimming game. The scene shows the complete table at once.
 */
class SwimmingGameScene(private val rootService: RootService) : BoardGameScene(1920, 1080), Refreshable {



    init {
        background = ImageVisual("poker_table.jpg")
        addComponents()
    }

}