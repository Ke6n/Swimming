package service

/**
 * Service layer class that provides the logic for the four possible actions a player
 * can take in the game: pass, knock, change one card and change all cards.
 */
class PlayerActionService(private var rootService: RootService) : AbstractRefreshableService() {

    /**
     * After the current player passes, the pass counter is incremented by 1.
     * Then this method calls the endTurn() method to handle subsequent actions.
     */
    fun pass() {
        val game = rootService.currentGame!!
        game.passCounter++
        endTurn()
    }

    /**
     * If a player knocked for the first time, set hasKnocked to true and record the number of remaining players.
     * Every time a Player knocks, set the pass counter to 0.
     * Then this method calls the endTurn() method to handle subsequent actions.
     */
    fun knock() {
        val game = rootService.currentGame!!
        if (!game.hasKnocked) {
            game.hasKnocked = true
            game.movesRemaining = game.players.size
        }
        game.passCounter = 0
        endTurn()
    }

    /**
     * Swap a hand card with a middle card.
     * Set the pass counter to 0.
     * Then this method calls the endTurn() method to handle subsequent actions.
     *
     * @param playerCardIndex is the index of the hand card to be changed (in 0 - 2)
     * @param middleCardIndex is the index of the middle card to be changed (in 0 - 2)
     *
     * @throws IllegalArgumentException if playerCardIndex or middleCardIndex is out of index
     */
    fun changeOneCard(playerCardIndex: Int, middleCardIndex: Int) {
        val game = rootService.currentGame!!
        require(playerCardIndex in 0..2) { "playerCardIndex is out of index" }
        require(middleCardIndex in 0..2) { "middleCardIndex is out of index" }
        game.passCounter = 0

        // Cards swap
        val handCards = game.players[game.activePlayerIndex].handDeck.cards
        val middleCards = game.middleDeck.cards
        handCards[playerCardIndex] = middleCards[middleCardIndex].apply {
            middleCards[middleCardIndex] = handCards[playerCardIndex]
        }

        onAllRefreshable { refreshOnChangeOneCard(playerCardIndex, middleCardIndex) }
        endTurn()
    }

    /**
     * Swap all hand cards with middle cards.
     * Set the pass counter to 0.
     * Then this method calls the endTurn() method to handle subsequent actions.
     */
    fun changeAllCards() {
        val game = rootService.currentGame!!
        game.passCounter = 0

        // Cards swap
        game.players[game.activePlayerIndex].handDeck =
            game.middleDeck.apply { game.middleDeck = game.players[game.activePlayerIndex].handDeck }

        onAllRefreshable { refreshOnChangeAllCards() }
        endTurn()
    }

    /**
     * Decide whether to end the game or the next player's turn
     */
    private fun endTurn() {
        val game = rootService.currentGame!!
        if (game.hasKnocked) {
            game.movesRemaining--
            onAllRefreshable { refreshOnKnock() }
            if (game.movesRemaining <= 0) {
                onAllRefreshable { refreshOnEndTurn(true) }
                return
            }
        }
        if (game.passCounter == game.players.size) {
            if (game.drawPile.cardsOnPile.size >= 3) {
                // put cards of middle deck on discard pile
                game.middleDeck.cards.forEach(game.discardPile.cardsOnPile::addFirst)
                // put top 3 cards of drawPile in middleDeck
                game.middleDeck.cards.replaceAll { game.drawPile.cardsOnPile.removeFirst() }
                game.passCounter = 0
                onAllRefreshable { refreshOnPass() }
            } else {
                onAllRefreshable { refreshOnEndTurn(true) }
                return
            }
        }
        game.changeActivePlayer()
        onAllRefreshable { refreshOnEndTurn(false) }
    }
}