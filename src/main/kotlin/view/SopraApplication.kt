package view

import service.RootService
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.core.BoardGameApplication
import kotlin.system.exitProcess

/**
 * Implementation of the BGW [BoardGameApplication] for the card game "Swimming"
 */
class SopraApplication : BoardGameApplication("SoPra Game"), Refreshable {

    // Central service
    private val rootService = RootService()

    // Scenes

    // This menu scene is shown after application start and if the "Quit" button
    // is clicked in the PauseMenuScene or in the ScoreBoardMenuScene
    private val startMenuScene = StartMenuScene().apply {
        exitButton.onMouseClicked = {
            exit()
        }
        playButton.onMouseClicked = {
            playerConfigMenuScene.playerCount = playerCount
            when (playerCount) {
                2 -> {
                    playerConfigMenuScene.player3Label.isVisible = false
                    playerConfigMenuScene.player3TextField.isVisible = false
                    playerConfigMenuScene.player4Label.isVisible = false
                    playerConfigMenuScene.player4TextField.isVisible = false
                }
                3 -> {
                    playerConfigMenuScene.player3Label.isVisible = true
                    playerConfigMenuScene.player3TextField.isVisible = true
                    playerConfigMenuScene.player4Label.isVisible = false
                    playerConfigMenuScene.player4TextField.isVisible = false
                }
                4 -> {
                    playerConfigMenuScene.player3Label.isVisible = true
                    playerConfigMenuScene.player3TextField.isVisible = true
                    playerConfigMenuScene.player4Label.isVisible = true
                    playerConfigMenuScene.player4TextField.isVisible = true
                }
                else -> exitProcess(-1)
            }
            showMenuScene(playerConfigMenuScene)
        }
    }

    // This menu scene is shown after the "Play" button in startMenuScene clicked
    private val playerConfigMenuScene = PlayerConfigMenuScene().apply {
        startButton.onMouseClicked = {
            promptLabel1.isVisible = false
            promptLabel2.isVisible = false
            promptLabel3.isVisible = false
            promptLabel4.isVisible = false
            when {
                (player1TextField.text.isBlank() || player1TextField.text.length > 10) ->
                    promptLabel1.isVisible = true
                player2TextField.text.isBlank() || player2TextField.text.length > 10 ->
                    promptLabel2.isVisible = true
                playerCount in 3..4 && (player3TextField.text.isBlank() || player3TextField.text.length > 10) ->
                    promptLabel3.isVisible = true
                playerCount == 4 && (player4TextField.text.isBlank() || player4TextField.text.length > 10) ->
                    promptLabel4.isVisible = true
                else -> {
                    val playerNames = ArrayList<String>(playerCount)
                    playerNames.add(player1TextField.text)
                    playerNames.add(player2TextField.text)
                    if (playerCount in 3..4) playerNames.add(player3TextField.text)
                    if (playerCount == 4) playerNames.add(player4TextField.text)
                    gameScene.playerNames = playerNames
                    rootService.gameService.startGame(playerNames)
                    gameScene.components.forEach { it.isVisible = true }
                    gameScene.remainingTurnsLabel.isVisible = false
                    showGameScene(gameScene)
                }
            }
        }
    }

    // This is where the actual game takes place
    private val gameScene = SwimmingGameScene(rootService).apply {
        passButton.onMouseClicked = {
            if (this.handDeck.first().currentSide == CardView.CardSide.FRONT)
                rootService.playerActionService.pass()
        }
        knockButton.onMouseClicked = {
            if (this.handDeck.first().currentSide == CardView.CardSide.FRONT)
                rootService.playerActionService.knock()
        }
        changeOneCardButton.onMouseClicked = {
            if (playerCardIndex in 0..2 && middleCardIndex in 0..2
                && this.handDeck.first().currentSide == CardView.CardSide.FRONT
            ) {
                rootService.playerActionService.changeOneCard(playerCardIndex, middleCardIndex)
            }
        }
        changeAllCardsButton.onMouseClicked = {
            if (this.handDeck.first().currentSide == CardView.CardSide.FRONT)
                rootService.playerActionService.changeAllCards()
        }
        pauseButton.onMouseClicked = { showMenuScene(pauseMenuScene) }
        helpButton.onMouseClicked = { showMenuScene(helpMenuScene) }
    }

    // This menu scene is shown at the end of the game
    private val scoreBoardMenuScene = ScoreBoardMenuScene(rootService).apply {
        restartButton.onMouseClicked = { restart() }
        quitButton.onMouseClicked = { quit() }
    }

    // This menu scene is shown after the "Pause" button in gameScene clicked
    private val pauseMenuScene = PauseMenuScene().apply {
        resumeButton.onMouseClicked = { hideMenuScene() }
        restartButton.onMouseClicked = { restart() }
        quitButton.onMouseClicked = { quit() }
    }

    // This menu scene is shown after the "Help" button in gameScene clicked
    private val helpMenuScene = HelpMenuScene().apply { closeButton.onMouseClicked = { hideMenuScene() } }

    init {
        // all scenes and the application itself need too
        // react to changes done in the service layer
        rootService.addRefreshables(
            this,
            gameScene,
            scoreBoardMenuScene
        )
        this.showGameScene(gameScene)
        this.showMenuScene(startMenuScene, 0)
    }

    /**
     * Game restarts with the same players after the "Restart" button clicked
     */
    private fun restart() {
        val game = rootService.currentGame!!
        val names = List(game.players.size) { index -> game.players[index].name }
        rootService.gameService.startGame(names)
        gameScene.remainingTurnsLabel.isVisible = false
        showGameScene(gameScene)
    }

    /**
     * Game restarts with the same players after the "Restart" button clicked
     */
    private fun quit(){
        rootService.gameService.stopGame()
        playerConfigMenuScene.randomNames()
    }

    /**
     * perform refreshes that are necessary after a new game started
     */
    override fun refreshOnStartGame() {
        this.hideMenuScene()
    }

    /**
     * perform refreshes that are necessary after the turn is over
     *
     * @param hasGameEnded is true if the game is over, false otherwise
     */
    override fun refreshOnEndTurn(hasGameEnded: Boolean) {
        if (hasGameEnded) showMenuScene(scoreBoardMenuScene)
    }

    /**
     * perform refreshes that are necessary after the game stopped
     */
    override fun refreshOnStopGame() {
        gameScene.components.forEach { it.isVisible = false }
        showMenuScene(startMenuScene)
    }
}

