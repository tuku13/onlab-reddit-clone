package hu.tuku13.onlab_reddit_clone.domain.model

enum class LikeValue(val value: Int) {
    Like(1), Dislike(-1), None(0)
}