package service

import entity.*
import kotlin.test.*


/**
 * Test cases for [Swimming]
 */
class SwimmigTest {
    private val handDeck1 = TripleDeck(
        mutableListOf(
            Card(CardSuit.CLUBS, CardValue.JACK),
            Card(CardSuit.CLUBS, CardValue.QUEEN),
            Card(CardSuit.CLUBS, CardValue.KING)
        )
    )
    private val handDeck2 = TripleDeck(
        mutableListOf(
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.HEARTS, CardValue.THREE),
            Card(CardSuit.HEARTS, CardValue.FOUR)
        )
    )
    private val player1 = Player("Zoe", handDeck1)
    private val player2 = Player("Ava", handDeck2)


    private val playerList = listOf(player1, player2)
    private val drawPile = CardPile(ArrayDeque(32))
    private val middleDeck = TripleDeck(
        mutableListOf(
            Card(CardSuit.SPADES, CardValue.TWO),
            Card(CardSuit.SPADES, CardValue.THREE),
            Card(CardSuit.SPADES, CardValue.FOUR)
        )
    )
    private val swimming = Swimming(playerList, drawPile, middleDeck)

    /**
     * Test if the index of active player changed correctly
     *
     * The index is incremented by 1 on every change.
     * Set to 0 if it's out of bounds.
     */
    @Test
    fun testChangeActivePlayer() {
        for (i in 1..playerList.size * 2) {
            swimming.changeActivePlayer()
            assertEquals(i % playerList.size, swimming.activePlayerIndex)
        }
    }
}