package com.example.vozi001.service


import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import java.io.IOException
import java.util.*

class AudioRecordingService(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var currentAudioFile: File? = null
    private var isRecording = false

    fun startRecording(): Result<File> {
        return try {
            stopRecording() // Detener cualquier grabación previa

            val outputDir = File(context.filesDir, "recordings").apply {
                if (!exists()) mkdirs()
            }
            val audioFile = File(outputDir, "recording_${UUID.randomUUID()}.wav")

            mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                MediaRecorder()
            }.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFile.absolutePath)

                try {
                    prepare()
                    start()
                } catch (e: IOException) {
                    release()
                    throw e
                }
            }

            currentAudioFile = audioFile
            isRecording = true

            Result.success(audioFile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun stopRecording(): Result<File?> {
        return try {
            if (isRecording) {
                mediaRecorder?.apply {
                    try {
                        stop()
                    } catch (e: Exception) {
                        // Ignorar errores al detener
                    }
                    release()
                }
            }
            mediaRecorder = null
            isRecording = false

            Result.success(currentAudioFile)
        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            mediaRecorder = null
            isRecording = false
        }
    }

    fun isRecording(): Boolean = isRecording

    fun getCurrentRecordingFile(): File? = currentAudioFile

    fun cleanup() {
        stopRecording()
        currentAudioFile = null
    }
}