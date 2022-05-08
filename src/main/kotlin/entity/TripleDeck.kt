package entity

/**
 * Data structure that holds [Card] objects for hand and middle cards.
 *
 */
class TripleDeck(val cards: MutableList<Card>) {
    /**
     * Check if there are exactly 3 cards in TripleDeck.
     */
    init {
        check(cards.size == 3) { "TripleDeck can only hold 3 cards" }
    }
}