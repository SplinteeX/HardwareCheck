import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hardwarecheck.R

@Composable
fun DeviceInfoCard(
    deviceId: String,
    isSaveDataEnabled: Boolean
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.device_information),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        InfoRow(stringResource(id = R.string.device_id), deviceId.take(8) + "..." + deviceId.takeLast(4))
        InfoRow(
            stringResource(id = R.string.sync_status),
            if (isSaveDataEnabled) stringResource(id = R.string.sync_status_active) else stringResource(id = R.string.sync_status_inactive)
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}