package service

import entity.*
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

/**
 * Test cases for [Swimmig]
 */
class SwimmigTest {
    val handDeck1 = TripleDeck(
        arrayOf(
            Card(CardSuit.CLUBS, CardValue.JACK),
            Card(CardSuit.CLUBS, CardValue.QUEEN),
            Card(CardSuit.CLUBS, CardValue.KING)
        )
    )
    val handDeck2 = TripleDeck(
        arrayOf(
            Card(CardSuit.HEARTS, CardValue.TWO),
            Card(CardSuit.HEARTS, CardValue.THREE),
            Card(CardSuit.HEARTS, CardValue.FOUR)
        )
    )
    val player1 = Player("Zoe", handDeck1)
    val player2 = Player("Ava", handDeck2)

    val playerList = listOf<Player>(player1, player2)
    val drawPile = CardPile(ArrayDeque<Card>(32))
    val middleDeck = TripleDeck(
        arrayOf(
            Card(CardSuit.SPADES, CardValue.TWO),
            Card(CardSuit.SPADES, CardValue.THREE),
            Card(CardSuit.SPADES, CardValue.FOUR)
        )
    )
    val swimming = Swimming(playerList, drawPile, middleDeck)

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