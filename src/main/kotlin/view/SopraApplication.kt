package view

import service.RootService
import tools.aqua.bgw.core.BoardGameApplication
import kotlin.system.exitProcess

/**
 * Implementation of the BGW [BoardGameApplication] for the card game "Swimming"
 */
class SopraApplication : BoardGameApplication("SoPra Game"), Refreshable {

    // Central service
    private val rootService = RootService()

    // Scenes

    // This is where the actual game takes place
    private val gameScene = SwimmingGameScene(rootService)

    // This menu scene is shown after application start and if the "Quit" button
    // is clicked in the PauseMenuScene or in the ScoreBoardMenuScene
    private val startMenuScene = StartMenuScene(rootService).apply {
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
    private val playerConfigMenuScene = PlayerConfigMenuScene(rootService).apply {
        startButton.onMouseClicked = {
            promptLabel1.isVisible = false
            promptLabel2.isVisible = false
            promptLabel3.isVisible = false
            promptLabel4.isVisible = false
            when {
                player1TextField.text.length !in 1..10 ->
                    promptLabel1.isVisible = true
                player2TextField.text.length !in 1..10 ->
                    promptLabel2.isVisible = true
                playerCount in 3..4 && player3TextField.text.length !in 1..10 ->
                    promptLabel3.isVisible = true
                playerCount == 4 && player4TextField.text.length !in 1..10 ->
                    promptLabel4.isVisible = true
                else -> {
                    val playerNames = ArrayList<String>(playerCount)
                    playerNames.add(player1TextField.text)
                    playerNames.add(player2TextField.text)
                    if(playerCount in 3..4) playerNames.add(player3TextField.text)
                    if (playerCount == 4) playerNames.add(player4TextField.text)
                    rootService.gameService.startGame(playerNames)
                }
            }
        }
    }

    init {

        // all scenes and the application itself need too
        // react to changes done in the service layer
        rootService.addRefreshables(
            this,
            startMenuScene
        )

        this.showGameScene(gameScene)
        this.showMenuScene(startMenuScene, 0)

    }

    override fun refreshOnStartGame() {
        this.hideMenuScene()
    }

    //override fun refreshOnStopGame() {
    //this.showMenuScene(gameFinishedMenuScene)
    //}
}

