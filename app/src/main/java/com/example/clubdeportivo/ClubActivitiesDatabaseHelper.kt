package com.example.clubdeportivo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ClubActivitiesDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "CLUBDEPORTIVO.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USER = "ClubActivities"
        private const val COLUMN_CLUBACTIVITY_ID = "idClubActivity"
        private const val COLUMN_CLUBACTIVITY_NAME = "nameClubActivity"
        private const val COLUMN_CLUBACTIVITY_COST = "costClubActivity"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableUser = ("CREATE TABLE $TABLE_USER ("
                + "$COLUMN_CLUBACTIVITY_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_CLUBACTIVITY_NAME TEXT, "
                + "$COLUMN_CLUBACTIVITY_COST TEXT)")

        db.execSQL(createTableUser)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    fun addClubActivity(nameClubActivity: String, costClubActivity: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CLUBACTIVITY_NAME, nameClubActivity)
            put(COLUMN_CLUBACTIVITY_COST, costClubActivity)
        }
        return db.insert(TABLE_USER, null, values)
    }

    fun checkUser(nameClubActivity: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USER WHERE $COLUMN_CLUBACTIVITY_NAME = ?"
        val cursor = db.rawQuery(query, arrayOf(nameClubActivity))

        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()

        return exists
    }
}