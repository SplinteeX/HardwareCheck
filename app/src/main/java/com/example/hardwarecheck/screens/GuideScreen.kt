package com.example.hardwarecheck.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hardwarecheck.R
import com.example.hardwarecheck.utils.PreferenceHelper
import kotlinx.coroutines.launch

@Composable
fun GuideScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val pages = listOf(
        TutorialPage(
            title = stringResource(id = R.string.guide_discover_title),
            description = stringResource(id = R.string.guide_discover_description),
            icon = R.drawable.ic_device
        ),
        TutorialPage(
            title = stringResource(id = R.string.guide_test_title),
            description = stringResource(id = R.string.guide_test_description),
            icon = R.drawable.ic_camera
        ),
        TutorialPage(
            title = stringResource(id = R.string.guide_enhance_title),
            description = stringResource(id = R.string.guide_enhance_description),
            icon = R.drawable.ic_magic
        ),
        TutorialPage(
            title = stringResource(id = R.string.guide_control_title),
            description = stringResource(id = R.string.guide_control_description),
            icon = R.drawable.build
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onFinish) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clear),
                    contentDescription = stringResource(id = R.string.guide_exit),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            TutorialPageContent(page = pages[page])
        }

        if (pagerState.currentPage == 3) {
            var saveDataEnabled by remember {
                mutableStateOf(PreferenceHelper.isSaveDataEnabled(context))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(id = R.string.guide_save_data))
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = saveDataEnabled,
                    onCheckedChange = {
                        saveDataEnabled = it
                        PreferenceHelper.setSaveDataEnabled(context, it)
                    }
                )
            }
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
                Text(stringResource(id = R.string.guide_back))
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pages.size) { index ->
                    val color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else Color.LightGray
                    val size = if (pagerState.currentPage == index) 10.dp else 8.dp

                    Box(
                        modifier = Modifier
                            .size(size)
                            .background(color = color, shape = CircleShape)
                    )
                }
            }

            TextButton(
                onClick = {
                    if (pagerState.currentPage < pages.size - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onFinish()
                    }
                }
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.size - 1) {
                        stringResource(id = R.string.guide_finish)
                    } else {
                        stringResource(id = R.string.guide_next)
                    }
                )
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
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = page.icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title
            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 17.sp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}