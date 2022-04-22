package utils

/**
 * This class provides methods for validating amount of protein.
 * It returns false if the amount of protein is less than 0
 *
 *
 * @author Evan Sullivan
 * @version 1.0
 */

object isValidProteinAmount {

    @JvmStatic
    fun isValidProteinAmount(protein: Double):Boolean{
        return protein > 0
    }

}