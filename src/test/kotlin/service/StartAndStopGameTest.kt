package service

import kotlin.test.*

/**
 * Test cases for the method startGame() and stopGame() in class [GameService]
 */
class StartAndStopGameTest {
    private val playerName1 = "Ava"
    private val playerName2 = "Zoe"
    private val playerName3 = "Ben"
    private val playerName4 = "Yvo"
    private val playerName5 = "Cal"
    private val playerName6 = ""

    private val testRefreshable = TestRefreshable()

    /**
     *  Test if the game doesn't start with blank player name
     */
    @Test
    fun testStartGameWithBlankName() {
        val rootService = RootService()
        val playerNames = listOf(playerName1, playerName6)
        assertFailsWith<IllegalStateException> {
            rootService.gameService.startGame(playerNames)
        }
    }

    /**
     *  Test if the game doesn't start with less than 2 players
     */
    @Test
    fun testStartGameWithOnePlayer() {
        val rootService = RootService()
        val playerNames = listOf(playerName1)
        assertFailsWith<IllegalStateException> {
            rootService.gameService.startGame(playerNames)
        }
    }

    /**
     *  Test if the game doesn't start with more than 4 players
     */
    @Test
    fun testStartGameWithFivePlayers() {
        val rootService = RootService()
        val playerNames = listOf(playerName1, playerName2, playerName3, playerName4, playerName5)
        assertFailsWith<IllegalStateException> {
            rootService.gameService.startGame(playerNames)
        }
    }

    /**
     *  Helper method for testStartGame()
     *  - There should be 3 cards in the middle
     *  - Every player should have a name
     *  - Every player should have his hand deck with 3 cards
     *  - There should be (32 - hand cards of players - middle cards) cards in the draw pile
     */
    private fun startGameTestHelper(playerNames: List<String>) {
        // Game has not started yet.
        val rootService = RootService()
        rootService.addRefreshable(testRefreshable)
        assertNull(rootService.currentGame)
        assertFalse { testRefreshable.refreshOnStartGameCalled }
        // Start the game
        rootService.gameService.startGame(playerNames)
        assertNotNull(rootService.currentGame)
        // There should be 3 cards in the middle
        assert(3 == rootService.currentGame!!.middleDeck.cards.size)
        // Every player should have a name
        playerNames.forEachIndexed { index, s ->
            assertEquals(s, rootService.currentGame!!.players[index].name)
        }
        // Every player should have his hand deck with 3 cards
        rootService.currentGame!!.players.forEach {
            assert(3 == it.handDeck.cards.size)
        }
        // Cards in the draw pile: 32 - hand cards of players - middle cards
        assert(
            32 - 3 * rootService.currentGame!!.players.size - 3
                    == rootService.currentGame!!.drawPile.cardsOnPile.size
        )

        assertTrue { testRefreshable.refreshOnStartGameCalled }
        testRefreshable.reset()
    }

    /**
     *  Test if the game starts with 2 - 4 players
     */
    @Test
    fun testStartGame() {
        // Test if the game starts with 2 players
        startGameTestHelper(listOf(playerName1, playerName2))
        // Test if the game starts with 3 players
        startGameTestHelper(listOf(playerName1, playerName2, playerName3))
        // Test if the game starts with 4 players
        startGameTestHelper(listOf(playerName1, playerName2, playerName3, playerName4))
    }

    /**
     *  Test if the draw deck shuffled at the start of the game
     *  (There is a very small probability that the test will fail,
     *  because the order of the cards may also be the same after multiple shuffles)
     */
    @Test
    fun testStartGameShuffled() {
        var isShuffled = false
        val rootService = RootService()
        val playerNames = listOf(playerName1, playerName2)
        rootService.gameService.startGame(playerNames)
        val drawCards1 = rootService.currentGame!!.drawPile.cardsOnPile
        repeat(5) {
            rootService.gameService.startGame(playerNames)
            if (drawCards1 != rootService.currentGame!!.drawPile.cardsOnPile) isShuffled = true
        }
        assertTrue { isShuffled }
    }

    /**
     *  Test if there are duplicate cards in the draw pile
     */
    @Test
    fun testDuplicateCards() {
        var isDuplicate = false
        val rootService = RootService()
        repeat(5) {
            rootService.gameService.startGame(listOf(playerName1, playerName2))
            val drawCards = rootService.currentGame!!.drawPile.cardsOnPile
            if (drawCards.toSet().size != drawCards.size) isDuplicate = true
        }
        assertFalse { isDuplicate }
    }

    /**
     *  Test if the game can be stopped
     */
    @Test
    fun testStopGame() {
        // Game has not started yet.
        val rootService = RootService()
        rootService.addRefreshable(testRefreshable)
        assertNull(rootService.currentGame)
        val playerNames = listOf(playerName1, playerName2)
        assertFalse { testRefreshable.refreshOnStopGameCalled }
        // Start the game
        rootService.gameService.startGame(playerNames)
        assertNotNull(rootService.currentGame)
        // The game should stop now
        rootService.gameService.stopGame()
        assertNull(rootService.currentGame)

        assertTrue { testRefreshable.refreshOnStopGameCalled }
        testRefreshable.reset()
    }
}