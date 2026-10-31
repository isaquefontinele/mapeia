package io.github.isaquefontinele.mapeia.config

import android.content.Context
import android.content.pm.ApplicationInfo

internal fun androidBuildInfo(context: Context) = BuildInfo(
    isDebugBuild = context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0,
)
