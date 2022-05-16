package hu.tuku13.onlab_reddit_clone.network.model

data class RegisterForm(
    var username: String,
    var emailAddress: String,
    var password: String,
    var confirmPassword: String,
)
