package com.huutri.sixpack.common.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.huutri.sixpack.common.model.DayDB
import com.huutri.sixpack.common.model.Exercise_Move
import com.huutri.sixpack.common.model.ExercisesDB
import com.huutri.sixpack.common.model.MoveDB
import com.huutri.sixpack.ui.fragment.OneDayFragment.Companion.oneDayToGetData
import android.content.ContentValues
import com.huutri.sixpack.common.adapter.DayAdapter
import com.huutri.sixpack.ui.fragment.OneDayFragment
import com.huutri.sixpack.ui.fragment.RunFragment.Companion.posRun


class DatabaseAccess

/**
 * Private constructor to aboid object creation from outside classes.
 *
 * @param context
 */
private constructor(context: Context) {
    private val openHelper: SQLiteOpenHelper
    var database: SQLiteDatabase? = null
    /**
     * Read all quotes from the databases.
     *
     * @return a List of DAYS
     */
    val getALLDayLose: ArrayList<DayDB>
        get() {
            var list = ArrayList<DayDB>()
            open()
            val cursor = database?.rawQuery("SELECT * FROM 'DAY' WHERE ID_OF_TYPE=1", null)
            cursor?.moveToFirst()
            while (!cursor!!.isAfterLast()) {
                val day = DayDB()
                day.ID_DAY = cursor.getInt(0)
                day.NAME_DAY = cursor.getString(1)
                day.SUM_NUMBER_EX_ON_DAY = cursor.getInt(2)
                day.STATUS_DAY = cursor.getInt(3)
                day.ID_OF_TYPE = cursor.getInt(4)
                list.add(day)
                cursor.moveToNext()
            }
            cursor.close()
            return list
        }
    /**
     * Read all quotes from the databases.
     *
     * @return a List of DAYS
     */
    val getALLDayRock: ArrayList<DayDB>
        get() {
            var list = ArrayList<DayDB>()
            open()
            val cursor = database?.rawQuery("SELECT * FROM 'DAY' WHERE ID_OF_TYPE=2", null)
            cursor?.moveToFirst()
            while (!cursor!!.isAfterLast()) {
                val day = DayDB()
                day.ID_DAY = cursor.getInt(0)
                day.NAME_DAY = cursor.getString(1)
                day.SUM_NUMBER_EX_ON_DAY = cursor.getInt(2)
                day.STATUS_DAY = cursor.getInt(3)
                day.ID_OF_TYPE = cursor.getInt(4)
                list.add(day)
                cursor.moveToNext()
            }
            cursor.close()
            return list
        }
    /**
     * Read all quotes from the databases.
     *
     * @return a List of DAYS
     */
    val getALLDayAbs: ArrayList<DayDB>
        get() {
            var list = ArrayList<DayDB>()
            open()
            val cursor = database?.rawQuery("SELECT * FROM 'DAY' WHERE ID_OF_TYPE=3", null)
            cursor?.moveToFirst()
            while (!cursor!!.isAfterLast()) {
                val day = DayDB()
                day.ID_DAY = cursor.getInt(0)
                day.NAME_DAY = cursor.getString(1)
                day.SUM_NUMBER_EX_ON_DAY = cursor.getInt(2)
                day.STATUS_DAY = cursor.getInt(3)
                day.ID_OF_TYPE = cursor.getInt(4)
                list.add(day)
                cursor.moveToNext()
            }
            cursor.close()
            return list
        }
    /**
     * get number Motion status start of 1 day
     *
     * @return ""
     */
    val getNumberMotionStartOfDay: Int
        get() {
            var stt = 0
            open()
            val cursor = database?.rawQuery("SELECT  * FROM 'EXERCISES' WHERE ID_OF_DAY="+oneDayToGetData+" and STATUS_EX=0 LIMIT 1", null)

            cursor?.moveToFirst()
            while (!cursor!!.isAfterLast()) {
                stt = cursor.getInt(0)
                cursor.moveToNext()
            }
            cursor.close()
            return stt
        }
    /**
     * get number Motion status start of 1 day
     *
     * @return ""
     */
    val getNumberStartOfDay: Int
        get() {
            var stt = 0
            open()
            val cursor = database?.rawQuery("SELECT  * FROM 'EXERCISES' WHERE ID_OF_DAY="+oneDayToGetData+"  LIMIT 1", null)

            cursor?.moveToFirst()
            while (!cursor!!.isAfterLast()) {
                stt = cursor.getInt(0)
                cursor.moveToNext()
            }
            cursor.close()
            return stt
        }
    /**
     * update finish day
     *
     * @return ""
     */
    val updateDayinish: String
        get() {
            open()
            val cv = ContentValues()
            cv.put(
                "STATUS_DAY",
                2
            ) //These Fields should be your String values of actual column names
            this.database!!.update(
                "DAY",
                cv,
                "ID_DAY="+ OneDayFragment.oneDayToGetData!!,
                null
            )
            return ""
        }
    /**
     * update Inprogress day
     *
     * @return ""
     */
    val updateDayInprogress: String
        get() {
            open()
            val cv = ContentValues()
            cv.put(
                "STATUS_DAY",
                3
            ) //These Fields should be your String values of actual column names
            this.database!!.update(
                "DAY",
                cv,
                "ID_DAY="+ OneDayFragment.oneDayToGetData!!,
                null
            )
            return ""
        }
    /**
     * get Count Exercise Finish
     *
     * @return ""
     */
    val countExerciseFinish: Int
        get() {
            var count = 0
            open()

            val cursor = database?.rawQuery("  SELECT count( ID_EX)  from 'EXERCISES' where ID_OF_DAY="+ DayAdapter.daygetCountExerciseFinish +" AND STATUS_EX=2", null)

            cursor?.moveToFirst()
            while (!cursor!!.isAfterLast()) {
                count = cursor.getInt(0)
                cursor.moveToNext()
            }
            cursor.close()

            return count
        }
    /**
     * update finish motion
     *
     * @return ""
     */
    val updateMotion: String
        get() {
            open()
            val cv = ContentValues()
            cv.put(
                "STATUS_EX",
                2
            ) //These Fields should be your String values of actual column names
            this.database!!.update(
                "EXERCISES",
                cv,
                "ID_OF_DAY="+ oneDayToGetData+" AND ID_EX="+posRun,
                null
            )
            return ""
        }
    /**
     * update start motion
     *
     * @return ""
     */
    val updateMotionStart: String
        get() {
            open()
            val cv = ContentValues()
            cv.put(
                "STATUS_EX",
                0
            ) //These Fields should be your String values of actual column names
            this.database!!.update(
                "EXERCISES",
                cv,
                "ID_OF_DAY="+ oneDayToGetData+" AND ID_EX="+posRun,
                null
            )
            return ""
        }
    /**
     * reset ALL start motion of day
     *
     * @return ""
     */
    val updateResetALLMotionStart: String
        get() {
            open()
            val cv = ContentValues()
            cv.put(
                "STATUS_EX",
                0
            ) //These Fields should be your String values of actual column names
            this.database!!.update(
                "EXERCISES",
                cv,
                "ID_OF_DAY="+ oneDayToGetData,
                null
            )
            return ""
        }
    /**
     * Read all quotes from the databases.
     *
     * @return a List of DAY
     */
    val getOneDay: Exercise_Move
        get() {
            var list = Exercise_Move()
            var listExercisesDB = ArrayList<ExercisesDB>()
            var listMoveDB = ArrayList<MoveDB>()

            open()
            val cursor = database?.rawQuery(
                "SELECT * FROM 'EXERCISES' INNER JOIN 'MOVE' on EXERCISES.ID_OF_MOVE=MOVE.ID_MOVE WHERE EXERCISES.ID_OF_DAY=" + oneDayToGetData,
                null
            )

            cursor?.moveToFirst()
            while (!cursor!!.isAfterLast()) {
                val exercise = ExercisesDB()
                val move = MoveDB()

                exercise.ID_EX = cursor.getInt(0)
                exercise.TIME_EX = cursor.getString(1)
                exercise.STATUS_EX = cursor.getInt(2)
                exercise.ID_OF_DAY = cursor.getInt(3)
                exercise.ID_OF_MOVE = cursor.getInt(4)

                move.ID_MOVE = cursor.getInt(5)
                move.NAME_MOVE = cursor.getString(6)
                move.DESCRIPTION_MOVE = cursor.getString(7)
                move.LINK_IMAGE_MOVE = cursor.getString(8)
                move.LINK_VIDEO_MOVE = cursor.getString(9)
                listExercisesDB.add(exercise)
                listMoveDB.add(move)

                cursor.moveToNext()
            }
            cursor.close()
            list.listMove = listMoveDB
            list.listExercise = listExercisesDB
            return list
        }

    init {
        this.openHelper = DatabaseOpenHelper(context)
    }

    /**
     * Open the databases connection.
     */
    fun open() {
        this.database = openHelper.getWritableDatabase()
    }

    /**
     * Close the databases connection.
     */
    fun close() {
        if (database != null) {
            this.database!!.close()
        }
    }

    companion object {
        private var instance: DatabaseAccess? = null
        /**
         * Return a singleton instance of DatabaseAccess.
         *
         * @param context the Context
         * @return the instance of DabaseAccess
         */
        fun getInstance(context: Context): DatabaseAccess {
            if (instance == null) {
                instance = DatabaseAccess(context)
            }
            return instance!!
        }
    }
}