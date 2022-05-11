package service

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Test cases for the method knock() in class [PlayerActionService]
 */
class KnockTest {
    private val playerName1 = "Ava"
    private val playerName2 = "Zoe"
    private val playerName3 = "Ben"
    private val playerName4 = "Yvo"

    private val rootService = RootService()
    private val testRefreshable = TestRefreshable()

    /**
     * Test if game ends after someone's knock
     *
     * - Player1 passes, passCounter set to 1,
     *                   hasKnocked  is false,
     *                   movesRemaining is -1
     * - Player2 knocks, passCounter set to 0,
     *                   hasKnocked set to true,
     *                   movesRemaining set to size of players - 1.
     * - Player1 passes, passCounter set to 1,
     *                   hasKnocked  is true,
     *                   movesRemaining = 0 -> game ends
     */
    @Test
    fun testGameEndsAfterKnock() {
        rootService.gameService.startGame(listOf(playerName1, playerName2))
        rootService.addRefreshable(testRefreshable)
        val game = rootService.currentGame!!
        // Player1's turn
        assert(0 == game.activePlayerIndex)
        // Before knocking, player1 passed
        rootService.playerActionService.pass()
        assert(1 == game.passCounter)
        assertFalse { game.hasKnocked }
        assert(-1 == game.movesRemaining)
        assertFalse { testRefreshable.refreshOnKnockCalled }
        // Player2's turn
        assert(1 == game.activePlayerIndex)
        rootService.playerActionService.knock()
        // After knocking
        assertTrue { game.hasKnocked }
        assert(0 == game.passCounter)
        assert(game.players.size - 1 == game.movesRemaining)
        assertTrue { testRefreshable.refreshOnKnockCalled }
        // Player1 has another chance
        assert(0 == game.activePlayerIndex)
        rootService.playerActionService.pass()
        assert(1 == game.passCounter)
        assertTrue { game.hasKnocked }
        assert(0 == game.movesRemaining)
        assertTrue { testRefreshable.refreshOnKnockCalled }
        // Game ends
        assertTrue { testRefreshable.refreshOnEndTurnCalledWithTrue }
        testRefreshable.reset()
    }

    /**
     * Test knocks after the first knock
     *
     * - Player1 knocks, hasKnocked set to true,
     *                   movesRemaining set to size of players - 1.
     * - Player2 changes all cards,
     *                   cards exchanged
     *                   hasKnocked  is true,
     *                   movesRemaining--
     * - Player3 knocks, hasKnocked is true,
     *                   movesRemaining--
     * - Player4 knocks, hasKnocked is true,
     *                   movesRemaining = 0 -> game ends
     */
    @Test
    fun testKnocksAfterKnock() {
        rootService.gameService.startGame(listOf(playerName1, playerName2, playerName3, playerName4))
        rootService.addRefreshable(testRefreshable)
        val game = rootService.currentGame!!
        // Player1's turn
        assert(0 == game.activePlayerIndex)
        assertFalse { game.hasKnocked }
        assert(- 1 == game.movesRemaining)
        assertFalse { testRefreshable.refreshOnKnockCalled }
        rootService.playerActionService.knock()
        assertTrue { game.hasKnocked }
        assert(game.players.size - 1 == game.movesRemaining)
        assertTrue { testRefreshable.refreshOnKnockCalled }
        // Player2' turn
        assert(1 == game.activePlayerIndex)
        val oldMiddle = game.middleDeck.cards
        val oldHandOfPlayer2 = game.players[game.activePlayerIndex].handDeck.cards
        // Player2 changes all cards
        assertFalse { testRefreshable.refreshOnChangeAllCardsCalled }
        rootService.playerActionService.changeAllCards()
        assertTrue { testRefreshable.refreshOnChangeAllCardsCalled }
        // cards exchanged
        assertEquals(oldMiddle, game.players[game.activePlayerIndex - 1].handDeck.cards)
        assertEquals(oldHandOfPlayer2, game.middleDeck.cards)
        assertTrue { game.hasKnocked }
        assert(2 == game.movesRemaining)
        // Player3' turn
        assert(2 == game.activePlayerIndex)
        testRefreshable.reset()
        rootService.playerActionService.knock()
        assertTrue { game.hasKnocked }
        assert(1 == game.movesRemaining)
        assertTrue { testRefreshable.refreshOnKnockCalled }
        // Player4' turn
        assert(3 == game.activePlayerIndex)
        testRefreshable.reset()
        rootService.playerActionService.knock()
        assertTrue { game.hasKnocked }
        assert(0 == game.movesRemaining)
        assertTrue { testRefreshable.refreshOnKnockCalled }
        // Game ends
        assertTrue { testRefreshable.refreshOnEndTurnCalledWithTrue }
        testRefreshable.reset()
    }
}