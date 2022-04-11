package controllers

import models.proteinMilk

class milkAPI {

    private var proteinMilks = ArrayList<proteinMilk>()


    fun add(proteinmilk: proteinMilk): Boolean {
        return proteinMilks.add(proteinmilk)
    }


}