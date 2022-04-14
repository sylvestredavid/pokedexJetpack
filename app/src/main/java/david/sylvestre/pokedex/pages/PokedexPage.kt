package david.sylvestre.pokedex.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import david.sylvestre.pokedex.composants.PokemonCard
import david.sylvestre.pokedex.data.SampleData
import david.sylvestre.pokedex.model.Pokemon
import david.sylvestre.pokedex.utils.TypeIcon
import kotlinx.coroutines.launch
import java.text.Normalizer


@Composable
fun PokedexPage(navController: NavController) {
    var searchInput by rememberSaveable { mutableStateOf("") }
    var filters by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }
    var drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Pokedex") },
                actions = {
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = {
                        scope.launch {
                            if (drawerState.isOpen)  drawerState.close() else drawerState.open()
                        }
                    }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { navController.navigate("pokemonForm") }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "edit"
                        )
                    }
                }
            )
        }
    ) {
        ModalDrawer(
            drawerState = drawerState,
            drawerContent = {
                FilterByType(onChange = {filters = it}, filters = filters.toMutableList())
            }
        ) {
            Column {
                SearchView(value = searchInput, onChange = { searchInput = it })
                LazyColumn {
                    items(filteredPokemons(searchInput, filters)) { pokemon ->
                        PokemonCard(pokemon = pokemon, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun FilterByType(onChange: (List<String>) -> Unit, filters: MutableList<String>) {
    LazyColumn {
        items(SampleData.types) { type ->
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(all = 5.dp)
                    .fillMaxWidth()
                    .padding(all = 5.dp)
                    .clickable {
                        if (filters.contains(type[0])) {
                            filters.removeAt(filters.indexOf(type[0]))
                        } else {
                            filters.add(type[0])
                        }
                        onChange(filters.toList())
                    }) {
                TypeIcon(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 5.dp), type = type[0]
                )
                Text(text = type[1],
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(end = 5.dp))
                if(filters.contains(type[0])) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                }
            }
        }
    }
}

@Composable
fun SearchView(value: String, onChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onChange,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        label = { Text("Chercher un pokemon") }
    )
}

@Preview
@Composable
fun FilterByTypePreview() {
    FilterByType(onChange = {}, filters = mutableListOf("Bug"))
}

/**
@Preview
@Composable
fun PagePreview() {
PokedexTheme {
PokedexPage()
}
}

@Preview
@Composable
fun PokemonCardPreview() {
PokedexTheme {
PokemonCard(pokemon = SampleData.pokemons[0])
}
}

@Preview
@Composable
fun SearchBarPreview() {
PokedexTheme {
SearchView(value = "essais", onChange = {})
}
}
 **/


fun filteredPokemons(search: String, filters: List<String>): List<Pokemon> {
    if (search.isEmpty()) {
        if(filters.isEmpty()) {
            return SampleData.pokemons
        }
        val res = mutableListOf<Pokemon>()
        filters.forEach { filter ->
            res += SampleData.pokemons.filter { pokemon -> pokemon.types.contains(filter) }
        }
        return res.toList()
    }
    val pokemons = SampleData.pokemons.filter { pokemon ->
        pokemon.name.lowercase().unaccent().contains(search.lowercase().unaccent())
    }
    if(filters.isEmpty()) {
        return pokemons
    }
    val res = mutableListOf<Pokemon>()
    filters.forEach { filter ->
        res += pokemons.filter { pokemon -> pokemon.types.contains(filter) }
    }
    return res.toList()
}

private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

fun CharSequence.unaccent(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}