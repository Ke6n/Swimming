package service

import entity.*
import kotlin.test.*

/**
 * Test cases for the method calculatePlayerScore() in class [GameService]
 */
class CalculatePlayerScoreTest {
    private val gameService = GameService(RootService())
    private val handDeck1 = TripleDeck(
        mutableListOf(
            Card(CardSuit.CLUBS, CardValue.JACK),
            Card(CardSuit.CLUBS, CardValue.QUEEN),
            Card(CardSuit.CLUBS, CardValue.KING)
        )
    )

    private val handDeck2 = TripleDeck(
        mutableListOf(
            Card(CardSuit.CLUBS, CardValue.KING),
            Card(CardSuit.HEARTS, CardValue.KING),
            Card(CardSuit.DIAMONDS, CardValue.KING)
        )
    )

    private val handDeck3 = TripleDeck(
        mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.CLUBS, CardValue.QUEEN),
            Card(CardSuit.DIAMONDS, CardValue.KING)
        )
    )

    private val handDeck4 = TripleDeck(
        mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.CLUBS, CardValue.JACK),
            Card(CardSuit.DIAMONDS, CardValue.KING)
        )
    )

    private val handDeck5 = TripleDeck(
        mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.CLUBS, CardValue.TEN),
            Card(CardSuit.DIAMONDS, CardValue.KING)
        )
    )

    private val handDeck6 = TripleDeck(
        mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.CLUBS, CardValue.NINE),
            Card(CardSuit.DIAMONDS, CardValue.KING)
        )
    )

    private val handDeck7 = TripleDeck(
        mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.CLUBS, CardValue.SEVEN),
            Card(CardSuit.DIAMONDS, CardValue.KING)
        )
    )

    private val handDeck8 = TripleDeck(
        mutableListOf(
            Card(CardSuit.CLUBS, CardValue.EIGHT),
            Card(CardSuit.SPADES, CardValue.SEVEN),
            Card(CardSuit.DIAMONDS, CardValue.ACE)
        )
    )

    /**
     * The score of hand deck ♣J, ♣Q, ♣K should be 30
     */
    @Test
    fun testHandDeck1() {
        val player = Player("a", handDeck1)
        assert(30.0 == gameService.calculatePlayerScore(player))
    }

    /**
     * The score of hand deck ♣K, ♥K, ♦K should be 30.5
     */
    @Test
    fun testHandDeck2() {
        val player = Player("a", handDeck2)
        assert(30.5 == gameService.calculatePlayerScore(player))
    }

    /**
     * The score of hand deck ♣8, ♣Q, ♦K should be 18
     */
    @Test
    fun testHandDeck3() {
        val player = Player("a", handDeck3)
        assert(18.0 == gameService.calculatePlayerScore(player))
    }

    /**
     * The score of hand deck ♣8, ♣J, ♦K should be 18
     */
    @Test
    fun testHandDeck4() {
        val player = Player("a", handDeck4)
        assert(18.0 == gameService.calculatePlayerScore(player))
    }

    /**
     * The score of hand deck ♣8, ♣10, ♦K should be 18
     */
    @Test
    fun testHandDeck5() {
        val player = Player("a", handDeck5)
        assert(18.0 == gameService.calculatePlayerScore(player))
    }

    /**
     * The score of hand deck ♣8, ♣9, ♦K should be 17
     */
    @Test
    fun testHandDeck6() {
        val player = Player("a", handDeck6)
        assert(17.0 == gameService.calculatePlayerScore(player))
    }

    /**
     * The score of hand deck ♣8, ♣7, ♦K should be 15
     */
    @Test
    fun testHandDeck7() {
        val player = Player("a", handDeck7)
        assert(15.0 == gameService.calculatePlayerScore(player))
    }

    /**
     * The score of hand deck ♣8, ♠7, ♦A should be 11
     */
    @Test
    fun testHandDeck8() {
        val player = Player("a", handDeck8)
        assert(11.0 == gameService.calculatePlayerScore(player))
    }
}