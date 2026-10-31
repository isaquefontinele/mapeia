package io.github.isaquefontinele.mapeia.config

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
internal fun iosBuildInfo() = BuildInfo(isDebugBuild = Platform.isDebugBinary)
