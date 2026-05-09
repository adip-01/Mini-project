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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.adib0082.miniprojek.R
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
                title = { Text(stringResource(R.string.title_recycle_bin)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_desc),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        if (deletedData.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text(stringResource(R.string.empty_recycle_bin))
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(deletedData) { data ->
                    ListItem(
                        headlineContent = { Text(stringResource(R.string.unit_ms, data.nilai)) },
                        supportingContent = { Text(data.lokasi) },
                        trailingContent = {
                            IconButton(onClick = { viewModel.restore(data.id) }) {
                                Icon(Icons.Default.Restore, stringResource(R.string.restore_desc), tint = Color(0xFF2196F3))
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}