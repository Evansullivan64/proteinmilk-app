package utils

/**
 * This class provides methods for validating the milk price.
 * It returns false if the amount of price is less than 0
 *
 *
 * @author Evan Sullivan
 * @version 1.0
 */

object isValidPrice {

    @JvmStatic
    fun isValidPrice(price: Double): Boolean {
        return price > 0
    }
}
