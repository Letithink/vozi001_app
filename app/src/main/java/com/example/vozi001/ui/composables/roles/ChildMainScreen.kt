package com.example.vozi001.ui.composables.roles


import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Mic

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import com.example.vozi001.ui.theme.VoziBlue
import com.example.vozi001.ui.theme.VoziWhite

import androidx.compose.material3.*

import androidx.navigation.compose.rememberNavController
import com.example.vozi001.navigation.BottomNavBar
import com.example.vozi001.navigation.NavGraph


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildMainScreen(
    onSignOut: () -> Unit,
    onStartRecording: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "V😊ZI",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = VoziBlue,
                    titleContentColor = VoziWhite,
                    navigationIconContentColor = VoziWhite,
                    actionIconContentColor = VoziWhite
                )
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onStartRecording,
                containerColor = VoziBlue,
                contentColor = VoziWhite
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Grabar audio"
                )
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            onSignOut = onSignOut,
            onStartRecording = onStartRecording,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
