package com.arnyminerz.games.la_pocha

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arnyminerz.games.la_pocha.ui.screen.CreateGameScreen
import com.arnyminerz.games.la_pocha.ui.screen.IntroScreen
import com.arnyminerz.games.la_pocha.ui.theme.LaPochaTheme
import com.arnyminerz.games.la_pocha.viewmodel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaPochaTheme {
                val shownIntro by viewModel
                    .shownIntro
                    .collectAsState(initial = false)
                val gameInfo by viewModel
                    .gameInfo
                    .collectAsState(initial = null)

                AnimatedVisibility(visible = !shownIntro) {
                    IntroScreen(viewModel)
                }
                AnimatedVisibility(visible = shownIntro && gameInfo == null) {
                    CreateGameScreen(viewModel)
                }
                AnimatedVisibility(visible = gameInfo != null) {
                    Text(text = "Game")
                }
            }
        }
    }
}
