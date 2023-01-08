package com.github.elvirka.leetcode

import kotlin.test.assertEquals

private class BestTimeToBuyAndSellStockII {
    fun maxProfit(prices: IntArray): Int {
        var profit = 0
        var buyPrice = Int.MAX_VALUE
        var sellPrice = Int.MIN_VALUE
        var hasStock = false
        for (i in 0..prices.lastIndex) {
            val price = prices[i]
            val nextPrice = if (i < prices.lastIndex) prices[i + 1] else null
            if (hasStock) {
                if (price >= sellPrice) {
                    sellPrice = price
                    if (nextPrice == null || price > nextPrice) {
                        hasStock = false
                        profit += (sellPrice - buyPrice)
                        buyPrice = Int.MAX_VALUE
                        sellPrice = Int.MIN_VALUE
                    }
                }
            } else {
                if (price <= buyPrice) {
                    buyPrice = price
                    if (nextPrice == null || price < nextPrice) {
                        hasStock = true
                    }
                }
            }
        }
        return profit
    }
}

fun main() {
    val c = BestTimeToBuyAndSellStockII()
    assertEquals(3, c.maxProfit(intArrayOf(2, 2, 5)))
    assertEquals(4, c.maxProfit(intArrayOf(1, 2, 3, 4, 5)))
    assertEquals(0, c.maxProfit(intArrayOf(5, 4, 3, 2, 1)))
    assertEquals(20, c.maxProfit(intArrayOf(5, 2, 3, 2, 6, 6, 2, 9, 1, 0, 7, 4, 5, 0)))
    assertEquals(12, c.maxProfit(intArrayOf(1, 7, 2, 3, 6, 7, 6, 7)))
}


