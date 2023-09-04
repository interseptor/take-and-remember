package ru.evka.takeandremember

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.evka.takeandremember.compose.TakeAndRememberApp
import ru.evka.takeandremember.ui.TakeAndRememberTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            TakeAndRememberTheme {
                TakeAndRememberApp()
            }
        }
    }
}