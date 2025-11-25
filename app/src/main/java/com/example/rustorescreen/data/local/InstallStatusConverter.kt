package com.example.rustorescreen.data.local

import androidx.room.TypeConverter
import com.example.rustorescreen.domain.domainModel.InstallStatus


class InstallStatusConverter {

    // Формат хранения:
    // Idle
    // InstallPrepared
    // InstallStarted
    // Installing:42
    // Installed
    // InstallError:Some error message
    // UninstallPrepared
    // Uninstalling:10
    // UninstallError:Other error

    @TypeConverter
    fun fromStatus(status: InstallStatus?): String? {
        return when (status) {
            is InstallStatus.Idle -> "Idle"
            is InstallStatus.InstallPrepared -> "InstallPrepared"
            is InstallStatus.InstallStarted -> "InstallStarted"
            is InstallStatus.Installing -> "Installing: ${status.progress}"
            is InstallStatus.Installed -> "Installed"
            is InstallStatus.InstallError -> "InstallError:${status.error.message ?: "Unknown"}"
            is InstallStatus.UninstallPrepared -> "UninstallPrepared"
            is InstallStatus.Uninstalling -> "Uninstalling${status.progress}"
            is InstallStatus.UninstallError -> "UninstallError:${status.error.message ?: "Unknown"}"
            null -> null
        }
    }

    @TypeConverter
    fun toStatus(str: String?): InstallStatus? {
        if (str.isNullOrEmpty()) return null

        val parts = str.split( // для сохранения и выгрузки прогресса
            ":",
            limit = 2,
        )
        val type = parts[0]
        val arg = parts.getOrNull(index = 1)

        return when (type) {
            "Idle" -> InstallStatus.Idle
            "InstallPrepared" -> InstallStatus.InstallPrepared
            "InstallStarted" -> InstallStatus.InstallStarted
            "Installing" -> {
                val progress = arg?.toIntOrNull() ?: 0
                InstallStatus.Installing(progress = progress)
            }
            "Installed" -> InstallStatus.Installed
            "InstallError" -> {
                val message = arg ?: "Unknown"
                InstallStatus.InstallError(error = Throwable(message))
            }
            "UninstallPrepared" -> InstallStatus.UninstallPrepared
            "Uninstalling" -> {
                val progress = arg?.toIntOrNull() ?: 0
                InstallStatus.Uninstalling(progress = progress)
            }
            "UninstallError" -> {
                val message = arg ?: "Unknown"
                InstallStatus.UninstallError(error = Throwable(message))
            }
            else -> InstallStatus.Idle
        }
    }
}