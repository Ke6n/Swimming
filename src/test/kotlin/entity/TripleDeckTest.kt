package entity

import kotlin.test.*

/**
 * Test cases for [TripleDeck]
 */
class TripleDeckTest {

    private val card1 = Card(CardSuit.CLUBS, CardValue.JACK)
    private val card2 = Card(CardSuit.CLUBS, CardValue.QUEEN)
    private val card3 = Card(CardSuit.CLUBS, CardValue.KING)
    private val card4 = Card(CardSuit.CLUBS, CardValue.ACE)

    /**
     * Test if the [TripleDeck] is not initialized with less than 3 cards
     */
    @Test
    fun testTripleDeckInitWithTwoCards() {
        assertFailsWith<IllegalStateException> {
            TripleDeck(mutableListOf(card1, card2))
        }
    }

    /**
     * Test if the [TripleDeck] is not initialized with no cards
     */
    @Test
    fun testTripleDeckInitWithNoCards() {
        assertFailsWith<IllegalStateException> {
            TripleDeck(mutableListOf())
        }
    }
    /**
     * Test if the [TripleDeck] is not initialized with more than 3 cards
     */
    @Test
    fun testTripleDeckInitWithFourCards() {
        assertFailsWith<IllegalStateException> {
            TripleDeck(mutableListOf(card1, card2, card3, card4))
        }
    }

}