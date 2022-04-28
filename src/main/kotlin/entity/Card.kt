package entity

/**
 * Data class for the single typ of game elements that the game "Swimming" knows: cards.
 *
 * It is characterized by a [CardSuit] and a [CardValue]
 */
data class Card(val suit: CardSuit, val value: CardValue) {
    // Card Points in the game "Swimming"
    val cardPoints = when (value) {
        CardValue.ACE -> 11.0
        CardValue.JACK, CardValue.QUEEN, CardValue.KING -> 10.0
        else -> value.toString().toDouble()
    }

    override fun toString() = "$suit$value"
}