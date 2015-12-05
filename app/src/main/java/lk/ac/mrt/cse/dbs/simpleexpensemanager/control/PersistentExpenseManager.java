package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.MyApplication;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

/**
 * Created by Neo_ on 12/5/2015.
 */
public class PersistentExpenseManager extends ExpenseManager{

    public PersistentExpenseManager() throws ExpenseManagerException {
        setup();
    }

    public void setup() throws ExpenseManagerException {


        AccountDAO persistenceAccountDAO = new PersistentAccountDAO(MyApplication.getCustomAppContext());
        setAccountsDAO(persistenceAccountDAO);

        TransactionDAO persistantTransactionDAO = new PersistentTransactionDAO(MyApplication.getCustomAppContext());
        setTransactionsDAO(persistantTransactionDAO);

        // dummy data
     //   Account dummyAcct1 = new Account("12345", "Yoda Bank", "Anakin Skywalker", 10000.0);
     //   Account dummyAcct2 = new Account("78945", "Clone BC", "Obi-Wan Kenobi", 80000.0);
      //  getAccountsDAO().addAccount(dummyAcct1);
      //  getAccountsDAO().addAccount(dummyAcct2);

    }
}
