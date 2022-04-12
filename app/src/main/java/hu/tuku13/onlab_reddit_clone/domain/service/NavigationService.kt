package hu.tuku13.onlab_reddit_clone.domain.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import hu.tuku13.onlab_reddit_clone.ui.navigation.Route

class NavigationService {
    var navController: NavHostController? = null

    private val routes: MutableSet<Route> = mutableSetOf()
    private var _currentRoute: MutableLiveData<Route> = MutableLiveData(Route.HomeRoute)
    val currentRoute: LiveData<Route>
        get() = _currentRoute

    fun navigateTo(route: Route) {
        _currentRoute.value?.let { routes += it }

        _currentRoute.value = route
        navController?.navigate(route.route)
    }

    fun popBackStack() {
        navController?.let { it.popBackStack() }
    }

    fun onPopBackStack(navDestination: NavDestination) {
        val lastRoute = routes.find { it.navigation == navDestination.route }
        lastRoute?.let { _currentRoute.value = it }
    }

}