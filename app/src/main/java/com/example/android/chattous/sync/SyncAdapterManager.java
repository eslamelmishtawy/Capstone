package com.example.android.chattous.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.android.chattous.R;

import static android.content.Context.ACCOUNT_SERVICE;

class SyncAdapterManager {

    private static final String TAG = SyncAdapterManager.class.getSimpleName();
    private final String authority;
    private final String type;

    private Account account;
    private Context context;

    SyncAdapterManager(final Context context) {
        this.context = context;

        type = context.getString(R.string.account_type);
        authority = context.getString(R.string.authority);
        account = new Account(context.getString(R.string.app_name), type);
    }

    @SuppressWarnings ("MissingPermission")
    void beginPeriodicSync(final long updateConfigInterval) {
        Log.d(TAG, "beginPeriodicSync() called with: updateConfigInterval = [" +
                updateConfigInterval + "]");

        final AccountManager accountManager = (AccountManager) context
                .getSystemService(ACCOUNT_SERVICE);

        if (!accountManager.addAccountExplicitly(account, null, null)) {
            account = accountManager.getAccountsByType(type)[0];
        }

        setAccountSyncable();

        ContentResolver.addPeriodicSync(account, context.getString(R.string.authority),
                Bundle.EMPTY, updateConfigInterval);

        ContentResolver.setSyncAutomatically(account, authority, true);
    }

    void syncImmediately() {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        ContentResolver.requestSync(account, authority, settingsBundle);
    }

    private void setAccountSyncable() {
        if (ContentResolver.getIsSyncable(account, authority) == 0) {
            ContentResolver.setIsSyncable(account, authority, 1);
        }
    }

}