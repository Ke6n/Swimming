package service

import kotlin.test.*

/**
 * Test cases for the method changeAllCards() in class [PlayerActionService]
 */
class ChangeAllCardsTest {
    private val playerName1 = "Ava"
    private val playerName2 = "Zoe"

    private val rootService = RootService()
    private val testRefreshable = TestRefreshable()

    /**
     *  Test if all cards swapped
     *  - passCounter set to 0
     *  - all cards swapped
     */
    @Test
    fun testChangeAllCards(){
        rootService.gameService.startGame(listOf(playerName1, playerName2))
        rootService.addRefreshable(testRefreshable)
        val game = rootService.currentGame!!
        // Player1's turn
        assert(0 == game.activePlayerIndex)
        // player1 passed
        rootService.playerActionService.pass()
        assert(1 == game.passCounter)
        // Player2's turn
        assert(1 == game.activePlayerIndex)
        val oldHandCards = game.players[game.activePlayerIndex].handDeck.cards
        val oldMiddleCards = game.middleDeck.cards
        testRefreshable.reset()
        assertFalse { testRefreshable.refreshOnChangeAllCardsCalled }
        assertFalse { testRefreshable.refreshOnEndTurnCalledWithFalse }
        // player2 changes all cards
        rootService.playerActionService.changeAllCards()
        // passCounter set to 0
        assert(0 == game.activePlayerIndex)
        // All cards swapped
        assertEquals(oldHandCards, game.middleDeck.cards)
        assertEquals(oldMiddleCards, game.players[1].handDeck.cards)
        assertTrue { testRefreshable.refreshOnChangeAllCardsCalled }
        assertTrue { testRefreshable.refreshOnEndTurnCalledWithFalse }
        testRefreshable.reset()
    }
}