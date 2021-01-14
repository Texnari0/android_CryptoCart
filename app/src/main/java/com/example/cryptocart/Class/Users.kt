package com.example.cryptocart.Class

class Users {
    var name: String? = ""
    var login: String?
    var pass: String?

    constructor(name: String, login: String, pass: String){
        this.name = name
        this.login = login
        this.pass = pass
    }

    constructor(login: String, pass: String){
        this.login = login
        this.pass = pass
    }
}