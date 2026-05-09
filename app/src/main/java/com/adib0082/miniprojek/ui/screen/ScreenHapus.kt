package com.adib0082.miniprojek.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White // Ikon panah jadi putih
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3), // Warna Biru
                    titleContentColor = Color.White      // Tulisan jadi Putih
                )
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
                                Icon(Icons.Default.Restore, "Undo", tint = Color(0xFF2196F3))
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}