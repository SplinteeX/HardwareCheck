
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hardwarecheck.database.FirestoreManager
import com.example.hardwarecheck.utils.PreferenceHelper

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val firestoreManager = FirestoreManager()
    val deviceId = android.provider.Settings.Secure.getString(
        context.contentResolver,
        android.provider.Settings.Secure.ANDROID_ID
    )

    var isSaveDataEnabled by remember {
        mutableStateOf(PreferenceHelper.isSaveDataEnabled(context))
    }

    val statusMessage = if (isSaveDataEnabled) "Connected to cloud database" else "Local mode (data not synced)"
    val statusColor = if (isSaveDataEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    fun handleSaveDataChange(enabled: Boolean) {
        PreferenceHelper.setSaveDataEnabled(context, enabled)
        isSaveDataEnabled = enabled
        if (!enabled) firestoreManager.deleteDeviceInfo(deviceId)
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