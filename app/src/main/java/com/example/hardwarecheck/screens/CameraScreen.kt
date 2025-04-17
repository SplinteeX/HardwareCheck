package com.example.hardwarecheck.screens

import android.Manifest
import android.graphics.ImageFormat
import android.os.SystemClock
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.view.PreviewView
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.CameraController
import androidx.compose.foundation.background
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
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import java.nio.ByteBuffer

@Composable
fun CameraScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // State for brightness value
    val brightness = remember { mutableStateOf(0f) }
    // State for detected objects
    val detectedObjects = remember { mutableStateOf<List<DetectedObject>>(emptyList()) }
    // State to track if camera is initialized
    val cameraInitialized = remember { mutableStateOf(false) }

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

    // Initialize ML Kit Object Detector
    val objectDetector = remember {
        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
            .enableClassification()
            .build()
        ObjectDetection.getClient(options)
    }

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            // Enable both preview and analysis
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS or CameraController.IMAGE_CAPTURE)

            // Set up combined image analyzer
            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                CombinedAnalyzer(
                    brightnessCallback = { luminance ->
                        brightness.value = luminance
                    },
                    objectDetectionCallback = { objects ->
                        detectedObjects.value = objects
                    },
                    objectDetector = objectDetector
                )
            )
        }
    }

    // Request permission when composable is first launched
    LaunchedEffect(Unit) {
        if (!hasCameraPermission.value) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Bind controller to lifecycle when permission is granted
    LaunchedEffect(hasCameraPermission.value) {
        if (hasCameraPermission.value && !cameraInitialized.value) {
            try {
                cameraController.bindToLifecycle(lifecycleOwner)
                cameraInitialized.value = true
            } catch (e: Exception) {
                Log.e("CameraScreen", "Failed to bind camera", e)
            }
        }
    }

    // Clean up when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            objectDetector.close()
            if (cameraInitialized.value) {
                cameraController.unbind()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
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

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Brightness: ${"%.2f".format(brightness.value)}%",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Objects: ${detectedObjects.value.size}",
                        fontSize = 16.sp
                    )
                }
            }
        }

        if (hasCameraPermission.value) {
            Box(modifier = Modifier.weight(1f)) {
                AndroidView(
                    factory = { ctx ->
                        PreviewView(ctx).apply {
                            controller = cameraController
                            scaleType = PreviewView.ScaleType.FILL_CENTER
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // Show detected objects as an overlay
                DetectedObjectsInfo(
                    objects = detectedObjects.value,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                        .padding(8.dp)
                )
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

@Composable
fun DetectedObjectsInfo(
    objects: List<DetectedObject>,
    modifier: Modifier = Modifier
) {
    if (objects.isNotEmpty()) {
        Column(modifier = modifier) {
            Text(
                "Detected Objects:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            objects.forEachIndexed { index, obj ->
                val labels = obj.labels.joinToString {
                    "${it.text} (${"%.2f".format(it.confidence * 100)}%)"
                }
                Text(
                    "• $labels",
                    fontSize = 14.sp
                )
            }
        }
    }
}

class CombinedAnalyzer(
    private val brightnessCallback: (Float) -> Unit,
    private val objectDetectionCallback: (List<DetectedObject>) -> Unit,
    private val objectDetector: com.google.mlkit.vision.objects.ObjectDetector
) : ImageAnalysis.Analyzer {

    private var isProcessingDetection = false
    private var lastTimestamp = SystemClock.elapsedRealtimeNanos()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val now = SystemClock.elapsedRealtimeNanos()
        if (now <= lastTimestamp) {
            // Guarantee strictly increasing timestamp
            lastTimestamp += 1_000_000 // Add 1ms
        } else {
            lastTimestamp = now
        }

        try {
            val luminance = calculateLuminance(imageProxy)
            brightnessCallback(luminance)
        } catch (e: Exception) {
            Log.e("CombinedAnalyzer", "Brightness error", e)
        }

        if (isProcessingDetection) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }

        isProcessingDetection = true

        val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        objectDetector.process(inputImage)
            .addOnSuccessListener { results ->
                objectDetectionCallback(results)
            }
            .addOnFailureListener { e ->
                Log.e("CombinedAnalyzer", "Object detection failed", e)
            }
            .addOnCompleteListener {
                isProcessingDetection = false
                imageProxy.close()
            }
    }

    private fun calculateLuminance(image: ImageProxy): Float {
        if (image.format != ImageFormat.YUV_420_888) return 0f
        val yBuffer = image.planes[0].buffer
        val yBytes = ByteArray(yBuffer.remaining())
        yBuffer.get(yBytes)

        var sum = 0f
        for (byte in yBytes) {
            sum += (byte.toInt() and 0xFF)
        }
        return (sum / yBytes.size) / 255f * 100
    }
}
