package ru.evka.takeandremember.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.evka.takeandremember.ui.TakeAndRememberTheme

@Composable
fun TakeAndRememberApp(
    data: List<String> = ArrayList()
) {
    LazyColumn {
        items(data) { message ->
            Text(
                text = "::$message",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
private fun TakeAndRememberAppPreview() {
    TakeAndRememberTheme {
        TakeAndRememberApp(
            data = List(10) { "Item #$it" }
        )
    }
}