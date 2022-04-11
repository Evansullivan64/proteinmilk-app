package utils

object isValidPrice {


    @JvmStatic
    fun isValidPrice(price: Double): Boolean {
        return price > 0
    }
}