package david.sylvestre.pokedex.pages

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
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
import david.sylvestre.pokedex.utils.TypeIcon


@Composable
fun PokemonFormPage(navController: NavController, id: String? = null) {
    var pokemon: Pokemon = if(id != null) SampleData.pokemons.find{it.id == id.toInt()}!!
    else Pokemon(0, "", "", emptyList(), PokemonBase(0,0,0,0,0,0), 0, emptyList(), "", "", "")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if(pokemon.name.isBlank()) "Créer pokémon" else "Modifier ${pokemon.name}")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        PokemonFormBody(pokemon = pokemon)
    }
}

@Composable
fun PokemonFormBody(pokemon: Pokemon) {
    var name by rememberSaveable {mutableStateOf(pokemon.name)}
    var description by rememberSaveable {mutableStateOf(pokemon.description)}
    var types by rememberSaveable { mutableStateOf<List<String>>(pokemon.types) }
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(label = {Text(text = "Nom")},value = name, onValueChange = {name = it}, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp))
        TextField(label = {Text(text = "Description")},value = description, onValueChange = {description = it}, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp))
        TypesSelect(onChange = {types = it}, types = types.toMutableList())
    }
}

@Composable
fun TypesSelect(types: MutableList<String>, onChange: (List<String>) -> Unit) {
    var openOptions by rememberSaveable{mutableStateOf(false)}

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(all = 5.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .clickable { openOptions = !openOptions }) {
            Text(text = "Types")
            Row(modifier = Modifier.fillMaxWidth()){
                for (type in types) {
                    Text(text = "${SampleData.types.find { it[0] == type }!![1]} ")
                }
            }
        }

        if(openOptions) {
            LazyRow(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                items(SampleData.types) { type ->
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(all = 5.dp)
                            .fillMaxWidth()
                            .padding(all = 5.dp)
                            .clickable {
                                if (types.contains(type[0])) {
                                    types.removeAt(types.indexOf(type[0]))
                                } else {
                                    if (types.size < 2) {
                                        types.add(type[0])
                                    }
                                }
                                onChange(types.toList())
                            }) {
                        TypeIcon(
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 5.dp), type = type[0]
                        )
                        Text(text = type[1])
                        if(types.contains(type[0])) {
                            Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                        }
                    }
                }
            }
        }
    }
}