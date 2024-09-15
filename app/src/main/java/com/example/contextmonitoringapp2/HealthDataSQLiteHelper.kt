package com.example.contextmonitoringapp2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.IOException

class HealthDataSQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "HealthData.db"
        private const val TABLE_NAME = "health_data"

        private const val COLUMN_ID = "id"
        private const val COLUMN_HEART_RATE = "heart_rate"
        private const val COLUMN_RESPIRATORY_RATE = "respiratory_rate"
        private const val COLUMN_NAUSEA_RATING = "nausea_rating"
        private const val COLUMN_HEADACHE_RATING = "headache_rating"
        private const val COLUMN_DIARRHEA_RATING = "diarrhea_rating"
        private const val COLUMN_SORE_THROAT_RATING = "sore_throat_rating"
        private const val COLUMN_FEVER_RATING = "fever_rating"
        private const val COLUMN_MUSCLE_ACHE_RATING = "muscle_ache_rating"
        private const val COLUMN_LOSS_OF_SMELL_TASTE_RATING = "loss_of_smell_taste_rating"
        private const val COLUMN_COUGH_RATING = "cough_rating"
        private const val COLUMN_SHORTNESS_OF_BREATH_RATING = "shortness_of_breath_rating"
        private const val COLUMN_FEELING_TIRED_RATING = "feeling_tired_rating"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_HEART_RATE INTEGER, "
                + "$COLUMN_RESPIRATORY_RATE INTEGER, "
                + "$COLUMN_NAUSEA_RATING INTEGER, "
                + "$COLUMN_HEADACHE_RATING INTEGER, "
                + "$COLUMN_DIARRHEA_RATING INTEGER, "
                + "$COLUMN_SORE_THROAT_RATING INTEGER, "
                + "$COLUMN_FEVER_RATING INTEGER, "
                + "$COLUMN_MUSCLE_ACHE_RATING INTEGER, "
                + "$COLUMN_LOSS_OF_SMELL_TASTE_RATING INTEGER, "
                + "$COLUMN_COUGH_RATING INTEGER, "
                + "$COLUMN_SHORTNESS_OF_BREATH_RATING INTEGER, "
                + "$COLUMN_FEELING_TIRED_RATING INTEGER)")
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insert health data
    fun insertHealthData(healthData: HealthData): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_HEART_RATE, healthData.heartRate)
            put(COLUMN_RESPIRATORY_RATE, healthData.respiratoryRate)
            put(COLUMN_NAUSEA_RATING, healthData.nauseaRating)
            put(COLUMN_HEADACHE_RATING, healthData.headacheRating)
            put(COLUMN_DIARRHEA_RATING, healthData.diarrheaRating)
            put(COLUMN_SORE_THROAT_RATING, healthData.soreThroatRating)
            put(COLUMN_FEVER_RATING, healthData.feverRating)
            put(COLUMN_MUSCLE_ACHE_RATING, healthData.muscleAcheRating)
            put(COLUMN_LOSS_OF_SMELL_TASTE_RATING, healthData.lossOfSmellTasteRating)
            put(COLUMN_COUGH_RATING, healthData.coughRating)
            put(COLUMN_SHORTNESS_OF_BREATH_RATING, healthData.shortnessOfBreathRating)
            put(COLUMN_FEELING_TIRED_RATING, healthData.feelingTiredRating)
        }

        return db.insert(TABLE_NAME, null, values)
    }

    // Retrieve all health data
    fun getAllHealthData(): List<HealthData> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val healthDataList = mutableListOf<HealthData>()

        if (cursor.moveToFirst()) {
            do {
                val healthData = HealthData(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    heartRate = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HEART_RATE)),
                    respiratoryRate = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RESPIRATORY_RATE)),
                    nauseaRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAUSEA_RATING)),
                    headacheRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HEADACHE_RATING)),
                    diarrheaRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DIARRHEA_RATING)),
                    soreThroatRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SORE_THROAT_RATING)),
                    feverRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEVER_RATING)),
                    muscleAcheRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MUSCLE_ACHE_RATING)),
                    lossOfSmellTasteRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LOSS_OF_SMELL_TASTE_RATING)),
                    coughRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COUGH_RATING)),
                    shortnessOfBreathRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SHORTNESS_OF_BREATH_RATING)),
                    feelingTiredRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FEELING_TIRED_RATING))
                )
                healthDataList.add(healthData)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return healthDataList
    }

    fun exportDatabase(context: Context): File? {
        // Path to the internal database file
        val databasePath = this.readableDatabase.path
        val srcFile = File(databasePath)

        // Destination path in external files directory
        val externalFile = File(context.getExternalFilesDir(null), "HealthData_Exported.db")

        return try {
            // Copy the database file to the destination path
            srcFile.copyTo(externalFile, overwrite = true)
            Log.d("DB Export", "Database exported to: ${externalFile.absolutePath}")
            externalFile
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("DB Export", "Export failed: ${e.message}")
            null
        }
    }
}