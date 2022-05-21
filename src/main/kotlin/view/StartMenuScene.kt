package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * This menu scene is shown after application start and if the "Quit" button
 * is clicked in the PauseMenuScene or in the ScoreBoardMenuScene
 */
class StartMenuScene : MenuScene(){
    var playerCount: Int = 2

    private val helloLabel = Label(
        width = 1200,
        height = 300,
        posX = 360,
        posY = 50,
        text = "Swimming",
        font = Font(size = 160, Color.LIGHT_GRAY, fontStyle = Font.FontStyle.ITALIC, fontWeight = Font.FontWeight.BOLD),
    )
    private val helloLabelShadow = Label(
        width = 1200,
        height = 300,
        posX = 370,
        posY = 60,
        text = "Swimming",
        font = Font(size = 160, Color.DARK_GRAY, fontStyle = Font.FontStyle.ITALIC, fontWeight = Font.FontWeight.BOLD),
    )

    val exitButton = Button(
        width = 600, height = 140,
        posX = 660, posY = 800,
        text = "Exit",
        font = Font(size = 80, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(40, 0, 63)
    }

    val playButton = Button(
        width = 600, height = 180,
        posX = 660, posY = 400,
        text = "Play",
        font = Font(size = 80, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(40, 0, 63)
    }

    private val twoPlayersButton = Button(
        width = 200, height = 80,
        posX = 660, posY = 580,
        text = "2",
        font = Font(size = 40, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(90, 50, 103)
        onMouseClicked = {
            visual = ColorVisual(90, 50, 103)
            resetColor(2)
            playerCount = 2
        }
    }

    private val threePlayersButton = Button(
        width = 200, height = 80,
        posX = 860, posY = 580,
        text = "3",
        font = Font(size = 40, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(55, 15, 78)
        onMouseClicked = {
            visual = ColorVisual(90, 50, 103)
            resetColor(3)
            playerCount = 3
        }
    }

    private val fourPlayersButton = Button(
        width = 200, height = 80,
        posX = 1060, posY = 580,
        text = "4",
        font = Font(size = 40, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(55, 15, 78)
        onMouseClicked = {
            visual = ColorVisual(90, 50, 103)
            resetColor(4)
            playerCount = 4
        }
    }

    init {
        opacity = 0.0
        addComponents(
            helloLabelShadow,
            helloLabel,
            exitButton,
            playButton,
            twoPlayersButton,
            threePlayersButton,
            fourPlayersButton
        )
    }

    /**
     * Reset the Color of the unselected player count buttons
     */
    private fun resetColor(selectedCount: Int) {
        require(selectedCount in 2..4){"selectedCount should in 2..4"}
        val colorVisual = ColorVisual(55, 15, 78)
        when (selectedCount) {
            2 -> {
                threePlayersButton.visual = colorVisual
                fourPlayersButton.visual = colorVisual
            }
            3 -> {
                twoPlayersButton.visual = colorVisual
                fourPlayersButton.visual = colorVisual
            }
            4 -> {
                twoPlayersButton.visual = colorVisual
                threePlayersButton.visual = colorVisual
            }
        }
    }
}