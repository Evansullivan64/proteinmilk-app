package utils

object isValidListIndex {


    @JvmStatic
    fun isValidListIndexs(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }
}