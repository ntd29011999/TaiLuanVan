package com.example.appgrouplocate.user

class Group {
    constructor(){}

    var users : List<User> = listOf()
    var groupId : String = ""

    fun addToGroup(uid : String) : Boolean
    {
        var user =  User("a","b") //fake user
        return if(!users.contains(user)) {
            users += user
            true
        }else
            false

    }

    fun removeFromGroup(uid: String) : Boolean
    {
        var user =  User("a","b") //fake user
        return if(users.contains(user)) {
            users -= user
            true
        }else
            false
    }
}