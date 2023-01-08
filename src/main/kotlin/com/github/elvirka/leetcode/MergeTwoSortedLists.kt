package com.github.elvirka.leetcode

private class ListNode(val `val`: Int) {
    var next: ListNode? = null
}

fun main() {
    val list1 = ListNode(1).apply {
        next = ListNode(2).apply {
            next = ListNode(4)
        }
    }
    val list2 = ListNode(1).apply {
        next = ListNode(3).apply {
            next = ListNode(4)
        }
    }
    var result = mergeTwoLists(list1, list2)
    while (result?.next != null) {
        println(result.`val`)
        result = result.next
    }
}

private fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
    val result = when {
        list1 == null -> list2
        list2 == null -> list1
        list1.`val` < list2.`val` -> list1.apply { next = mergeTwoLists(list1.next, list2) }
        else -> list2.apply { next = mergeTwoLists(list1, list2.next) }
    }
    return result
}