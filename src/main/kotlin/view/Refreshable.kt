package view

/**
 * This interface provides a mechanism for the service layer classes to communicate
 * (usually to the view classes) that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 *
 * Default (empty) implementations are provided for all methods, so that implementing
 * UI classes only need to react to events relevant to them.
 *
 * @see service.AbstractRefreshableService
 *
 */
interface Refreshable {
    /**
     * perform refreshes that are necessary after a new game started
     */
    fun refreshOnStartGame() {}

    /**
     * perform refreshes that are necessary after the game stopped
     */
    fun refreshOnStopGame() {}

    /**
     * perform refreshes that are necessary after passed
     */
    fun refreshOnPass() {}

    /**
     * perform refreshes that are necessary after knocked
     */
    fun refreshOnKnock() {}

    /**
     * perform refreshes that are necessary after one card changed
     *
     * @param playerCardIndex is the index of the hand card to be changed
     * @param middleCardIndex is the index of the middle card to be changed
     */
    fun refreshOnChangeOneCard(playerCardIndex: Int, middleCardIndex: Int) {}

    /**
     * perform refreshes that are necessary after all cards changed
     */
    fun refreshOnChangeAllCards() {}

    /**
     * perform refreshes that are necessary after the turn is over
     *
     * @param hasGameEnded is true if the game is over, false otherwise
     */
    fun refreshOnEndTurn(hasGameEnded: Boolean) {}
}