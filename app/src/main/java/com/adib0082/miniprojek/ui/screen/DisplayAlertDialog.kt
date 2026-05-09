package com.adib0082.miniprojek.ui.screen

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DisplayAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        text = { Text(text = "Apakah Anda yakin ingin menghapus data ini?")},
        confirmButton = {
            TextButton(onClick = { onConfirmation()}) {
                Text(text = "Hapus")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest()}) {
                Text(text = "Batal")
            }
        },
        onDismissRequest = { onDismissRequest()}
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogPreview() {
    DisplayAlertDialog(
        onDismissRequest = { },
        onConfirmation = { }
    )
}
