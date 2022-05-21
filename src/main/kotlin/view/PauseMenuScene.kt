package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * This menu scene is shown if the "Pause" button is clicked in the [SwimmingGameScene]
 */
class PauseMenuScene : MenuScene() {
    private val menuLabel = Label(
        width = 600, height = 780,
        posX = 660, posY = 150,
        visual = ColorVisual(40, 0, 63)
    )
    private val headlineLabel = Label(
        width = 600, height = 100,
        posX = 660, posY = 200,
        text = "- PAUSE -",
        font = Font(size = 80, color = Color.WHITE)
    )

    val resumeButton = Button(
        width = 500, height = 100,
        posX = 710, posY = 400,
        text = "Resume",
        font = Font(size = 50, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(90, 50, 103)
    }

    val restartButton = Button(
        width = 500, height = 100,
        posX = 710, posY = 550,
        text = "Restart",
        font = Font(size = 50, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(90, 50, 103)
    }

    val quitButton = Button(
        width = 500, height = 100,
        posX = 710, posY = 700,
        text = "Quit",
        font = Font(size = 50, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(90, 50, 103)
    }

    init {
        opacity = 0.0
        addComponents(
            menuLabel,
            headlineLabel,
            resumeButton,
            restartButton,
            quitButton
        )
    }
}