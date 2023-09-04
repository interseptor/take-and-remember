package ru.evka.takeandremember

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import ru.evka.takeandremember.compose.TakeAndRememberApp
import ru.evka.takeandremember.ui.TakeAndRememberTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val data: List<String> = List(10) { "Item #$it" }
        setContent {
            TakeAndRememberTheme {
                TakeAndRememberApp(
                    data = data
                )
            }
        }
    }
}