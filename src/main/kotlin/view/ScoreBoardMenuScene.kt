package view

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * This menu scene is shown if the game is over
 *
 * This menu shows Scores of players and the winner
 */
class ScoreBoardMenuScene(private val rootService: RootService) : MenuScene(), Refreshable {
    private val scoreBoardLabel = Label(
        width = 1200, height = 780,
        posX = 360, posY = 150,
        visual = ColorVisual(40, 0, 63)
    )

    private val headlineLabel = Label(
        width = 1200, height = 100,
        posX = 360, posY = 200,
        text = "- Score Board -",
        font = Font(size = 80, color = Color.WHITE)
    )

    private val scoreLabel = Label(
        width = 1200, height = 400,
        posX = 360, posY = 280,
        font = Font(size = 35, color = Color.WHITE)
    )

    private val winnerLabel = Label(
        width = 1200, height = 100,
        posX = 360, posY = 650,
        font = Font(size = 60, color = Color.ORANGE, fontWeight = Font.FontWeight.BOLD)
    )

    val restartButton = Button(
        width = 350, height = 100,
        posX = 410, posY = 780,
        text = "Restart",
        font = Font(size = 60, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(90, 50, 103)
    }

    val quitButton = Button(
        width = 350, height = 100,
        posX = 1160, posY = 780,
        text = "Quit",
        font = Font(size = 60, color = Color.WHITE)
    ).apply {
        visual = ColorVisual(90, 50, 103)
    }

    init {
        opacity = 0.0
        addComponents(
            scoreBoardLabel,
            headlineLabel,
            scoreLabel,
            winnerLabel,
            restartButton,
            quitButton
        )
    }

    /**
     * perform refreshes that are necessary after the turn is over
     *
     * @param hasGameEnded is true if the game is over, false otherwise
     */
    override fun refreshOnEndTurn(hasGameEnded: Boolean) {
        if (hasGameEnded) {
            val game = rootService.currentGame!!
            var scores = ""

            game.players.forEachIndexed { index, player ->
                player.score = rootService.gameService.calculatePlayerScore(player)
                scores += "Player ${index + 1}: ${player.name} (${player.score} Points)\n\n"
            }
            val ranking = game.players.sortedByDescending { it.score }
            this.scoreLabel.text = scores
            if (ranking[0].score == ranking[1].score) this.winnerLabel.text = "Draw!"
            else this.winnerLabel.text = "Winner: ${ranking[0].name} (P${game.players.indexOf(ranking[0]) + 1})"
        }
    }
}