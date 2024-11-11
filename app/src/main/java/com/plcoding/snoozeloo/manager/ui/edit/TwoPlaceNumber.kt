package com.plcoding.snoozeloo.manager.ui.edit

data class TwoPlaceNumber(val firstPlace: Int, val secondPlace: Int) {
    var value = firstPlace * 10 + secondPlace
    fun setFirstPlace(number: Int) = copy(firstPlace = number)
    fun setSecondPlace(number: Int) = copy(secondPlace = number)
}