package service

import view.Refreshable

/**
 * [Refreshable] implementation that refreshes nothing, but remembers
 * if a refresh method has been called (since last [reset])
 */
class TestRefreshable : Refreshable {

    var refreshOnStartGameCalled = false
        private set
    var refreshOnStopGameCalled = false
        private set
    var refreshOnPassCalled = false
        private set
    var refreshOnKnockCalled = false
        private set
    var refreshOnChangeOneCardCalled = false
        private set
    var refreshOnChangeAllCardsCalled = false
        private set
    var refreshOnEndTurnCalledWithTrue = false
        private set
    var refreshOnEndTurnCalledWithFalse = false
        private set

    /**
     * resets all *Called properties to false
     */
    fun reset() {
        refreshOnStartGameCalled = false
        refreshOnStopGameCalled = false
        refreshOnPassCalled = false
        refreshOnKnockCalled = false
        refreshOnChangeOneCardCalled = false
        refreshOnChangeAllCardsCalled = false
        refreshOnEndTurnCalledWithTrue = false
        refreshOnEndTurnCalledWithFalse = false
    }

    override fun refreshOnStartGame() {
        refreshOnStartGameCalled = true
    }

    override fun refreshOnStopGame() {
        refreshOnStopGameCalled = true
    }

    override fun refreshOnPass() {
        refreshOnPassCalled = true
    }

    override fun refreshOnKnock() {
        refreshOnKnockCalled = true
    }

    override fun refreshOnChangeOneCard(playerCardIndex: Int, middleCardIndex: Int) {
        refreshOnChangeOneCardCalled = true
    }

    override fun refreshOnChangeAllCards() {
        refreshOnChangeAllCardsCalled = true
    }

    override fun refreshOnEndTurn(hasGameEnded: Boolean) {
        if (hasGameEnded) refreshOnEndTurnCalledWithTrue = true
        else refreshOnEndTurnCalledWithFalse = true
    }
}