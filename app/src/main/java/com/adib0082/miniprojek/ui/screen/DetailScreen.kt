package com.adib0082.miniprojek.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.adib0082.miniprojek.R
import com.adib0082.miniprojek.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nilai by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }
    var jenis by remember { mutableStateOf("Wind") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getData(id) ?: return@LaunchedEffect
        nilai = data.nilai.toString()
        lokasi = data.lokasi
        jenis = data.jenis
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_desc),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.title_add_sensor))
                    else
                        Text(text = stringResource(id = R.string.title_edit_sensor))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (nilai == "" || lokasi == "") {
                            Toast.makeText(context, R.string.error_empty_fields, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }

                        val nilaiDouble = nilai.toDoubleOrNull() ?: 0.0
                        if (id == null) {
                            viewModel.insert(nilaiDouble, jenis, lokasi)
                        } else {
                            viewModel.update(id, nilaiDouble, jenis, lokasi)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(id = R.string.save_desc),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormSensor(
            nilai = nilai,
            onNilaiChange = { nilai = it },
            lokasi = lokasi,
            onLokasiChange = { lokasi = it },
            jenis = jenis,
            onJenisChange = { jenis = it },
            modifier = Modifier.padding(padding)
        )

        if (id != null && showDialog) {
            DisplayAlertDialog(
                onDismissRequest = { showDialog = false },
                onConfirmation = {
                    showDialog = false
                    viewModel.delete(id)
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun FormSensor(
    nilai: String, onNilaiChange: (String) -> Unit,
    lokasi: String, onLokasiChange: (String) -> Unit,
    jenis: String, onJenisChange: (String) -> Unit,
    modifier: Modifier
) {
    val radioOptions = listOf("Wind", "Water")

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nilai,
            onValueChange = { onNilaiChange(it) },
            label = { Text(text = stringResource(id = R.string.label_speed)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lokasi,
            onValueChange = { onLokasiChange(it) },
            label = { Text(text = stringResource(id = R.string.label_location)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == jenis),
                                onClick = { onJenisChange(text) },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        RadioButton(
                            selected = (text == jenis),
                            onClick = null
                        )
                        Text(
                            text = if (text == "Wind") stringResource(R.string.sensor_type_wind) else stringResource(R.string.sensor_type_water),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = {expanded = true}) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.more_desc),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.delete_text))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(rememberNavController())
}
