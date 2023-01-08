package com.github.elvirka.leetcode

import kotlin.test.assertEquals

private class BestTimeToBuyAndSellStockII3 {
    fun maxProfit(prices: IntArray): Int {
        var profit = 0
        for (i in 1 until prices.size) {
            if (prices[i] > prices[i - 1])
                profit += prices[i] - prices[i - 1]
        }
        return profit
    }
}

fun main() {
    val c = BestTimeToBuyAndSellStockII3()
    assertEquals(3, c.maxProfit(intArrayOf(2, 2, 5)))
    assertEquals(4, c.maxProfit(intArrayOf(1, 2, 3, 4, 5)))
    assertEquals(0, c.maxProfit(intArrayOf(5, 4, 3, 2, 1)))
    assertEquals(20, c.maxProfit(intArrayOf(5, 2, 3, 2, 6, 6, 2, 9, 1, 0, 7, 4, 5, 0)))
    assertEquals(9, c.maxProfit(intArrayOf(1, 7, 2, 3, 6, 7, 6, 7)))
}


