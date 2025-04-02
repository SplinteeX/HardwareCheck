import androidx.appcompat.app.AppCompatActivity
import com.example.hardwarecheck.R
import com.example.hardwarecheck.fragments.HardwareFragment
import com.example.hardwarecheck.fragments.OverviewFragment
import com.example.hardwarecheck.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

object NavigationUtils {
    fun setupBottomNavigation(
        bottomNav: BottomNavigationView,
        activity: AppCompatActivity,
        containerId: Int = R.id.fragment_container
    ) {
        activity.supportFragmentManager.beginTransaction()
            .replace(containerId, HardwareFragment())
            .commit()

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_hardware -> {
                    activity.supportFragmentManager.beginTransaction()
                        .replace(containerId, HardwareFragment())
                        .commit()
                    true
                }
                R.id.nav_overview -> {
                    activity.supportFragmentManager.beginTransaction()
                        .replace(containerId, OverviewFragment())
                        .commit()
                    true
                }
                R.id.nav_profile -> {
                    activity.supportFragmentManager.beginTransaction()
                        .replace(containerId, ProfileFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}