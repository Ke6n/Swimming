package service

import kotlin.test.*

/**
 * Test cases for the method pass() in class [PlayerActionService]
 */
class PassTest {
    private val playerName1 = "Ava"
    private val playerName2 = "Zoe"
    private val playerName3 = "Ben"
    private val playerName4 = "Yvo"

    private val rootService = RootService()
    private val testRefreshable = TestRefreshable()

    /**
     * Helper method for testPass()
     * Simulate players keep passing until the end of the game
     *
     * Maximum times of pass: (num of cards in draw pile) / 3 * (num of players) + 1
     *
     * When all players have passed one by one:
     *      If there are enough cards in the draw pile
     *          - Should put cards of middle deck on discard pile
     *          - Should put top 3 cards of drawPile in middleDeck
     *          - passCounter should set to 0
     *      If there are not enough cards in the draw pile
     *          - The game should end
     * When not
     *      - passCounter++
     *      - should be the next player's turn
     */
    private fun passTestHelper(playerNames: List<String>) {
        rootService.gameService.startGame(playerNames)
        rootService.addRefreshable(testRefreshable)
        val game = rootService.currentGame!!

        var oldMiddleCards = game.middleDeck.cards
        var oldTopThreeCardsToDraw = game.drawPile.cardsOnPile.filterIndexed { index, _ -> index < 3 }

        assertFalse { testRefreshable.refreshOnPassCalled }
        assertFalse { testRefreshable.refreshOnEndTurnCalledWithFalse }
        // There are enough cards in the draw pile
        repeat(game.drawPile.cardsOnPile.size / 3 * game.players.size) {
            rootService.playerActionService.pass()
            // passCounter should repeat from 0 to (size of players - 1)
            assert((it + 1) % game.players.size == game.passCounter)
            // All players have passed one by one
            if (game.passCounter == game.players.size) {
                // Should put cards of middle deck on discard pile
                assertEquals(oldMiddleCards,
                    game.discardPile.cardsOnPile.filterIndexed { index, _ -> index < 3 })
                // Should put top 3 cards of drawPile in middleDeck
                assertEquals(oldTopThreeCardsToDraw, game.middleDeck.cards)
                // passCounter should set to 0
                assert(0 == game.passCounter)
                assertTrue { testRefreshable.refreshOnPassCalled }
            }
            // Someone have not passed yet
            else {
                oldMiddleCards = game.middleDeck.cards
                oldTopThreeCardsToDraw = game.drawPile.cardsOnPile.filterIndexed { index, _ -> index < 3 }
                // The next player's turn
                assert((it + 1) % game.players.size == game.activePlayerIndex)
                assertTrue { testRefreshable.refreshOnEndTurnCalledWithFalse }
            }
            testRefreshable.reset()
        }

        assertFalse { testRefreshable.refreshOnEndTurnCalledWithTrue }
        repeat(game.players.size){
            rootService.playerActionService.pass()
        }
        // There are not enough cards in the draw pile
        assertTrue { testRefreshable.refreshOnEndTurnCalledWithTrue }
        testRefreshable.reset()
    }

    /**
     * Simulate players keep passing until the end of the game with 2-4 players
     */
    @Test
    fun testPass(){
        // Test pass() if the game starts with 2 players
        passTestHelper(listOf(playerName1, playerName2))
        // Test pass() if the game starts with 3 players
        passTestHelper(listOf(playerName1, playerName2, playerName3))
        // Test pass() if the game starts with 4 players
        passTestHelper(listOf(playerName1, playerName2, playerName3, playerName4))
    }
}