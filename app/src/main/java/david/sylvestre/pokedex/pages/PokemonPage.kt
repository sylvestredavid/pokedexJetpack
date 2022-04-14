package david.sylvestre.pokedex.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import david.sylvestre.pokedex.data.SampleData
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import david.sylvestre.pokedex.composants.PokemonCard
import david.sylvestre.pokedex.model.Pokemon
import david.sylvestre.pokedex.model.PokemonBase


@Composable
fun PokemonPage(navController: NavController, id: String?) {
    if (id != null) {
        val pokemon = SampleData.pokemons.find { pokemon -> pokemon.id == id.toInt() }
        if (pokemon != null) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = pokemon.name)
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { navController.navigate("pokemonForm/${pokemon.id}") }) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = "edit"
                                )
                            }
                            IconButton(onClick = { navController.navigate("pokedex") }) {
                                Icon(
                                    imageVector = Icons.Filled.List,
                                    contentDescription = "return to list"
                                )
                            }
                        }
                    )
                }
            ) {
                PokemonBody(pokemon = pokemon, navController = navController)
            }
        } else {
            Text(text = "Pokemon introuvable")
        }
    }
}

@Composable
fun PokemonBody(pokemon: Pokemon, navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(all = 10.dp)
            .verticalScroll(scrollState)
    ) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberImagePainter(
                    data = pokemon.hires
                ),
                contentDescription = "Android Logo",
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 20.dp)
            )
        }
        Text(text = getTypeString(pokemon.types))
        Text(text = pokemon.description, modifier = Modifier.padding(vertical = 10.dp))
        BaseTable(pokemon.base)
        PreEvolution(id = pokemon.preEvolution, naveController = navController)
        Evolution(ids = pokemon.evolution, naveController = navController)
    }
}

@Composable
fun PreEvolution(id: Int, naveController: NavController) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(text = "Pré évolution", style = MaterialTheme.typography.h4)
        if (id == 0) {
            Text(text = "Pokémon de base")
        } else {
            val pokemon = SampleData.pokemons.find { it.id == id }!!
            PokemonCard(pokemon = pokemon, navController = naveController)
        }
    }
}

@Composable
fun Evolution(ids: List<Int>, naveController: NavController) {

    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(
            text = if (ids.size > 1) "Evolutions" else "Evolution",
            style = MaterialTheme.typography.h4
        )
        if (ids.isEmpty()) {
            Text(text = "Ce pokémon n'a pas d'évolutions")
        } else {
            ids.forEach { id ->

                val pokemon = SampleData.pokemons.find { it.id == id }!!
                PokemonCard(pokemon = pokemon, navController = naveController)
            }
        }
    }
}

@Composable
fun BaseTable(base: PokemonBase) {
    Column(
        modifier = Modifier
            .border(1.dp, Color.Black)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .border(1.dp, Color.Black)
                .fillMaxWidth()
                .padding(vertical = 5.dp), horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Points de vie")
            Text(text = "${if (base.HP > 0) base.HP else "non communiqué"}")
        }
        Row(
            modifier = Modifier
                .border(1.dp, Color.Black)
                .fillMaxWidth()
                .padding(vertical = 5.dp), horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Attaque")
            Text(text = "${if (base.Attack > 0) base.Attack else "non communiqué"}")
        }
        Row(
            modifier = Modifier
                .border(1.dp, Color.Black)
                .fillMaxWidth()
                .padding(vertical = 5.dp), horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Défense")
            Text(text = "${if (base.Defense > 0) base.Defense else "non communiqué"}")
        }
        Row(
            modifier = Modifier
                .border(1.dp, Color.Black)
                .fillMaxWidth()
                .padding(vertical = 5.dp), horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Attaque spéciale")
            Text(text = "${if (base.SpAttack > 0) base.SpAttack else "non communiqué"}")
        }
        Row(
            modifier = Modifier
                .border(1.dp, Color.Black)
                .fillMaxWidth()
                .padding(vertical = 5.dp), horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Défense spéciale")
            Text(text = "${if (base.SpDefense > 0) base.SpDefense else "non communiqué"}")
        }
        Row(
            modifier = Modifier
                .border(1.dp, Color.Black)
                .fillMaxWidth()
                .padding(vertical = 5.dp), horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Vitesse")
            Text(text = "${if (base.Speed > 0) base.Speed else "non communiqué"}")
        }
    }
}

fun getTypeString(types: List<String>): String {
    var res = "Type "
    for (index in types.indices) {
        res += SampleData.types.find { t -> t[0] == types[index] }!![1] + " "
        if (index < types.size - 1 && types.size > 1) {
            res += "et "
        }
    }
    return res
}

@Preview
@Composable
fun DataTablePreview() {
    BaseTable(base = SampleData.pokemons[0].base)
}