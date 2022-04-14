package david.sylvestre.pokedex.composants

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import david.sylvestre.pokedex.model.Pokemon
import david.sylvestre.pokedex.utils.TypeIcon


@Composable
fun PokemonCard(pokemon: Pokemon, navController: NavController) {
    Surface(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("pokemon/${pokemon.id}") },
        elevation = 1.dp,
    ) {
        Row(modifier = Modifier.padding(all = 5.dp)) {
            Image(
                painter = rememberImagePainter(
                    data = pokemon.thumbnail
                ),
                contentDescription = "Android Logo",
                modifier = Modifier
                    .size(80.dp)
            )
            Column(Modifier.padding(start = 10.dp)) {
                Text(text = pokemon.name, style = MaterialTheme.typography.h4)
                Row {
                    for (type in pokemon.types) {
                        TypeIcon(
                            modifier = Modifier
                                .size(30.dp)
                                .padding(end = 5.dp), type = type
                        )
                    }
                }
            }
        }
    }
}