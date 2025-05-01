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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.hardwarecheck.R
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions

@Composable
fun CameraScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    val brightness = remember { mutableStateOf(0f) }
    val detectedObjects = remember { mutableStateOf<List<DetectedObject>>(emptyList()) }
    val cameraInitialized = remember { mutableStateOf(false) }
    val cameraActive = remember { mutableStateOf(true) }
    val hasCameraPermission = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        )
    }

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

            setEnabledUseCases(CameraController.IMAGE_ANALYSIS or CameraController.IMAGE_CAPTURE)


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
    LaunchedEffect(hasCameraPermission.value, cameraActive.value) {
        if (hasCameraPermission.value) {
            try {
                if (cameraActive.value && !cameraInitialized.value) {
                    cameraController.bindToLifecycle(lifecycleOwner)
                    cameraInitialized.value = true
                } else if (!cameraActive.value && cameraInitialized.value) {
                    cameraController.unbind()
                    cameraInitialized.value = false
                }
            } catch (e: Exception) {
                Log.e("CameraScreen", "Error managing camera lifecycle", e)
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
                    text = stringResource(id = R.string.camera_analysis),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = stringResource(
                            id = R.string.brightness,
                            brightness.value,
                            detectedObjects.value.size
                        ),
                        fontSize = 16.sp
                    )
                    Text(
                        text = stringResource(id = R.string.objects) + ": ${detectedObjects.value.size}",
                        fontSize = 16.sp
                    )
                    Button(
                        onClick = {
                            cameraActive.value = !cameraActive.value
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(if (cameraActive.value) stringResource(id = R.string.stop_camera) else stringResource(id = R.string.start_camera))
                    }
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
                text = stringResource(id = R.string.objects_detected),
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
