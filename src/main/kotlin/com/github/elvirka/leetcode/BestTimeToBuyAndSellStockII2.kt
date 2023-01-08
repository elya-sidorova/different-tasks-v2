package com.github.elvirka.leetcode

import kotlin.test.assertEquals

private class BestTimeToBuyAndSellStockII2 {
    fun maxProfit(prices: IntArray): Int {
        var profit = 0
        var buyPrice: Int
        var sellPrice: Int
        var i = 0
        while (i < prices.lastIndex) {
            while (i < prices.lastIndex && prices[i] >= prices[i + 1])
                i++
            buyPrice = prices[i]
            while (i < prices.lastIndex && prices[i] <= prices[i + 1])
                i++
            sellPrice = prices[i]
            profit += sellPrice - buyPrice
        }
        return profit
    }
}

fun main() {
    val c = BestTimeToBuyAndSellStockII2()
    assertEquals(3, c.maxProfit(intArrayOf(2, 2, 5)))
    assertEquals(4, c.maxProfit(intArrayOf(1, 2, 3, 4, 5)))
    assertEquals(0, c.maxProfit(intArrayOf(5, 4, 3, 2, 1)))
    assertEquals(20, c.maxProfit(intArrayOf(5, 2, 3, 2, 6, 6, 2, 9, 1, 0, 7, 4, 5, 0)))
    assertEquals(12, c.maxProfit(intArrayOf(1, 7, 2, 3, 6, 7, 6, 7)))
}


