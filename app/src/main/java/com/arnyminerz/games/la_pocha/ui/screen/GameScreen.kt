package com.arnyminerz.games.la_pocha.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arnyminerz.games.la_pocha.R
import com.arnyminerz.games.la_pocha.game.GameInfo
import com.arnyminerz.games.la_pocha.game.GameProgress
import com.arnyminerz.games.la_pocha.viewmodel.MainViewModel

@Composable
@ExperimentalMaterial3Api
fun GameScreen(gameInfo: GameInfo, gameProgress: GameProgress, viewModel: MainViewModel) {
    var showingConfirmEndDialog by remember { mutableStateOf(false) }
    var showingDealCardsDialog by remember { mutableStateOf(false) }

    if (showingConfirmEndDialog)
        AlertDialog(
            onDismissRequest = { showingConfirmEndDialog = false },
            title = { Text(stringResource(R.string.dialog_end_game_title)) },
            text = { Text(stringResource(R.string.dialog_end_game_message)) },
            confirmButton = {
                Button(onClick = { viewModel.endGame() }) {
                    Text(stringResource(R.string.action_end))
                }
            },
            dismissButton = {
                Button(onClick = { showingConfirmEndDialog = false }) {
                    Text(stringResource(R.string.action_cancel))
                }
            },
        )
    if (showingDealCardsDialog)
        Dialog(
            onDismissRequest = { /*TODO*/ },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.dialog_deal_cards_title),
                )
                Text(
                    text = stringResource(R.string.dialog_deal_cards_message),
                )
            }
        }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(
                        onClick = { viewModel.endGame() },
                    ) { Icon(Icons.Rounded.Close, stringResource(R.string.image_desc_end_game)) }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        stringResource(
                            R.string.game_round,
                            gameProgress.round + 1,
                            gameInfo.numberOfRounds,
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Text("number of cards: ${gameProgress.numberOfCards(gameInfo)}. Number of players: ${gameInfo.numberOfPlayers}")
            Button(
                onClick = {
                    viewModel.nextRound()
                },
            ) {
                Text("Next round")
            }
        }
    }
}
