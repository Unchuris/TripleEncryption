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

        private val pt: Matrix = arrayOf(
                booleanArrayOf(false, false, false, false, false, false, false, false, true, false),
                booleanArrayOf(false, false, false, false, false, false, false, false, false, true),
                booleanArrayOf(false, false, false, false, false, false, false, true, false, false),
                booleanArrayOf(false, false, true, false, false, false, false, false, false, false),
                booleanArrayOf(true, false, false, false, false, false, false, false, false, false),
                booleanArrayOf(false, false, false, false, true, false, false, false, false, false),
                booleanArrayOf(false, false, false, true, false, false, false, false, false, false),
                booleanArrayOf(false, false, false, false, false, false, true, false, false, false),
                booleanArrayOf(false, false, false, false, false, true, false, false, false, false),
                booleanArrayOf(false, true, false, false, false, false, false, false, false, false)
        )

        fun encrypt(text: ByteArray, key: Matrix): ByteArray {
            return if (text.size != key.size) text else text.transposition(key.toTransposition())
        }

        fun decrypt(text: ByteArray, key: Matrix): ByteArray {
            return if (text.size != key.size) text else text.transpositionReverse(key.toTransposition())
        }

        fun getNextMatrix(currentMatrix: Matrix) = pt * currentMatrix
    }
}
