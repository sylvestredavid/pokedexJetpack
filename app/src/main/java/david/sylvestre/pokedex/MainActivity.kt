package david.sylvestre.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import david.sylvestre.pokedex.data.SampleData
import david.sylvestre.pokedex.model.Pokemon
import david.sylvestre.pokedex.pages.PokedexPage
import david.sylvestre.pokedex.pages.PokemonFormPage
import david.sylvestre.pokedex.pages.PokemonPage
import david.sylvestre.pokedex.ui.theme.PokedexTheme
import david.sylvestre.pokedex.utils.TypeIcon
import david.sylvestre.pokedex.utils.getTypeIcon
import kotlinx.coroutines.launch
import java.text.Normalizer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "pokedex"
                ) {
                    composable("pokedex") { PokedexPage(navController = navController) }
                    composable("pokemonForm") { PokemonFormPage(navController = navController) }
                    composable("pokemon/{id}") { backStackEntry ->
                        PokemonPage(navController, backStackEntry.arguments?.getString("id"))
                    }
                    composable("pokemonForm/{id}") { backStackEntry ->
                        PokemonFormPage(navController, backStackEntry.arguments?.getString("id"))
                    }
                }
            }
        }
    }
}



