package com.github.elvirka.leetcode

import kotlin.test.assertEquals

private class RemoveDuplicatesSortedArray {
    fun removeDuplicates(nums: IntArray): Int {
        var lastUniqueIndex = 0
        for (i in 1..nums.lastIndex) {
            val curr = nums[i]
            if (curr != nums[lastUniqueIndex]) {
                lastUniqueIndex++
                nums[lastUniqueIndex] = curr
            }
        }
        return lastUniqueIndex + 1
    }
}

fun main() {
    val arr = intArrayOf(1, 1, 2)
    val c = RemoveDuplicatesSortedArray()
    val res = c.removeDuplicates(arr)
    val output = "$res, nums = [${arr.joinToString(",")}]"
    assertEquals("4, nums = [1,2,2]", output)
}