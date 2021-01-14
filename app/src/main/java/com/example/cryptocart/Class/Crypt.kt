package com.example.cryptocart.Class

class Crypt {
     var name: String
     var count: Float
     var price: Float
     var id_user: Int
     var active: Int = 1
     var buy: Int

    constructor(name: String, count: Float,price: Float,id_user: Int,buy: Int ){
        this.name = name
        this.count = count
        this.price = price
        this.id_user = id_user
        this.buy = buy
    }
}