class TranspositionCipher {
    companion object {
        val DEFAULT_MATRIX: Matrix = arrayOf(
                booleanArrayOf(false, false, false, false, false, true, false, false, false, false),
                booleanArrayOf(false, false, false, false, false, false, false, false, false, true),
                booleanArrayOf(false, false, false, false, false, false, false, true, false, false),
                booleanArrayOf(true, false, false, false, false, false, false, false, false, false),
                booleanArrayOf(false, false, false, false, true, false, false, false, false, false),
                booleanArrayOf(false, false, true, false, false, false, false, false, false, false),
                booleanArrayOf(false, false, false, false, false, false, false, false, true, false),
                booleanArrayOf(false, false, false, true, false, false, false, false, false, false),
                booleanArrayOf(false, true, false, false, false, false, false, false, false, false),
                booleanArrayOf(false, false, false, false, false, false, true, false, false, false)
        )

        private val pt: Matrix = key.sliceArray(key.size - 2 - 10 until key.size - 2).toMatrix()

        fun encrypt(text: ByteArray, key: ByteArray): ByteArray {
            return if (text.size != key.size) text else text.transposition(key)
        }

        fun decrypt(text: ByteArray, key: ByteArray): ByteArray {
            return if (text.size != key.size) text else text.transpositionReverse(key)
        }

        fun getNextMatrix(currentMatrix: Matrix) = pt * currentMatrix
    }
}
