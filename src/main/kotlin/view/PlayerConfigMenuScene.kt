package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * This menu scene is shown after the "Play" button in startMenuScene clicked
 */
class PlayerConfigMenuScene(private val rootService: RootService) : MenuScene(), Refreshable {
    var playerCount: Int = 2

    private val headlineLabel = Label(
        width = 1000, height = 180,
        posX = 460, posY = 100,
        text = "- Please enter your name -",
        font = Font(size = 80, color = Color.WHITE)
    )

    private val player1Label = Label(
        width = 170, height = 30,
        posX = 660, posY = 320,
        text = "Player 1: ",
        font = Font(size = 40, color = Color.WHITE)
    )

    val player1TextField = TextField(
        width = 260,
        posX = 860, posY = 320,
        font = Font(size = 30, color = Color.BLACK)
    )

    val promptLabel1 = Label(
        width = 300, height = 30,
        posX = 1126, posY = 330,
        text = "* Enter 1-10 characters",
        font = Font(size = 26, color = Color.LIGHT_GRAY)
    )

    private val player2Label = Label(
        width = 170, height = 30,
        posX = 660, posY = 400,
        text = "Player 2: ",
        font = Font(size = 40, color = Color.WHITE)
    )

    val player2TextField = TextField(
        width = 260,
        posX = 860, posY = 400,
        font = Font(size = 30, color = Color.BLACK)
    )

    val promptLabel2 = Label(
        width = 300, height = 30,
        posX = 1126, posY = 410,
        text = "* Enter 1-10 characters",
        font = Font(size = 26, color = Color.LIGHT_GRAY)
    )

    val player3Label = Label(
        width = 170, height = 30,
        posX = 660, posY = 480,
        text = "Player 3: ",
        font = Font(size = 40, color = Color.WHITE)
    )

    val player3TextField = TextField(
        width = 260,
        posX = 860, posY = 480,
        font = Font(size = 30, color = Color.BLACK)
    )

    val promptLabel3 = Label(
        width = 300, height = 30,
        posX = 1126, posY = 490,
        text = "* Enter 1-10 characters",
        font = Font(size = 26, color = Color.LIGHT_GRAY)
    )

    val player4Label = Label(
        width = 170, height = 30,
        posX = 660, posY = 560,
        text = "Player 4: ",
        font = Font(size = 40, color = Color.WHITE)
    )

    val player4TextField = TextField(
        width = 260,
        posX = 860, posY = 560,
        font = Font(size = 30, color = Color.BLACK)
    )

    val promptLabel4 = Label(
        width = 300, height = 30,
        posX = 1126, posY = 570,
        text = "* Enter 1-10 characters",
        font = Font(size = 26, color = Color.LIGHT_GRAY)
    )

    val startButton = Button(
        width = 600, height = 140,
        posX = 660, posY = 800,
        text = "Start Game",
        font = Font(size = 60, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(40, 0, 63)
    }

    init {
        opacity = 0.0
        player3Label.isVisible = false
        player4Label.isVisible = false
        player3TextField.isVisible = false
        player4TextField.isVisible = false
        promptLabel1.isVisible = false
        promptLabel2.isVisible = false
        promptLabel3.isVisible = false
        promptLabel4.isVisible = false
        addComponents(
            headlineLabel,
            player1Label,
            promptLabel1,
            player1TextField,
            player2Label,
            player2TextField,
            promptLabel2,
            player3Label,
            player3TextField,
            promptLabel3,
            player4Label,
            player4TextField,
            promptLabel4,
            startButton
        )

    }
}