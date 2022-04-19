package hu.tuku13.onlab_reddit_clone.domain.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import hu.tuku13.onlab_reddit_clone.ui.navigation.Route


class NavigationService {
    private val TAG = "NavigationService"
    var navController: NavHostController? = null

    private val routes: MutableSet<Route> = mutableSetOf()
    private var _currentRoute: MutableLiveData<Route> = MutableLiveData(Route.HomeRoute)
    val currentRoute: LiveData<Route>
        get() = _currentRoute

    fun navigate(route: Route) {
        _currentRoute.value?.let { routes += it }

        _currentRoute.value = route

        Log.d(TAG, "${route.navigation}\n${route.route}")

        navController?.navigate(route.route)
    }

    fun navigateWithPop(route: Route) {
        popBackStack()
        navigate(route)
    }

    fun popBackStack() {
        navController?.popBackStack()
    }

    fun onPopBackStack(navDestination: NavDestination) {
        val lastRoute = routes.find { it.navigation == navDestination.route }
        lastRoute?.let { _currentRoute.value = it }
    }

}