package view

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * This menu scene is shown if the "Help" button is clicked in the [SwimmingGameScene]
 *
 * This menu shows the Info of "How to play"
 */
class HelpMenuScene : MenuScene() {
    private val helpLabel = Label(
        width = 1200, height = 880,
        posX = 360, posY = 100,
        visual = ColorVisual(40, 0, 63)
    )

    private val headlineLabel = Label(
        width = 1200, height = 100,
        posX = 360, posY = 150,
        text = "- How To Play -",
        font = Font(size = 80, color = Color.WHITE)
    )

    private val helpTextLabel = Label(
        width = 1160, height = 600,
        posX = 380, posY = 250,
        font = Font(size = 25, color = Color.WHITE),
        text = "1. Click on the hand deck to flip to the front.\n\n" +
                "2. The current player has 4 optional actions:\n" +
                "\t- Change one card: Choose a card from the hand and a card from the middle and click \"change\n" +
                "one card\" button.\n" +
                "\t- Change all cards: Swap all hand cards of the current player with all middle cards with click\n" +
                " \"change all cards\" button.\n" +
                "\t- pass: Click \"pass\" button, if a player does not want to exchange cards. " +
                "When all players have\npassed, the three cards from the middle are placed on the discard pile" +
                " and three new ones from the\ndraw pile are placed in the middle.\n" +
                "\t- knock: If a player thinks they have a good hand, they can knock. Just as with passing," +
                " he\ndoesn't exchange any cards. After a player has knocked, all other players have exactly one more turn.\n" +
                "After that the game is over.\n\n" +
                "3.The aim of the game is to have the most points in your hand at the end." +
                " The highest total of one of\nthe four suits is counted, with {jack, queen, king} each counting as " +
                "10 points and the ace as 11 points.\nThis means that a maximum of 31 points (ace plus two cards " +
                "with 10 points each) is possible. Three\ncards of the same number or face value are worth 30.5 points."
    )

    val closeButton = Button(
        width = 300, height = 80,
        posX = 810, posY = 880,
        text = "close",
        font = Font(size = 40, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(90, 50, 103)
    }

    init {
        opacity = 0.0
        addComponents(
            helpLabel,
            headlineLabel,
            helpTextLabel,
            closeButton
        )
    }
}