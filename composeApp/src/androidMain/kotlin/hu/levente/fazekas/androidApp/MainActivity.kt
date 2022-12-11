package hu.levente.fazekas.androidApp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.eszkozkolcsonzo.presentation.navigation.NavDrawerItem
import hu.levente.fazekas.androidApp.presentation.navigation.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()

            Scaffold(
                scaffoldState = scaffoldState,
                topBar = { Topbar(scope = scope, scaffoldState = scaffoldState) },
                drawerContent = {
                    Drawer(
                        scope = scope,
                        scaffoldState = scaffoldState,
                        navController = navController
                    )
                }
            ) { values ->
                values
                Navigation(navController = navController)
            }
        }
    }
}
@Composable
fun Topbar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = { Text(text = "EquipmentRental", fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "Menu icon")
            }
        }
    )
}

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController,
    //viewModel: MainViewModel = hiltViewModel()
) {
    val items = mutableListOf<NavDrawerItem>()
    //if (MainViewModel.state.token == null) {
        items.addAll(
            listOf(
                NavDrawerItem.Registration,
                NavDrawerItem.Login
            )
        )
    //} else {
        items.addAll(
            listOf(
                NavDrawerItem.Devices,
                NavDrawerItem.ReservationList
            )
        )
        //if (viewModel.isAdmin()) {
            items.addAll(
                listOf(
                    NavDrawerItem.Lease,
                    NavDrawerItem.Drawback,
                    NavDrawerItem.CreateDevice
                )
            )
        //}
        items.add(
            NavDrawerItem.Logout
        )
    //}


    Column {
        /*if (MainViewModel.state.token != null) {
            DropDown(
                text = MainViewModel.state.name ?: ""
            ) {
                QRCodeView(str = QRCodeSchema.User + MainViewModel.state.id)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }*/

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            DrawerItem(item = item, selected = currentRoute == item.route, onItemclick = {
                Log.d("Navigation", item.route)
                navController.navigate(item.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                // Close drawer
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        }


    }
}

@Composable
fun DrawerItem(item: NavDrawerItem, selected: Boolean, onItemclick: (NavDrawerItem) -> Unit) {
    val background = if (selected) Color.LightGray else Color.Transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemclick(item) }
            .height(40.dp)
            .background(background)
            .padding(start = 10.dp)
    ) {
        Text(
            text = item.title,
            fontSize = 18.sp
        )
    }
}

@Composable
fun DropDown(
    text: String,
    modifier: Modifier = Modifier,
    initiallyOpened: Boolean = false,
    content: @Composable () -> Unit
) {
    var isOpen by remember {
        mutableStateOf(initiallyOpened)
    }
    val alpha = animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    val rotateX = animateFloatAsState(
        targetValue = if (isOpen) 0f else -90f,
        animationSpec = tween(
            durationMillis = 300
        )
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isOpen = !isOpen
                }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 0f)
                    rotationX = rotateX.value
                }
                .alpha(alpha.value)
        ) {
            content()
        }
    }
}