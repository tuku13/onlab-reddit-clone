package hu.tuku13.onlab_reddit_clone.ui.scaffold

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import hu.tuku13.onlab_reddit_clone.service.navigation.NavigationService
import hu.tuku13.onlab_reddit_clone.service.navigation.Route

@Composable
fun BottomBar(
    routes: List<Route>,
    navigationService: NavigationService
) {

    NavigationBar {
        routes.forEach { route ->
            val selected = route.route == navigationService.currentRoute.value?.navigation

            NavigationBarItem(
                selected = selected,
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(),
                label = {
                    Text(
                        text = route.title,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                onClick = { navigationService.navigate(route) },
                icon = {
                    Icon(
                        imageVector = route.icon,
                        contentDescription = route.title,
                    )
                }
            )
        }
    }
}