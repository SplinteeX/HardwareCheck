package com.example.hardwarecheck.screens

import android.Manifest
import android.graphics.ImageFormat
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.view.PreviewView
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.CameraController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import java.nio.ByteBuffer

@Composable
fun CameraScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // State for brightness value
    val brightness = remember { mutableStateOf(0f) }

    // Permission state
    val hasCameraPermission = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission.value = isGranted
            if (!isGranted) {
                Log.w("CameraScreen", "Camera permission denied")
            }
        }
    )

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            // Only enable analysis use case
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)

            // Set up image analysis for brightness calculation
            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                BrightnessAnalyzer { luminance ->
                    brightness.value = luminance
                }
            )
        }
    }

    // Request permission when composable is first launched
    LaunchedEffect(Unit) {
        if (!hasCameraPermission.value) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Camera Analysis",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            // Display brightness value
            Text(
                text = "Brightness: ${"%.2f".format(brightness.value)}%",
                fontSize = 16.sp
            )
        }

        if (hasCameraPermission.value) {
            // Show camera preview
            AndroidView(
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        controller = cameraController
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            // Bind controller to lifecycle
            LaunchedEffect(Unit) {
                cameraController.bindToLifecycle(lifecycleOwner)
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("Camera permission required to use this feature")
            }
        }
    }
}

class BrightnessAnalyzer(
    private val onLuminanceCalculated: (Float) -> Unit
) : ImageAnalysis.Analyzer {
    override fun analyze(image: ImageProxy) {
        val luminance = calculateLuminance(image)
        onLuminanceCalculated(luminance)
        image.close()
    }

    private fun calculateLuminance(image: ImageProxy): Float {
        if (image.format != ImageFormat.YUV_420_888) {
            return 0f
        }

        val yBuffer = image.planes[0].buffer
        val ySize = yBuffer.remaining()
        val yBytes = ByteArray(ySize)
        yBuffer.get(yBytes)

        var sum = 0f
        for (i in 0 until ySize) {
            sum += (yBytes[i].toInt() and 0xFF)
        }

        return (sum / ySize) / 255f * 100
    }
}