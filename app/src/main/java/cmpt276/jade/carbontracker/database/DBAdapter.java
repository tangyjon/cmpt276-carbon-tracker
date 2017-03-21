package cmpt276.jade.carbontracker.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import cmpt276.jade.carbontracker.model.Car;
import cmpt276.jade.carbontracker.model.Journey;
import cmpt276.jade.carbontracker.model.Route;

// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";

    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 2;

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb";

    // DB Tables
    public static final String DATABASE_TABLE = "mainTable";
    public static final String TABLE_CAR = "cars";
    public static final String TABLE_ROUTE = "routes";
    public static final String TABLE_JOURNEY = "journeys";


    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;

    // TODO: Setup Journey Fields Here

    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)

    // ALL KEYS

    // TODO: Setup Car Fields Here
    public static final String KEY_CAR_CARBON_TAIL_PIPE = "car_carbon_tail_pipe";
    public static final String KEY_CAR_CITY_MPG = "car_city_mpg";
    public static final String KEY_CAR_ENGINE_DESCRIPTION = "car_engine_descrip";
    public static final String KEY_CAR_ENGINE_DISP_LITRES = "car_engine_displacement";
    public static final String KEY_CAR_FUEL_ANNUAL_COST = "car_fuel_annual_cost";
    public static final String KEY_CAR_FUEL_TYPE = "car_fuel_type";
    public static final String KEY_CAR_HIGHWAY_MPG = "car_highway_mpg";
    public static final String KEY_CAR_MAKE = "car_make ";
    public static final String KEY_CAR_MODEL = "car_model";
    public static final String KEY_CAR_NICK_NAME = "car_nick_name";
    public static final String KEY_CAR_TRANS_DESCRIPTION = "car_descrip";
    public static final String KEY_CAR_YEAR = "car_year";

    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)
    public static final int COL_CAR_CARBON_TAIL_PIPE = 1;
    public static final int COL_CAR_CITY_MPG = 2;
    public static final int COL_CAR_ENGINE_DESCRIPTION = 3;
    public static final int COL_CAR_ENGINE_DISP_LITRES = 4;
    public static final int COL_CAR_FUEL_ANNUAL_COST = 5;
    public static final int COL_CAR_FUEL_TYPE = 6;
    public static final int COL_CAR_HIGHWAY_MPG = 7;
    public static final int COL_CAR_MAKE = 8;
    public static final int COL_CAR_MODEL = 9;
    public static final int COL_CAR_NICK_NAME = 10;
    public static final int COL_CAR_TRANS_DESCRIPTION = 11;
    public static final int COL_CAR_YEAR = 12;

    // ALL KEYS (Contains all KEYS in array of strings)
    public static final String[] ALL_CAR_KEYS = new String[] {
            KEY_ROWID,
            KEY_CAR_CARBON_TAIL_PIPE,
            KEY_CAR_CITY_MPG,
            KEY_CAR_ENGINE_DESCRIPTION,
            KEY_CAR_ENGINE_DISP_LITRES,
            KEY_CAR_FUEL_ANNUAL_COST,
            KEY_CAR_FUEL_TYPE,
            KEY_CAR_HIGHWAY_MPG,
            KEY_CAR_MAKE,
            KEY_CAR_MODEL,
            KEY_CAR_NICK_NAME,
            KEY_CAR_TRANS_DESCRIPTION,
            KEY_CAR_YEAR };

    // Create the Data Base (SQL)
    private static final String CREATE_TABLE_CAR =
            "create table " + TABLE_CAR
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    // TODO: Place your fields here!
                    + KEY_CAR_CARBON_TAIL_PIPE + " real, "
                    + KEY_CAR_CITY_MPG + " real, "
                    + KEY_CAR_ENGINE_DESCRIPTION + " integer, "
                    + KEY_CAR_ENGINE_DISP_LITRES + " real, "
                    + KEY_CAR_FUEL_ANNUAL_COST + " integer, "
                    + KEY_CAR_FUEL_TYPE + " text, "
                    + KEY_CAR_HIGHWAY_MPG + " integer, "
                    + KEY_CAR_MAKE + " text, "
                    + KEY_CAR_MODEL + " text, "
                    + KEY_CAR_NICK_NAME + " text, "
                    + KEY_CAR_TRANS_DESCRIPTION + " text, "
                    + KEY_CAR_YEAR + " integer"

                    // Rest  of creation:
                    + ");";



    // TODO: Setup Route Fields Here

    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)

    // ALL KEYS

    // TODO: Setup Bus Fields Here

    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)

    // ALL KEYS


    // TODO: Setup Skytrain Fields Here

    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)

    // ALL KEYS

    // TODO: Setup Walk Fields Here

    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)

    // ALL KEYS


    // TODO: Setup Utilities Fields Here

    // COLUMN FIELD NUMBERS (0 = KEY_ROWID, 1=...)

    // ALL KEYS


    // TODO: Setup your fields here:
    public static final String KEY_NAME = "name";
    public static final String KEY_STUDENTNUM = "studentnum";
    public static final String KEY_FAVCOLOUR = "favcolour";

    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_NAME = 1;
    public static final int COL_STUDENTNUM = 2;
    public static final int COL_FAVCOLOUR = 3;
    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_STUDENTNUM, KEY_FAVCOLOUR};

    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
                    // TODO: Place your fields here!
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_NAME + " text not null, "
                    + KEY_STUDENTNUM + " integer not null, "
                    + KEY_FAVCOLOUR + " string not null"

                    // Rest  of creation:
                    + ");";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRow(String name, int studentNum, String favColour) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_STUDENTNUM, studentNum);
        initialValues.put(KEY_FAVCOLOUR, favColour);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Add a new set of values to the database.
    public long insertRow(Car car) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CAR_CARBON_TAIL_PIPE,car.getCarbonTailPipe());
        initialValues.put(KEY_CAR_CITY_MPG,car.getCityMPG());
        initialValues.put(KEY_CAR_ENGINE_DESCRIPTION,car.getEngineDescription());
        initialValues.put(KEY_CAR_ENGINE_DISP_LITRES,car.getEngineDispLitres());
        initialValues.put(KEY_CAR_FUEL_ANNUAL_COST,car.getFuelAnnualCost());
        initialValues.put(KEY_CAR_FUEL_TYPE,car.getFuelType());
        initialValues.put(KEY_CAR_HIGHWAY_MPG,car.getHighwayMPG());
        initialValues.put(KEY_CAR_MAKE,car.getMake());
        initialValues.put(KEY_CAR_MODEL,car.getModel());
        initialValues.put(KEY_CAR_NICK_NAME,car.getNickName());
        initialValues.put(KEY_CAR_TRANS_DESCRIPTION,car.getTransDescription());
        initialValues.put(KEY_CAR_YEAR,car.getYear());

        // Insert it into the database.
        return db.insert(TABLE_CAR, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(String DB_TABLE, long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DB_TABLE, where, null) != 0;
    }

    public void deleteAll(String DB_TABLE) {
        Cursor c = getAllRows(DB_TABLE);
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(DB_TABLE,c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows(String DB_TABLE) {
        String where = null;

        Cursor c = null;
        switch (DB_TABLE){
            case TABLE_CAR:
                c = 	db.query(true, DB_TABLE, ALL_CAR_KEYS,
                        where, null, null, null, null, null);
                break;
            default:
                break;
        }

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateRow(long rowId, String name, int studentNum, String favColour) {
        String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, name);
        newValues.put(KEY_STUDENTNUM, studentNum);
        newValues.put(KEY_FAVCOLOUR, favColour);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }



    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
//            _db.execSQL(DATABASE_CREATE_SQL);
            _db.execSQL(CREATE_TABLE_CAR);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
//            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR);

            // Recreate new database:
            onCreate(_db);
        }
    }
}