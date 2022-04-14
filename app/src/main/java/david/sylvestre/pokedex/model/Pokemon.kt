package david.sylvestre.pokedex.model

data class Pokemon(
    val id: Int,
    val name: String,
    val description: String,
    val types: List<String>,
    val base: PokemonBase,
    val preEvolution: Int,
    val evolution: List<Int>,
    val sprite: String,
    val thumbnail: String,
    val hires: String,
    ) {
}

data class PokemonBase(
    val HP: Int,
    val Attack: Int,
    val Defense: Int,
    val SpAttack: Int,
    val SpDefense: Int,
    val Speed: Int
)