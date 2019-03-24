class CustomCipher() {

    companion object {

        fun encrypt(byteArray: ByteArray, round: Int, transpositionCipher: TranspositionCipher, substitutionCipher: SubstitutionCipher, gammaCipher: Gamma): ByteArray {
            var array = byteArray

            (0 until round).forEach {
                val step1 = transpositionCipher.encrypt(array)
                val step2 = substitutionCipher.encrypt(step1)
                array = gammaCipher.encrypt(step2)
            }

            return array
        }

        fun decrypt(byteArray: ByteArray, round: Int, transpositionCipher: TranspositionCipher, substitutionCipher: SubstitutionCipher, gammaCipher: Gamma): ByteArray {
            var array = byteArray

            (0 until round).forEach {
                val step1 = gammaCipher.decrypt(array)
                val step2 = substitutionCipher.decrypt(step1)
                array = transpositionCipher.decrypt(step2)
            }

            return array
        }
    }
}
