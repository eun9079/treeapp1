package com.tree.treeapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CyclerReciver extends BroadcastReceiver {

    private static final String TAG = CyclerReciver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String name = intent.getStringExtra(TreeItem.TREE_NAME);
        String nickname = intent.getStringExtra(TreeItem.TREE_NICKNAME);

        Log.d(TAG, "onReceive: " + name);
        Log.d(TAG, "onReceive: " + nickname);
        Log.d(TAG, "onReceive: " + System.currentTimeMillis());

//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
