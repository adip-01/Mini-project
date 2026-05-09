package com.adib0082.miniprojek.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.adib0082.miniprojek.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHapus(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: MainViewModel = viewModel(factory = ViewModelFactory(context))
    val deletedData by viewModel.deletedData.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Keranjang Sampah") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        if (deletedData.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Tidak ada data terhapus")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(deletedData) { data ->
                    ListItem(
                        headlineContent = { Text("${data.nilai} m/s") },
                        supportingContent = { Text(data.lokasi) },
                        trailingContent = {
                            IconButton(onClick = { viewModel.restore(data.id) }) {
                                Icon(Icons.Default.Restore, "Undo", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}