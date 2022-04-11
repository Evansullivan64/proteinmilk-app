package controllers

import models.proteinMilk

import utils.isValidListIndex.isValidListIndexs

class milkAPI {

    private var proteinMilks = ArrayList<proteinMilk>()


    fun add(proteinmilk: proteinMilk): Boolean {
        return proteinMilks.add(proteinmilk)
    }


    fun updateMilks(indexToUpdate: Int, milk: proteinMilk?): Boolean {

        val foundMilk = findMilks(indexToUpdate)

           if ((foundMilk != null) && (milk != null)) {
            foundMilk.milkTitle = milk.milkTitle
            foundMilk.price = milk.price
            foundMilk.proteinAmount = milk.proteinAmount
            foundMilk.brand = milk.brand
            foundMilk.litres = milk.litres
            return true
        }

            return false
    }

    fun delete(indexToDelete: Int): proteinMilk? {
        return if (isValidListIndexs(indexToDelete, proteinMilks)) {
            proteinMilks.removeAt(indexToDelete)
        } else null
    }

    fun listAllMilks(): String =
        if  (proteinMilks.isEmpty()) "No notes stored"
        else formatListString(proteinMilks)

    fun formatListString(milksToFormat : List<proteinMilk>) : String =
        milksToFormat
            .joinToString (separator = "\n") { milk ->
                proteinMilks.indexOf(milk).toString() + ": " + milk.toString() }

    fun numberOfMilks(): Int {
        return proteinMilks.size
    }

    fun findMilks(index: Int): proteinMilk? {
        return if (isValidListIndexs(index, proteinMilks)) {
            proteinMilks[index]
        } else null
    }



    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndexs(index, proteinMilks);
    }


}