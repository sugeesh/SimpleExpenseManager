package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Neo_ on 12/5/2015.
 */
public class DBConnection extends SQLiteOpenHelper {
    // This class will create database and connect it with programme.

    public static final String DATABASE_NAME = "130079C";
    private static final int DATABASE_VERSION = 1;


    public DBConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String MyDatabase = "CREATE TABLE Account ("
                + "Account_no TEXT PRIMARY KEY, Bank TEXT, "
                +"Holder TEXT, balance DOUBLE );";
        db.execSQL(MyDatabase);

        String myDatabase2 = "CREATE TABLE MyTransaction (" +
                "   transaction_date            TEXT     NOT NULL," +
                "   transaction_accountNo       TEXT    NOT NULL," +
                "   transaction_expenseType     INT     NOT NULL," +
                "   transaction_amount    REAL ," +
                "   PRIMARY KEY(transaction_date,transaction_accountNo,expenseType,transaction_amount)," +
                "   FOREIGN KEY(transaction_accountNo) REFERENCES Account(Account_no)" +
                ");";

        db.execSQL(myDatabase2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Account");
        db.execSQL("DROP TABLE IF EXISTS MyTransaction");
        onCreate(db);
    }
}
