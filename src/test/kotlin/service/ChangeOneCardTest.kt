package service

import kotlin.test.*

/**
 * Test cases for the method changeOneCard() in class [PlayerActionService]
 */
class ChangeOneCardTest {
    private val playerName1 = "Ava"
    private val playerName2 = "Zoe"

    private val rootService = RootService()
    private val testRefreshable = TestRefreshable()

    /**
     *  Test if cards with illegal index are not swapped
     */
    @Test
    fun testChangeOneCardWithIllegalIndex() {
        rootService.gameService.startGame(listOf(playerName1, playerName2))
        assertFailsWith<IllegalArgumentException> {
            rootService.playerActionService.changeOneCard(-1, 1)
        }
        assertFailsWith<IllegalArgumentException> {
            rootService.playerActionService.changeOneCard(1, -1)
        }
        assertFailsWith<IllegalArgumentException> {
            rootService.playerActionService.changeOneCard(-1, -1)
        }
        assertFailsWith<IllegalArgumentException> {
            rootService.playerActionService.changeOneCard(3, 1)
        }
        assertFailsWith<IllegalArgumentException> {
            rootService.playerActionService.changeOneCard(1, 3)
        }
        assertFailsWith<IllegalArgumentException> {
            rootService.playerActionService.changeOneCard(3, 3)
        }
        assertFailsWith<IllegalArgumentException> {
            rootService.playerActionService.changeOneCard(-1, 3)
        }
        assertFailsWith<IllegalArgumentException> {
            rootService.playerActionService.changeOneCard(3, -1)
        }
    }

    /**
     *  Helper method for testChangeOneCardWithLegalIndex()
     *  - passCounter set to 0
     *  - selected cards swapped
     */
    private fun changeOneCardTestHelper(playerCardIndex: Int, middleCardIndex: Int){
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
        val handCardToSwap = game.players[game.activePlayerIndex].handDeck.cards[playerCardIndex]
        val middleCardToSwap = game.middleDeck.cards[middleCardIndex]
        testRefreshable.reset()
        assertFalse { testRefreshable.refreshOnChangeOneCardCalled }
        assertFalse { testRefreshable.refreshOnEndTurnCalledWithFalse }
        // player2 changes 1 card
        rootService.playerActionService.changeOneCard(playerCardIndex, middleCardIndex)
        // passCounter set to 0
        assert(0 == game.passCounter)
        // Selected cards swapped
        assertEquals(handCardToSwap, game.middleDeck.cards[middleCardIndex])
        assertEquals(middleCardToSwap, game.players[1].handDeck.cards[playerCardIndex])
        assertTrue { testRefreshable.refreshOnChangeOneCardCalled }
        assertTrue { testRefreshable.refreshOnEndTurnCalledWithFalse }
        testRefreshable.reset()
    }

    /**
     *  Test if cards with legal index are swapped
     */
    @Test
    fun testChangeOneCardWithLegalIndex(){
        changeOneCardTestHelper(0, 0)
        changeOneCardTestHelper(0, 1)
        changeOneCardTestHelper(0, 2)
        changeOneCardTestHelper(1, 0)
        changeOneCardTestHelper(1, 1)
        changeOneCardTestHelper(1, 2)
        changeOneCardTestHelper(2, 0)
        changeOneCardTestHelper(2, 1)
        changeOneCardTestHelper(2, 2)
    }
}