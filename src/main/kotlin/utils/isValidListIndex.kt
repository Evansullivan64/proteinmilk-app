package utils

/**
 * This class provides methods for validating the index in the arraylist.
 * It returns true if the index is greater than 0 and less than the arraylist size
 *
 *
 * @author Evan Sullivan
 * @version 1.0
 */

object isValidListIndex {


    @JvmStatic
    fun isValidListIndexs(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}