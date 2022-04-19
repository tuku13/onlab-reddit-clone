package hu.tuku13.onlab_reddit_clone.util

fun formatElapsedTime(time: Long): String {
    val now = System.currentTimeMillis()
    val elapsed = now - time

    val elapsedInMinutes = elapsed / (1000 * 60)

    return if (elapsedInMinutes <= 60) {
        "${elapsedInMinutes}m"
    } else if (elapsedInMinutes > 60 && elapsedInMinutes <= 24 * 60) {
        "${elapsedInMinutes / 60}h"
    } else {
        "${elapsedInMinutes / (60 * 24)}d"
    }
}