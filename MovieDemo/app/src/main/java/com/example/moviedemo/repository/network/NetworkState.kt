package com.example.moviedemo.repository.network

enum class Status{
    RUNING, SUCCESS, FAILED, NO_MORE_ROW

}
class NetworkState(var status: Status, val message:String) {
companion object{
    val LOADED=NetworkState(Status.SUCCESS,"Sucess")
    val FAILED=NetworkState(Status.FAILED,"Failed")
    val RUNING=NetworkState(Status.RUNING,"Running")
    val NO_MORE_ROW=NetworkState(Status.NO_MORE_ROW,"End of list")
}

}