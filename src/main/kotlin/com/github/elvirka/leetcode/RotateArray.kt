package com.github.elvirka.leetcode

import kotlin.math.abs
import kotlin.test.assertEquals

class RotateArray {
    fun rotateLeft(nums: IntArray, k: Int): IntArray {
        val res = IntArray(nums.size)
        for (i in 0..nums.lastIndex) {
            if (i - k >= 0) {
                res[i - k] = nums[i]
            } else {
                res[res.size - abs(i - k)] = nums[i]
            }
        }
        return  res
    }

    fun rotateRight(nums: IntArray, k: Int): IntArray {
        val res = IntArray(nums.size)
        for (i in 0..nums.lastIndex) {
            val newIndex = res.lastIndex - k + i
            if (newIndex <= res.lastIndex) {
                res[newIndex] = nums[i]
            } else {
                res[newIndex - nums.size] = nums[i]
            }
        }
        return  res
    }

    fun rotate(nums: IntArray, k: Int): Unit {
        return println("[${rotateRight(nums, k).joinToString(",")}]")
    }
}

fun main() {
    val arr = intArrayOf(1,2,3,4,5,6,7)
    val c = RotateArray()
    val res = c.rotateRight(arr, 3)
    assertEquals(intArrayOf(5,6,7,1,2,3,4).joinToString(), res.joinToString())
    c.rotate(arr, 3)
}