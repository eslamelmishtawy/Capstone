package com.example.android.chattous.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ConfigurationSyncAdapterService extends Service {

    private static ConfigurationSyncAdapter syncAdapter = null;
    private static final Object syncAdapterLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();

        /*
         * Create the sync adapter as a singleton.
         * Set the sync adapter as syncable
         * Disallow parallel syncs
         */
        synchronized (syncAdapterLock) {
            if (syncAdapter == null) {
                syncAdapter = new ConfigurationSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    /**
     * Return an object that allows the system to invoke
     * the sync adapter.
     *
     */
    @Override
    public IBinder onBind(final Intent intent) {
        /*
         * Get the object that allows external processes
         * to call onPerformSync(). The object is created
         * in the base class code when the SyncAdapter
         * constructors call super()
         */
        return syncAdapter.getSyncAdapterBinder();
    }
}