package controllers


import models.proteinMilk
import persistence.Serializer

import utils.isValidListIndex.isValidListIndexs

/**
 * A group of protein milks.
 *
 * This class is responsible for the crud functionality of the system.
 *
 * @param proteinMilk the type of a proteinMilk in this group.
 * @property serializerType the name of this group.
 * @constructor Creates an empty group.
 */

class milkAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType



    private var proteinMilks = ArrayList<proteinMilk>()


    fun add(proteinmilk: proteinMilk): Boolean {
        /**
         * Adds a [proteinMilk] to this group.
         * @return the new size of the group.
         */
        return proteinMilks.add(proteinmilk)
    }


    fun updateMilks(indexToUpdate: Int, milk: proteinMilk?): Boolean {
        /**
         * updates a [proteinMilk] of this group.
         * @return true or false if the milk was updated.
         */

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
        if  (proteinMilks.isEmpty()) "No milks stored"
        else formatListString(proteinMilks)


    fun listMilksByBrand( brand:String): String =
        if  (numberOfMilks() == 0)  "No milks stored"
        else formatListString(proteinMilks.filter { milk -> milk.brand.contains(brand) })

    fun listMilksByPrice( Price:Double): String =
        if  (numberOfMilks() == 0)  "No milks stored"
        else formatListString(proteinMilks.filter { milk -> milk.price.equals(Price) })


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

    fun favouriteMilk(milkToFavourite: Int): Boolean {
        if (isValidIndex(milkToFavourite)) {
            val milksToFavourite = proteinMilks[milkToFavourite]
            if (!milksToFavourite.isMilkFavourited) {
                milksToFavourite.isMilkFavourited = true
                return true
            }
        }
        return false
    }



    fun listFavouriteMilk(): String =
        if  (numberOfFavouriteMilks() == 0) "you have no favourite milks"
        else formatListString(proteinMilks.filter { milks -> milks.isMilkFavourited})


    fun numberOfFavouriteMilks(): Int = proteinMilks
        .filter { milk: proteinMilk -> milk.isMilkFavourited }
        .count()



    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndexs(index, proteinMilks);
    }


    @Throws(Exception::class)
    fun load() {
        proteinMilks = serializer.read() as ArrayList<proteinMilk>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(proteinMilks)
    }


}