package hu.tuku13.onlab_reddit_clone.service.navigation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController


class NavigationService {
    var navController: NavHostController? = null

    private val routes: MutableSet<Route> = mutableSetOf()
    private var _currentRoute: MutableLiveData<Route> = MutableLiveData(Route.HomeRoute)
    val currentRoute: LiveData<Route>
        get() = _currentRoute

    fun navigate(route: Route) {
        _currentRoute.value?.let { routes += it }
        _currentRoute.value = route

        Log.d("NavigationService", "${route.navigation}\n${route.route}")

        navController?.navigate(route.route)
    }

    fun popBackStack() {
        navController?.popBackStack()
    }

    fun onPopBackStack(navDestination: NavDestination) {
        val lastRoute = routes.find { it.navigation == navDestination.route }
        lastRoute?.let { _currentRoute.value = it }
    }

}