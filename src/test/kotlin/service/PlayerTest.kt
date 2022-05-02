package service

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

    @Test
    fun testInitPlayerWithBlankName(){
        assertFailsWith<IllegalArgumentException> {
            Player(name1, handDeck)
        }
    }
}