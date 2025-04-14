package io.livekit.android

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager

/**
 * SCO-enabled LiveKit wrapper.
 * Always uses CustomAudioType for full-duplex Bluetooth SCO communication.
 */
object LiveKitSco {

    fun create(
        appContext: Context,
    ): Room {
        val scoAudioType = AudioType.CustomAudioType(
            audioMode = AudioManager.MODE_IN_COMMUNICATION,
            audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build(),
            audioStreamType = AudioManager.STREAM_VOICE_CALL,
        )

        val audioOptions = AudioOptions(
            audioOutputType = scoAudioType,
            audioHandler = null, // Optional: use NoAudioHandler if needed
            disableCommunicationModeWorkaround = false,
            audioProcessorOptions = null,
            javaAudioDeviceModuleCustomizer = null,
            audioDeviceModule = null,
            // ✅ This is important to allow A2DP (media) to continue
            audioHandler = null,
        )

        val overrides = LiveKitOverrides(
            audioOptions = audioOptions
        )

        return LiveKit.create(
            appContext = appContext,
            options = RoomOptions(), // Optional: pass RoomOptions if needed
            overrides = overrides
        )
    }
}
