package com.revature.contentproviderexample

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.util.Log

class RevatureContentProvider:ContentProvider() {

    companion object
    {
        const val DATABASE_NAME="UserDB"
        const val DATABASE_VERSION=1
        const val TABLE_NAME="users"

        const val name="name"
        const val id="id"

        //defining the authority(content URI)

        const val PROVIDER_NAME="com.revature.user.provider"

        //defining content URI

        //  const val URL="http://www.google.com/user"
        const val URL="content://$PROVIDER_NAME/users"

        //parsing the content URI

//        val CONTENT_URI=Uri.parse(URL)
        val CONTENT_URI = Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(PROVIDER_NAME)
            .appendPath(TABLE_NAME).build()



        var uriMatcher:UriMatcher?=null

        private val values:HashMap<String,String>?=null

        //create a table

        const val CREATE_DB_TABLE=(

                "CREATE TABLE "+ TABLE_NAME+
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL);")

        const val uriCode = 1

        init {
            //to match the content URI
            uriMatcher= UriMatcher(UriMatcher.NO_MATCH)

            //to access whole table

            uriMatcher!!.addURI(
                PROVIDER_NAME,
                "users",
                uriCode

            )

            //access particular row

            uriMatcher!!.addURI(
                PROVIDER_NAME,
                "users/*",
                uriCode

            )
        }
    }

    override fun onCreate(): Boolean {
        val context = context

        val dbHelper = context?.let { DatabaseHelper(it) }
        if (dbHelper != null) {
            db =dbHelper.writableDatabase
        }
        return db != null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

        var sortOrder =sortOrder
        val qb = SQLiteQueryBuilder()
        qb.tables = TABLE_NAME

        when (uriMatcher!!.match(uri)){
            uriCode->qb.projectionMap = values
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }

        if (sortOrder == null || sortOrder ===""){
            sortOrder = id
        }
        val c = qb.query(db, projection,selection,selectionArgs,null,null,sortOrder)
        c.setNotificationUri(context!!.contentResolver,uri)

        return c


    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, p1: ContentValues?): Uri? {
        val rowId = db!!.insert(TABLE_NAME,"",p1)
        if(rowId >0) {
            val _uri = ContentUris.withAppendedId(uri,rowId)
            context!!.contentResolver.notifyChange(_uri,null)

            return _uri
        }
        throw SQLiteException("Failed to add record into $uri")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    private var db:SQLiteDatabase? = null

    private class DatabaseHelper
    internal constructor(context:Context) : SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION){

        override fun onCreate(p0: SQLiteDatabase?) {
            p0?.execSQL(CREATE_DB_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?,
                               oldVersion: Int,
                               newVersion: Int) {

            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }

    }

}