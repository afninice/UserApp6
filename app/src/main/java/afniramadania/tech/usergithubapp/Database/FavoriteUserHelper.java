package afniramadania.tech.usergithubapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import afniramadania.tech.usergithubapp.Model.UserModel;

import static afniramadania.tech.usergithubapp.Database.DatabaseContract.FavoriteColoumn.ID;
import static afniramadania.tech.usergithubapp.Database.DatabaseContract.FavoriteColoumn.IMAGE;
import static afniramadania.tech.usergithubapp.Database.DatabaseContract.FavoriteColoumn.TABLE_FAVORITE_NAME;
import static afniramadania.tech.usergithubapp.Database.DatabaseContract.FavoriteColoumn.TITLE;

public class FavoriteUserHelper {

    private static final String DB_TABLE =TABLE_FAVORITE_NAME ;
    private static FavoriteDatabaseHelper favoriteDbHelper;
    private static FavoriteUserHelper INST;
    private static SQLiteDatabase db;

    public FavoriteUserHelper(Context context){
        favoriteDbHelper = new FavoriteDatabaseHelper(context);
    }

    public static FavoriteUserHelper getInst(Context context){
        if (INST == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INST == null) {
                    INST= new FavoriteUserHelper(context);
                }
            }
        }
        return INST;
    }

    public void open() throws SQLException {
        db = favoriteDbHelper.getWritableDatabase();
//        db = favoriteDbHelper.getReadableDatabase();
    }

    public void close() {
        favoriteDbHelper.close();

        if (db.isOpen())
            db.close();
    }

    public ArrayList<UserModel> getAllFavorites(){
        ArrayList<UserModel> arrayList = new ArrayList<>();
        Cursor cursor = db.query(DB_TABLE,null,
                null,
                null,
                null,
                null,
                ID+ " ASC",
                null);
        cursor.moveToFirst();
        UserModel UserModel;
        if (cursor.getCount()>0){
            do{
                UserModel = new UserModel();
                UserModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                UserModel.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                UserModel.setAvatarUrl(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                arrayList.add(UserModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        } cursor.close();
        return arrayList;
    }

    public long favoriteInsert (UserModel UserModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,UserModel.getId());
        contentValues.put(TITLE,UserModel.getLogin());
        contentValues.put(IMAGE,UserModel.getAvatarUrl());

        return db.insert(DB_TABLE,null,contentValues);
    }

    public int favoriteDelete(String title) {
        return db.delete(TABLE_FAVORITE_NAME, TITLE + " = '" + title + "'", null);
    }
    public Cursor cursorFavoriteGet() {
        return db.query(DB_TABLE, null, null, null, null, null, ID+" ASC");
    }
    public Cursor cursorFavoriteGetId(String id) {
        return db.query(DB_TABLE, null
                , ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }
    public int favoriteDeleteProvider(String id) {
        return db.delete(TABLE_FAVORITE_NAME, ID+ "=?",new String[]{id});
    }
    public int favoriteUpdateProvider(String id, ContentValues values) {
        return db.update(DB_TABLE, values, ID + " =?", new String[]{id});
    }
    public long favoriteInsertProvider(ContentValues values) {
        return db.insert(DB_TABLE, null, values);
    }       

}
