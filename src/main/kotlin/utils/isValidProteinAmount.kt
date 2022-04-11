package utils

object isValidProteinAmount {

    @JvmStatic
    fun isValidProteinAmount(protein: Double):Boolean{
        return protein > 0
    }

}