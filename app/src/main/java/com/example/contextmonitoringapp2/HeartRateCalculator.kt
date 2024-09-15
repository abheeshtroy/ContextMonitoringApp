package com.example.contextmonitoringapp2

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun heartRateCalculator(uri: Uri): Int {
    return withContext(Dispatchers.IO) {
        val result: Int
        val videoPath = uri.path  // Directly get the path from the URI

        if (videoPath.isNullOrEmpty()) {
            throw IllegalArgumentException("Invalid video path: $videoPath")
        }

        val retriever = MediaMetadataRetriever()
        val frameList = ArrayList<Bitmap>()
        try {
            // Set data source using the file path
            retriever.setDataSource(videoPath)
            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT)
            Log.d("HeartRateCalculator", "Total frames in video: $duration")

            if (duration == null || duration.toInt() <= 0) {
                throw IllegalArgumentException("Invalid video duration")
            }

            val frameDuration = kotlin.math.min(duration.toInt(), 425)
            Log.d("HeartRateCalculator", "Processing up to $frameDuration frames")

            var i = 10
            while (i < frameDuration) {
                val bitmap = retriever.getFrameAtIndex(i)
                if (bitmap != null) {
                    frameList.add(bitmap)
                    Log.d("HeartRateCalculator", "Frame at index $i added successfully")
                } else {
                    Log.w("HeartRateCalculator", "Null frame at index $i")
                }
                i += 15  // Increment by 15 frames
            }

            if (frameList.isEmpty()) {
                throw IllegalArgumentException("No frames extracted from the video")
            }

            Log.d("HeartRateCalculator", "Frames extracted: ${frameList.size}")

        } catch (e: Exception) {
            Log.e("HeartRateCalculator", "Error processing video: ${e.message}", e)
            throw e
        } finally {
            retriever.release()  // Always release the retriever resources
        }

        // --- Core logic for heart rate calculation ---
        var redBucket: Long
        var pixelCount: Long = 0
        val a = mutableListOf<Long>()
        for (i in frameList) {
            redBucket = 0
            for (y in 350 until 450) {
                for (x in 350 until 450) {
                    val c: Int = i.getPixel(x, y)
                    pixelCount++
                    redBucket += android.graphics.Color.red(c) + android.graphics.Color.blue(c) +
                            android.graphics.Color.green(c)
                }
            }
            a.add(redBucket)
        }

        if (a.isEmpty()) {
            throw IllegalStateException("No color data extracted from frames")
        }

        val b = mutableListOf<Long>()
        for (i in 0 until a.lastIndex - 5) {
            val temp = (a.elementAt(i) + a.elementAt(i + 1) + a.elementAt(i + 2)
                    + a.elementAt(i + 3) + a.elementAt(i + 4)) / 4
            b.add(temp)
        }

        var x = b.elementAt(0)
        var count = 0
        for (i in 1 until b.lastIndex) {
            val p = b.elementAt(i)
            if ((p - x) > 3500) {
                count += 1
            }
            x = b.elementAt(i)
        }

        val rate = ((count.toFloat()) * 60).toInt()
        result = (rate / 4)

        result  // Return the calculated heart rate
    }
}

