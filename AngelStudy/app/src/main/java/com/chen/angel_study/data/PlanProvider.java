package com.chen.angel_study.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static com.chen.angel_study.data.PlanContract.CONTENT_AUTHORITY;
import static com.chen.angel_study.data.PlanContract.PATH_PLANS;
import static com.chen.angel_study.data.PlanContract.PlanEntry;
import static com.chen.angel_study.data.PlanContract.PlanEntry.CONTENT_URI;

public class PlanProvider extends ContentProvider {

    public static final String LOG_TAG = PlanProvider.class.getSimpleName();
    private PlanDpHelper mDbHelper;

    private static final int PLANS = 100;
    private static final int PLAN_ID = 101;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY,PATH_PLANS ,PLANS);
        sUriMatcher.addURI(CONTENT_AUTHORITY,PATH_PLANS + "/#" ,PLAN_ID);

    }

    @Override
    public boolean onCreate() {
        mDbHelper = new PlanDpHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case PLANS:
                cursor = database.query(PlanEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PLAN_ID:

                selection = PlanEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(PlanEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLANS:
                return insertPlan(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertPlan(Uri uri, ContentValues values) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(PlanEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLANS:
                return updatePlan(uri, contentValues, selection, selectionArgs);
            case PLAN_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = PlanEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updatePlan(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updatePlan(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link PetEntry#COLUMN_PET_NAME} key is present,
        // check that the name value is not null.
        if (values.containsKey(PlanEntry.COLUMN_NAME_PLAN)) {
            String name = values.getAsString(PlanEntry.COLUMN_NAME_PLAN);
            if (name == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }

        if (values.containsKey(PlanEntry.COLUMN_NAME_TIME)) {
            String time = values.getAsString(PlanEntry.COLUMN_NAME_TIME);
            if (time == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        return database.update(PlanEntry.TABLE_NAME, values, selection, selectionArgs);
    }


        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {

            SQLiteDatabase database = mDbHelper.getWritableDatabase();

            final int match = sUriMatcher.match(uri);
            switch (match) {
                case PLANS:
                    // Delete all rows that match the selection and selection args
                    return database.delete(PlanEntry.TABLE_NAME, selection, selectionArgs);
                case PLAN_ID:
                    // Delete a single row given by the ID in the URI
                    selection = PlanEntry._ID + "=?";
                    selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                    return database.delete(PlanEntry.TABLE_NAME, selection, selectionArgs);
                default:
                    throw new IllegalArgumentException("Deletion is not supported for " + uri);
            }
        }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}