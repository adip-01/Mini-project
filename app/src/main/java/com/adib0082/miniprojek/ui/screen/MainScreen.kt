package com.adib0082.miniprojek.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.adib0082.miniprojek.R
import com.adib0082.miniprojek.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.About.route)
                    })  {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(id = R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var totalJam by rememberSaveable { mutableStateOf("") }
    var totalJamError by rememberSaveable { mutableStateOf(false) }
    var jumlahSesi by rememberSaveable { mutableStateOf("") }
    var jumlahSesiError by rememberSaveable { mutableStateOf(false) }
    val hariOptions = listOf(
        stringResource(id = R.string.hari_kerja),
        stringResource(id = R.string.hari_libur)
    )
    var selectedHari by rememberSaveable { mutableStateOf(hariOptions[0]) }
    val kuliahOptions = listOf(
        stringResource(id = R.string.ada_kuliah),
        stringResource(id = R.string.tidak_ada_kuliah)
    )
    var selectedKuliah by rememberSaveable { mutableStateOf(kuliahOptions[0]) }
    var statusResId by rememberSaveable { mutableIntStateOf(0) }
    val context = LocalContext.current

    Column (
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.game_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        // Input Total Jam
        OutlinedTextField(
            value = totalJam,
            onValueChange = { totalJam = it },
            label = { Text(text = stringResource(R.string.total_jam)) },
            trailingIcon = { IconPicker(totalJamError, "jam") },
            supportingText = { ErrorHint(totalJamError) },
            isError = totalJamError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = jumlahSesi,
            onValueChange = { jumlahSesi = it },
            label = { Text(text = stringResource(R.string.jumlah_sesi)) },
            trailingIcon = { IconPicker(jumlahSesiError, "kali") },
            supportingText = { ErrorHint(jumlahSesiError) },
            isError = jumlahSesiError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = stringResource(R.string.pilih_hari), style = MaterialTheme.typography.labelLarge, modifier = Modifier.align(Alignment.Start))
        Row(
            modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            hariOptions.forEach { text ->
                SelectableOption(
                    label = text,
                    isSelected = selectedHari == text,
                    modifier = Modifier
                        .selectable(
                            selected = selectedHari == text,
                            onClick = { selectedHari = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(12.dp)
                )
            }
        }

        Text(text = stringResource(R.string.status_kuliah), style = MaterialTheme.typography.labelLarge, modifier = Modifier.align(Alignment.Start))
        Row(
            modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            kuliahOptions.forEach { text ->
                SelectableOption(
                    label = text,
                    isSelected = selectedKuliah == text,
                    modifier = Modifier
                        .selectable(
                            selected = selectedKuliah == text,
                            onClick = { selectedKuliah = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(12.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    totalJamError = (totalJam == "" || totalJam.toFloatOrNull() == null)
                    jumlahSesiError = (jumlahSesi == "" || jumlahSesi.toIntOrNull() == null)
                    if (totalJamError || jumlahSesiError) return@Button

                    statusResId = checkStatus(
                        jam = totalJam.toFloat(),
                        sesi = jumlahSesi.toInt(),
                        isWeekend = selectedHari == hariOptions[1],
                        hasCollege = selectedKuliah == kuliahOptions[0]
                    )
                },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.hitung))
            }

            OutlinedButton(
                onClick = {
                    totalJam = ""
                    jumlahSesi = ""
                    totalJamError = false
                    jumlahSesiError = false
                    selectedHari = hariOptions[0]
                    selectedKuliah = kuliahOptions[0]
                    statusResId = 0
                },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.reset))
            }
        }

        if (statusResId != 0) {
            val statusText = stringResource(statusResId)
            val imageRes =
                if (statusResId == R.string.status_oke) {
                    R.drawable.download
                } else {
                    R.drawable.download1
                }
            val message = stringResource(
                R.string.bagikan_template,
                totalJam,
                jumlahSesi,
                selectedHari,
                selectedKuliah,
                statusText
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
            )
            
            Text(
                text = statusText,
                style = MaterialTheme.typography.displayMedium,
                color = if (statusResId == R.string.status_over || statusResId == R.string.status_sesi_banyak) MaterialTheme.colorScheme.error else Color(0xFF4CAF50),
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = stringResource(R.string.gambar_status, statusText),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(200.dp)
            )

            Button(
                onClick = { shareData(context, message) },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.bagikan))
            }
        }
    }
}

@Composable
fun SelectableOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

private fun checkStatus(jam: Float, sesi: Int, isWeekend: Boolean, hasCollege: Boolean): Int {
    if (hasCollege) {
        if (!isWeekend && sesi > 3) return R.string.status_sesi_banyak
        if (isWeekend && sesi >= 6) return R.string.status_sesi_banyak
    }
    
    val limit = when {
        isWeekend && hasCollege -> 6f
        isWeekend && !hasCollege -> 8f
        !isWeekend && hasCollege -> 4f
        else -> 6f
    }

    return if (jam > limit) R.string.status_over else R.string.status_oke
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean){
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    MainScreen(rememberNavController())
}