package com.example.nikita.mymoney.database.model

data class Cash(val id:Int, val categoryId:Int, val cost:Double, val date: String){
    companion object {
        const val TABLENAME: String = "Cash"
    }
}