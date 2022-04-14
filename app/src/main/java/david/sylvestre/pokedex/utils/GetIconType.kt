package david.sylvestre.pokedex.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import david.sylvestre.pokedex.R

@Composable
fun TypeIcon(modifier: Modifier, type: String) {
    Image(
        painter = getTypeIcon(type = type),
        contentDescription = "Contact profile picture",
        modifier = modifier
    )
}

@Composable
fun getTypeIcon(type: String): Painter {
    return when(type.lowercase()) {
        "bug" -> painterResource(R.drawable.bug)
        "dark" -> painterResource(R.drawable.dark)
        "dragon" -> painterResource(R.drawable.dragon)
        "electric" -> painterResource(R.drawable.electric)
        "fairy" -> painterResource(R.drawable.fairy)
        "fighting" -> painterResource(R.drawable.fighting)
        "fire" -> painterResource(R.drawable.fire)
        "flying" -> painterResource(R.drawable.flying)
        "ghost" -> painterResource(R.drawable.ghost)
        "grass" -> painterResource(R.drawable.grass)
        "ground" -> painterResource(R.drawable.ground)
        "ice" -> painterResource(R.drawable.ice)
        "normal" -> painterResource(R.drawable.normal)
        "poison" -> painterResource(R.drawable.poison)
        "psychic" -> painterResource(R.drawable.psychic)
        "rock" -> painterResource(R.drawable.rock)
        "steel" -> painterResource(R.drawable.steel)
        "water" -> painterResource(R.drawable.water)
        else -> painterResource(R.drawable.ic_launcher_foreground)
    }
}