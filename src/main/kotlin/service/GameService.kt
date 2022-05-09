package service

import entity.*

/**
 * Service layer class that provides the logic for actions not directly
 * related to a single player.
 */
class GameService(private val rootService: RootService) : AbstractRefreshableService() {

    /**
     * Calculate player score
     *
     * @param player: The player who calculates his score
     *
     * @return score: The score of this player
     */
    fun calculatePlayerScore(player: Player): Double {
        // a Map with Key = CardSuit, Value= sum of card points with the same suit
        val cardsMap = mutableMapOf(
            CardSuit.CLUBS to 0.0,
            CardSuit.SPADES to 0.0,
            CardSuit.HEARTS to 0.0,
            CardSuit.DIAMONDS to 0.0
        )
        for (card in player.handDeck.cards) {
            cardsMap[card.suit] = cardsMap.getValue(card.suit) + card.getPoints()
        }
        var maxValue = cardsMap.maxByOrNull { it.value }!!.value
        // If there are 3 suits in the hand with the same points
        if (cardsMap.count { it.value == maxValue } == 3) {
            maxValue = 30.5
        }
        return maxValue
    }

    /**
     *  Calculate points of a card in the game "Swimming"
     */
    private fun Card.getPoints() = when (value) {
        CardValue.ACE -> 11.0
        CardValue.JACK, CardValue.QUEEN, CardValue.KING -> 10.0
        else -> value.toString().toDouble()
    }

    /**
     * Start or restart a game
     *
     * @param playerNames: The list of player names
     */
    fun startGame(playerNames: List<String>) {
        val allCards = createAllCards()
        val middleDeck = TripleDeck(MutableList(3) { allCards.cardsOnPile.removeFirst() })
        val players = createPlayers(playerNames, allCards)
        rootService.currentGame = Swimming(players, allCards, middleDeck)
        onAllRefreshable { refreshOnStartGame() }
    }

    /**
     * Stop the game, go to main menu
     */
    fun stopGame() {
        rootService.currentGame = null
        onAllRefreshable { refreshOnStopGame() }
    }

    /**
     * Creates a list of all players
     */
    private fun createPlayers(playerNames: List<String>, cardPile: CardPile) = List(playerNames.size) { index ->
        Player(playerNames[index], TripleDeck(MutableList(3) { cardPile.cardsOnPile.removeFirst() }))
    }

    /**
     * Creates a shuffled 32 cards pile of all four suits and cards
     * from 7 to Ace
     */
    private fun createAllCards(): CardPile {
        val allCardsList = List(32) { index ->
            Card(
                CardSuit.values()[index / 8],
                CardValue.values()[(index % 8) + 5]
            )
        }.shuffled()
        return CardPile(ArrayDeque(allCardsList))
    }
}