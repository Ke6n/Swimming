package entity

import entity.*
import kotlin.test.*


/**
 * Test cases for [Swimmig]
 */
class PlayerTest {
    private val name1 = ""

    private val handDeck = TripleDeck(
        mutableListOf(
            Card(CardSuit.HEARTS, CardValue.JACK),
            Card(CardSuit.HEARTS, CardValue.QUEEN),
            Card(CardSuit.HEARTS, CardValue.KING)
        )
    )

    /**
     * Test if the player is not initialized with blank name
     */
    @Test
    fun testInitPlayerWithBlankName(){
        assertFailsWith<IllegalStateException> {
            Player(name1, handDeck)
        }
    }
}