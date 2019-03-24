class KeyParser(byteArray: ByteArray) {

    private val key = byteArray

    companion object {
        const val GAMMA_SIZE_T = 8
        const val BLOCK_SIZE = 15
        const val GAMMA_SIZE = 64 - GAMMA_SIZE_T - BLOCK_SIZE - BLOCK_SIZE - 2
    }

    val transpositionCipher = TranspositionCipher(
            key.sliceArray(GAMMA_SIZE_T + GAMMA_SIZE until GAMMA_SIZE_T + GAMMA_SIZE + BLOCK_SIZE).toMatrix(),
            key.sliceArray(key.size - 2 - BLOCK_SIZE until key.size - 2).toMatrix())

    val substitutionCipher = SubstitutionCipher(key[key.size - 2], key[key.size - 1])

    val gammaCipher = Gamma(
            key.sliceArray(0 until GAMMA_SIZE),
            key.sliceArray(GAMMA_SIZE until GAMMA_SIZE + GAMMA_SIZE_T))
}
