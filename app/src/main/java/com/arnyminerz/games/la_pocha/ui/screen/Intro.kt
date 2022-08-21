package com.arnyminerz.games.la_pocha.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnyminerz.games.la_pocha.R
import com.arnyminerz.games.la_pocha.ui.fonts.robotoSlabFamily
import com.arnyminerz.games.la_pocha.viewmodel.MainViewModel

@Composable
@ExperimentalMaterial3Api
fun IntroScreen(viewModel: MainViewModel) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(stringResource(R.string.action_start)) },
                icon = {
                    Icon(
                        Icons.Rounded.ChevronRight,
                        stringResource(R.string.image_desc_start_app),
                    )
                },
                onClick = { viewModel.markShownIntro() },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
        ) {
            Image(
                painter = painterResource(R.drawable.undraw_playing_cards_cywn),
                contentDescription = stringResource(R.string.image_desc_playing_cards),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, end = 48.dp, top = 64.dp),
            )
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                fontFamily = robotoSlabFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, end = 48.dp, top = 48.dp),
            )
            Text(
                stringResource(R.string.intro_message),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                fontFamily = robotoSlabFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp),
            )
        }
    }
}
