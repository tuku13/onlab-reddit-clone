package hu.tuku13.onlab_reddit_clone.ui.screen.post

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import hu.tuku13.onlab_reddit_clone.domain.model.LikeValue
import hu.tuku13.onlab_reddit_clone.service.navigation.NavigationService
import hu.tuku13.onlab_reddit_clone.ui.screen.conversation.MessageInputBar
import hu.tuku13.onlab_reddit_clone.ui.screen.home.Comment
import hu.tuku13.onlab_reddit_clone.ui.screen.home.LikeBar
import hu.tuku13.onlab_reddit_clone.ui.screen.home.PostTitleBar
import hu.tuku13.onlab_reddit_clone.ui.theme.Extended

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    postId: Long,
    viewModel: PostViewModel = hiltViewModel(),
    navigationService: NavigationService
) {
    val post = viewModel.post.observeAsState()
    val comments = viewModel.comments.observeAsState(emptyList())
    val selectedComment = viewModel.selectedComment.observeAsState()

    LaunchedEffect(viewModel) {
        viewModel.postId = postId
        viewModel.getPost()
        viewModel.getComments()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .width(360.dp)
                .fillMaxHeight()
                .weight(1.0f)
        ) {
            item {
                Card(
                    containerColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.padding(vertical = 8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    post.value?.let {
                        PostTitleBar(
                            post = it,
                            navigationService = navigationService,
                            onDelete = { toDelete ->
                                viewModel.deletePost(toDelete.postId)
                            }
                        )
                    }

                    GlideImage(
                        imageModel = post.value?.postImage,
                        modifier = Modifier.width(360.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    post.value?.let {
                        Text(
                            text = it.title,
                            maxLines = 2,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    post.value?.let {
                        Text(
                            text = it.text,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    post.value?.let {
                        Row(
                            modifier = Modifier
                                .height(70.dp)
                                .width(360.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            LikeBar(
                                likes = it.likes,
                                userOpinion = it.userOpinion,
                                like = {
                                    if (it.userOpinion == 1) {
                                        viewModel.likePost(LikeValue.None)
                                    } else {
                                        viewModel.likePost(LikeValue.Like)
                                    }
                                },
                                dislike = {
                                    if (it.userOpinion == -1) {
                                        viewModel.likePost(LikeValue.None)
                                    } else {
                                        viewModel.likePost(LikeValue.Dislike)
                                    }
                                }
                            )

                            Comment(
                                comments = it.comments,
                                userAlreadyCommented = it.userCommented,
                                onClick = {
                                    viewModel.getComments()
                                }
                            )
                        }
                    }
                }
            }

            items(comments.value) { comment ->
                CommentWidget(
                    comment = comment,
                    selectedComment = selectedComment.value,
                    onClick = {
                        if (selectedComment.value == it) {
                            viewModel.selectParent(it)
                        } else {
                            viewModel.selectComment(it)
                        }
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .wrapContentHeight()
        ) {
            selectedComment.value?.let {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Extended.surface2)
                        .padding(start = 24.dp, top = 16.dp)
                        .clickable { viewModel.selectComment(null) },
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "X - Replying to ${it.user.name}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            MessageInputBar(
                onSubmit = { text ->
                    viewModel.createComment(text)
                }
            )
        }
    }
}