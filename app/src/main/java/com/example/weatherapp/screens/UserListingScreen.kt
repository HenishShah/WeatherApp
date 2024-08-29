package com.example.weatherapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.model.UserModel
import com.example.weatherapp.viewmodel.UserViewModel

@Composable
fun UserListingScreen(
    viewModel: UserViewModel,
    onAddUserClick: () -> Unit,
    onUserClick: () -> Unit
) {
    val users by viewModel.usersStateFlow.collectAsState()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Text("User Listing", modifier = Modifier.align(Alignment.Center))

                IconButton(onClick = onAddUserClick) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Back",
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
        },
        content = { paddingValues ->
            UserList(
                users = users,
                onUserClick = onUserClick,
                onDeleteUser = { user -> viewModel.deleteUser(user) },
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@Composable
fun UserList(
    users: List<UserModel>,
    onUserClick: () -> Unit,
    onDeleteUser: (UserModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(users, key = { user -> user.email }) { user ->
            SwipeToDismissUserItem(
                user = user,
                onUserClick = onUserClick,
                onDeleteUser = onDeleteUser
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDismissUserItem(
    user: UserModel,
    onUserClick: () -> Unit,
    onDeleteUser: (UserModel) -> Unit
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                onDeleteUser(user)
            }
            false
        }
    )

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.error)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Delete",
                    color = MaterialTheme.colorScheme.onError,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        },
        dismissContent = {
            ListItem(
                modifier = Modifier
                    .background(Color(0xFFF5F2D0))
                    .clickable { onUserClick() },
                headlineContent = { Text("${user.firstName} ${user.lastName} ") },
                supportingContent = { Text(user.email) },
            )
        }
    )
}

