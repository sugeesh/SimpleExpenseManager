package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by Neo_ on 12/5/2015.
 */
public class PersistentTransactionDAO implements TransactionDAO{
    public static final String TABLE_NAME = "MyTransaction";
    public static final String DATE = "transaction_date";
    public static final String ACCOUNTNO = "transaction_accountNo";
    public static final String EXPENSETYPE = "transaction_expenseType";
    public static final String AMOUNT = "transaction_amount";

    private SQLiteOpenHelper databaseObject = null;     // database connecting object

    public PersistentTransactionDAO(SQLiteOpenHelper sqLiteOpenHelper) {
        this.databaseObject = sqLiteOpenHelper;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        int expense = -1;
        switch (expenseType) {
            case EXPENSE:
                expense = 1;
                break;
            case INCOME:
                expense = 0;
                break;
        }
        SQLiteDatabase db = databaseObject.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/dd/yyyy");
        values.put(DATE, simpleDateFormat.format(date));
        values.put(ACCOUNTNO, accountNo);
        values.put(EXPENSETYPE, expense);
        values.put(AMOUNT, amount);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        String selectQuery = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase databaseReadableObject = databaseObject.getReadableDatabase();
        Cursor cursor = databaseReadableObject.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String date =  cursor.getString(0);
                String accountNo = cursor.getString(1);
                int expenseType = cursor.getInt(2);
                double amount = cursor.getDouble(3);

                ExpenseType expense = null;
                switch (expenseType) {
                    case 1:
                        expense = ExpenseType.EXPENSE;
                        break;
                    case 0:
                        expense = ExpenseType.INCOME;
                        break;
                }

                DateFormat format = new SimpleDateFormat("mm/dd/yyyy");
                Date dateResult = null;
                try {
                    dateResult = format.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Transaction transaction = new Transaction(dateResult,accountNo,expense,amount);
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        databaseReadableObject.close();
        return transactionList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactionList = getAllTransactionLogs();
        int size = transactionList.size();
        if (size <= limit) {
            return transactionList;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactionList.subList(size - limit, size);

    }

}