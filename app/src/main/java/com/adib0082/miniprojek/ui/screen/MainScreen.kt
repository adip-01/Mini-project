package com.adib0082.miniprojek.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.adib0082.miniprojek.R
import com.adib0082.miniprojek.model.SensorKecepatan
import com.adib0082.miniprojek.navigation.Screen
import com.adib0082.miniprojek.util.SettingsDataStore
import com.adib0082.miniprojek.util.ViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nama Aplikasi", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3), // Biru yang sama
                ),
                actions = {
                    IconButton(onClick = { navController.navigate("recycleBin") }) {
                        Icon(Icons.Default.DeleteSweep, "Sampah", tint = Color.White)
                    }
                    IconButton(onClick = {
                        scope.launch {
                            dataStore.saveLayout(!showList)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                if (showList) R.drawable.baseline_view_list_24
                                else R.drawable.baseline_grid_view_24
                            ),
                            contentDescription = "Ganti Layout",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Tambah Data",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        ScreenContent(showList, Modifier.padding(innerPadding), navController)
    }
}

@Composable
fun ScreenContent(showList: Boolean, modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val detailViewModel: DetailViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var selectedId by remember { mutableStateOf<Long?>(null) }

    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Daftar Kosong")
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) {
                    ListItem(
                        it, 
                        onClick = {
                            navController.navigate(Screen.FormUbah.withId(it.id))
                        },
                        onDelete = {
                            selectedId = it.id
                            showDialog = true
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
        else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)

            ) {
                items(data) {
                    GridItem(
                        it, 
                        onClick = {
                            navController.navigate(Screen.FormUbah.withId(it.id))
                        },
                        onDelete = {
                            selectedId = it.id
                            showDialog = true
                        }
                    )
                }
            }
        }
    }

    if (showDialog) {
        DisplayAlertDialog(
            onDismissRequest = { showDialog = false },
            onConfirmation = {
                selectedId?.let { detailViewModel.delete(it) }
                showDialog = false
            }
        )
    }
}

@Composable
fun ListItem(data: SensorKecepatan, onClick: () -> Unit, onDelete: () -> Unit) {
    val locale = LocalConfiguration.current.locales[0]
    val formatter = remember(locale) { SimpleDateFormat("dd/MM/yyyy HH:mm", locale) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "${data.nilai} m/s",
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Jenis: ${data.jenis} | Lokasi: ${data.lokasi}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(text = formatter.format(Date(data.waktu)), style = MaterialTheme.typography.labelSmall)
        }
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun GridItem(data: SensorKecepatan, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, DividerDefaults.color)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "${data.nilai} m/s",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Text(
                text = "Jenis: ${data.jenis}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Lokasi: ${data.lokasi}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(rememberNavController())
}
