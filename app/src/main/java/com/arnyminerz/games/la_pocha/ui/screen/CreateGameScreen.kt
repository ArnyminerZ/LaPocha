package com.arnyminerz.games.la_pocha.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Filter3
import androidx.compose.material.icons.rounded.Filter4
import androidx.compose.material.icons.rounded.Filter5
import androidx.compose.material.icons.rounded.Filter6
import androidx.compose.material.icons.rounded.Filter7
import androidx.compose.material.icons.rounded.Filter8
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material.icons.rounded.RemoveCircle
import androidx.compose.material.icons.rounded.Tag
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arnyminerz.games.la_pocha.R
import com.arnyminerz.games.la_pocha.game.GameInfo.Companion.cardsNumber
import com.arnyminerz.games.la_pocha.game.Player
import com.arnyminerz.games.la_pocha.ui.fonts.barlowFamily
import com.arnyminerz.games.la_pocha.ui.fonts.robotoSlabFamily
import com.arnyminerz.games.la_pocha.ui.theme.NoRippleTheme
import com.arnyminerz.games.la_pocha.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

private const val UP_AND_DOWN_DEFAULT = true
private val PLAYERS_LIST_DEFAULT = arrayOf(Player(""), Player(""), Player(""))

@Composable
@ExperimentalPagerApi
@ExperimentalMaterial3Api
fun CreateGameScreen(viewModel: MainViewModel) {
    val focusManager = LocalFocusManager.current

    val playersList = remember { mutableStateListOf(*PLAYERS_LIST_DEFAULT) }
    var gameRules by remember { mutableStateOf(GAME_RULES_DEFAULT) }

    val focusRequesters = remember {
        mutableStateListOf(FocusRequester(), FocusRequester(), FocusRequester())
    }

    var showingUpAndDownDialog by remember { mutableStateOf(false) }

    if (showingUpAndDownDialog)
        AlertDialog(
            onDismissRequest = { showingUpAndDownDialog = false },
            title = { Text(stringResource(R.string.dialog_up_and_down_title)) },
            text = { Text(stringResource(R.string.dialog_up_and_down_message)) },
            confirmButton = {
                Button(onClick = { showingUpAndDownDialog = false }) {
                    Text(stringResource(R.string.action_close))
                }
            },
        )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            val disabled = playersList.size == 7 || playersList.any { it.name.trim().length < 3 }
            CompositionLocalProvider(
                LocalRippleTheme provides if (disabled) NoRippleTheme else LocalRippleTheme.current
            ) {
                ExtendedFloatingActionButton(
                    text = { Text(stringResource(R.string.action_start)) },
                    icon = {
                        Icon(
                            Icons.Rounded.ChevronRight,
                            stringResource(R.string.image_desc_start_app),
                        )
                    },
                    containerColor = if (disabled) Gray else MaterialTheme.colorScheme.tertiary,
                    contentColor = if (disabled) DarkGray else MaterialTheme.colorScheme.onTertiary,
                    onClick = {
                        if (disabled)
                            return@ExtendedFloatingActionButton
                        viewModel
                            .startGame(playersList, upAndDownEnabled)
                            .invokeOnCompletion {
                                playersList.clear()
                                playersList.addAll(PLAYERS_LIST_DEFAULT)

                                focusRequesters.clear()
                                focusRequesters.addAll(
                                    listOf(FocusRequester(), FocusRequester(), FocusRequester())
                                )
                            }
                    },
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    val numberOfPlayers = playersList.size
                    val cardsNumber = cardsNumber(numberOfPlayers)

                    Text(
                        text = stringResource(R.string.new_game_title),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        fontFamily = robotoSlabFamily,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.new_game_players_list),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            fontFamily = barlowFamily,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .weight(1f),
                        )
                        OutlinedIconButton(
                            onClick = {
                                focusRequesters.add(FocusRequester())
                                playersList.add(Player(""))
                            },
                            enabled = numberOfPlayers < 8,
                        ) {
                            Icon(
                                Icons.Rounded.Add,
                                stringResource(R.string.image_desc_players_add),
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        playersList.forEachIndexed { index, player ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(50),
                                        )
                                        .width(50.dp),
                                ) {
                                    Text(
                                        text = player.tag,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 4.dp),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        letterSpacing = 2.sp,
                                    )
                                }
                                TextField(
                                    value = player.name,
                                    onValueChange = {
                                        playersList[index] = player.copy(name = it)
                                    },
                                    placeholder = {
                                        Text(stringResource(R.string.new_game_player_name))
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .focusRequester(focusRequesters[index]),
                                    singleLine = true,
                                    maxLines = 1,
                                    colors = TextFieldDefaults.textFieldColors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onNext = {
                                            focusRequesters[index + 1].requestFocus()
                                        },
                                        onDone = {
                                            focusManager.clearFocus()
                                        }
                                    ),
                                    keyboardOptions = KeyboardOptions(
                                        imeAction = if (focusRequesters.getOrNull(index + 1) != null)
                                            ImeAction.Next
                                        else
                                            ImeAction.Done,
                                    ),
                                )
                                TextButton(
                                    onClick = {
                                        playersList.remove(player)
                                        focusRequesters.removeAt(index)
                                    },
                                    enabled = index > 2,
                                ) {
                                    Icon(
                                        Icons.Rounded.RemoveCircle,
                                        stringResource(R.string.image_desc_players_remove),
                                    )
                                }
                            }
                        }
                    }
                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                stringResource(
                                    R.string.new_game_players_number,
                                    numberOfPlayers,
                                )
                            )
                        },
                        leadingIcon = {
                            Icon(
                                when (numberOfPlayers) {
                                    3 -> Icons.Rounded.Filter3
                                    4 -> Icons.Rounded.Filter4
                                    5 -> Icons.Rounded.Filter5
                                    6 -> Icons.Rounded.Filter6
                                    7 -> Icons.Rounded.Filter7
                                    8 -> Icons.Rounded.Filter8
                                    else -> Icons.Rounded.Tag
                                },
                                stringResource(R.string.image_desc_players_number),
                            )
                        },
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                    Text(
                        if (numberOfPlayers == 7)
                            stringResource(R.string.new_game_cards_7)
                        else stringResource(
                            R.string.new_game_cards,
                            numberOfPlayers,
                            cardsNumber,
                            cardsNumber / numberOfPlayers,
                        ) + when (cardsNumber) {
                            48 -> stringResource(R.string.new_game_cards_48)
                            36 -> stringResource(R.string.new_game_cards_remove_2s)
                            else -> ""
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        FilterChip(
                            selected = gameRules.upAndDown,
                            onClick = { gameRules = gameRules.copy(!gameRules.upAndDown) },
                            leadingIcon = {
                                if (gameRules.upAndDown)
                                    Icon(
                                        Icons.Rounded.Check,
                                        stringResource(R.string.image_desc_up_and_down),
                                    )
                                else
                                    Icon(
                                        Icons.Rounded.Close,
                                        stringResource(R.string.image_desc_up_and_down),
                                    )
                            },
                            label = {
                                Text(text = stringResource(R.string.new_game_up_and_down))
                            },
                        )
                        Box(
                            modifier = Modifier
                                .padding(start = 2.dp)
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.onSurfaceVariant,
                                    RoundedCornerShape(6.dp),
                                )
                                .clickable { showingUpAndDownDialog = true },
                        ) {
                            Icon(
                                Icons.Rounded.QuestionMark,
                                stringResource(R.string.image_desc_help),
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(2.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}
