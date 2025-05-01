
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hardwarecheck.R
import com.example.hardwarecheck.database.FirestoreManager
import com.example.hardwarecheck.utils.HardwareInfoUtils
import com.example.hardwarecheck.utils.PreferenceHelper
import java.util.Locale

@SuppressLint("HardwareIds")
@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    var selectedLanguage by remember { mutableStateOf(Locale.getDefault().language) }
    val firestoreManager = FirestoreManager()
    val deviceInfo = HardwareInfoUtils.collectDeviceInfo(context)
    val deviceId = android.provider.Settings.Secure.getString(
        context.contentResolver,
        android.provider.Settings.Secure.ANDROID_ID
    )

    var isSaveDataEnabled by remember {
        mutableStateOf(PreferenceHelper.isSaveDataEnabled(context))
    }

    val statusMessage = if (isSaveDataEnabled) stringResource(id = R.string.connected_to_cloud) else stringResource(id = R.string.local_mode)
    val statusColor = if (isSaveDataEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    fun handleSaveDataChange(enabled: Boolean) {
        PreferenceHelper.setSaveDataEnabled(context, enabled)
        isSaveDataEnabled = enabled
        if (!enabled) firestoreManager.deleteDeviceInfo(deviceId) else {
            firestoreManager.saveDeviceInfo(context, deviceId, deviceInfo)
        }
    }

    LaunchedEffect(selectedLanguage) {
        setLocale(context, selectedLanguage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        ProfileHeader()

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(id = R.string.language_option),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                LanguageSwitcher(
                    selectedLanguage = selectedLanguage,
                    onLanguageSelected = { language ->
                        selectedLanguage = language
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            DataSyncCard(
                isSaveDataEnabled = isSaveDataEnabled,
                statusMessage = statusMessage,
                statusColor = statusColor,
                onToggleChange = { handleSaveDataChange(it) }
            )

            DangerZoneCard(
                onDeleteClick = {
                    firestoreManager.deleteDeviceInfo(deviceId)
                    handleSaveDataChange(false)
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            DeviceInfoCard(
                deviceId = deviceId,
                isSaveDataEnabled = isSaveDataEnabled
            )
        }
    }
}

@Composable
fun LanguageSwitcher(selectedLanguage: String, onLanguageSelected: (String) -> Unit) {
    val languages = listOf("en" to R.string.english, "fi" to R.string.finnish)

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(languages) { (code, labelRes) ->
            val isSelected = selectedLanguage == code
            Button(
                onClick = { onLanguageSelected(code) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(text = stringResource(id = labelRes))
            }
        }
    }
}

fun setLocale(context: Context, languageCode: String) {
    val currentLanguage = PreferenceHelper.getAppLanguage(context)
    if (currentLanguage == languageCode) return

    PreferenceHelper.setAppLanguage(context, languageCode)

    if (context is Activity) {
        context.recreate()
    }
}