package com.example.wuhan_guide_helper.databaseUi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.wuhan_guide_helper.MyApplication
import com.example.wuhan_guide_helper.database.Review

import com.google.firebase.auth.FirebaseAuth

class ContextActivity : ComponentActivity() {


    private val reviewViewModel: ReviewViewModel by viewModels {
        ReviewViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ReviewApp(reviewViewModel = reviewViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ReviewApp(reviewViewModel: ReviewViewModel) {
    var screenState by remember { mutableStateOf<ScreenState>(ScreenState.List) }
    var selectedReview by remember { mutableStateOf<Review?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Review Manager",
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Left
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFB497BD),
                    titleContentColor = Color.White
                )
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AnimatedContent(targetState = screenState, transitionSpec = {
                    slideInHorizontally { fullWidth -> fullWidth } + fadeIn() with
                            slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut()
                }) { targetState ->
                    when (targetState) {
                        is ScreenState.List -> ReviewListScreen(
                            reviewViewModel = reviewViewModel,
                            onAddReviewClick = { screenState = ScreenState.Add },
                            onReviewClick = {
                                selectedReview = it
                                screenState = ScreenState.Detail
                            }
                        )
                        is ScreenState.Add -> AddReviewScreen(
                            reviewViewModel = reviewViewModel,
                            onBack = { screenState = ScreenState.List }
                        )
                        is ScreenState.Detail -> ReviewDetailScreen(
                            review = selectedReview!!,
                            onDelete = {
                                reviewViewModel.delete(selectedReview!!)
                                screenState = ScreenState.List
                            },
                            onChange = { updatedReview ->
                                reviewViewModel.update(updatedReview)
                                screenState = ScreenState.List
                            },
                            onBack = { screenState = ScreenState.List }
                        )
                    }
                }
            }
        }
    )
}

sealed class ScreenState {
    object List : ScreenState()
    object Add : ScreenState()
    object Detail : ScreenState()
}

@Composable
fun ReviewListScreen(
    reviewViewModel: ReviewViewModel,
    onAddReviewClick: () -> Unit,
    onReviewClick: (Review) -> Unit
) {
    val allReviews by reviewViewModel.allReviews.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(allReviews) { review ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clickable { onReviewClick(review) },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Location: ${review.location}",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Left
                        )
                        Text(
                            text = "Description: ${review.description}",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Left
                        )
                        Text(
                            text = "Name: ${review.name}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Left
                        )
                    }
                }
            }
        }

        Button(
            onClick = onAddReviewClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB497BD))
        ) {
            Text(
                "Add Review",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
    }
}

@Composable
fun AddReviewScreen(
    reviewViewModel: ReviewViewModel,
    onBack: () -> Unit
) {
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location", fontWeight = FontWeight.Bold) },
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description", fontWeight = FontWeight.Bold) },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser != null) {
                    reviewViewModel.insert(location, description)
                    onBack()
                } else {
                    Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB497BD)) // 按钮颜色改为 #B497BD
        ) {
            Text(
                "Save Review",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB497BD)) // 按钮颜色改为 #B497BD
        ) {
            Text(
                "Back",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
    }
}

@Composable
fun ReviewDetailScreen(
    review: Review,
    onDelete: () -> Unit,
    onChange: (Review) -> Unit,
    onBack: () -> Unit
) {
    var updatedLocation by remember { mutableStateOf(review.location) }
    var updatedDescription by remember { mutableStateOf(review.description) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Review Details",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left
        )

        // 地点输入框
        OutlinedTextField(
            value = updatedLocation,
            onValueChange = { updatedLocation = it },
            label = { Text("Location", fontWeight = FontWeight.Bold) },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = updatedDescription,
            onValueChange = { updatedDescription = it },
            label = { Text("Description", fontWeight = FontWeight.Bold) },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Name: ${review.name}",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left
        )

        Button(
            onClick = {
                val updatedReview = review.copy(
                    location = updatedLocation,
                    description = updatedDescription
                )
                onChange(updatedReview)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB497BD)) // 按钮颜色改为 #B497BD
        ) {
            Text(
                "Change",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        Button(
            onClick = onDelete,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000)) // 删除按钮颜色改为红色
        ) {
            Text(
                "Delete",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB497BD)) // 按钮颜色改为 #B497BD
        ) {
            Text(
                "Back",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
    }
}