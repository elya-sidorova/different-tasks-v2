package com.github.elvirka.fpBook

class Cafe {
    fun buyCoffee(cc: CreditCard): Pair<Coffee, Charge> =
        Coffee("") to Charge(cc, 5.0F)

    fun buyCoffees(
        cc: CreditCard,
        n: Int
    ): Pair<List<Coffee>, Charge> {
        val purchases: List<Pair<Coffee, Charge>> =
            List(n) { buyCoffee(cc) }

        val (coffees, charges) = purchases.unzip()

        return Pair(
            coffees,
            charges.reduce { c1, c2 -> c1.combine(c2) }
        )
    }
}

data class Coffee(val value:String)

data class CreditCard(val value:String)

data class Charge(val cc: CreditCard, val amount: Float) {

    fun combine(other: Charge): Charge =
        if (cc == other.cc)
            Charge(cc, amount + other.amount)
        else throw Exception("Cannot combine charges to different cards")
}