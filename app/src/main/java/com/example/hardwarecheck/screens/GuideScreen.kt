package com.example.hardwarecheck.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import com.example.hardwarecheck.R

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GuideScreen() {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val pages = listOf(
        TutorialPage(
            title = "Discover your phone specifications",
            description = "Uncover the full potential of your device. Instantly view detailed information about your phone’s hardware.",
            icon = R.drawable.ic_device
        ),
        TutorialPage(
            title = "Test your camera and sensors",
            description = "Check if everything’s working as it should. Easily test your phone’s camera and built-in sensors.",
            icon = R.drawable.ic_camera
        ),
        TutorialPage(
            title = "Enhance the app by sharing your phone details",
            description = "Share your device information to unlock additional features and personalize your experience.",
            icon = R.drawable.ic_magic
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            TutorialPageContent(page = pages[page])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    if (pagerState.currentPage > 0) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                },
                enabled = pagerState.currentPage > 0
            ) {
                Text("Back")
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color.Blue,
                inactiveColor = Color.LightGray
            )

            TextButton(
                onClick = {
                    if (pagerState.currentPage < pages.size - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }
            ) {
                Text("Next")
            }
        }
    }
}

data class TutorialPage(
    val title: String,
    val description: String,
    val icon: Int
)

@Composable
fun TutorialPageContent(page: TutorialPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 2.dp,
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 24.dp)
        ) {
            Icon(
                painter = painterResource(id = page.icon),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            )
        }

        Text(
            text = page.title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = page.description,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}
