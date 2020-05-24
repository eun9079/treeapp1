package com.tree.treeapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TreeListAdapter.TreeItemTools {

    private final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView tree_list;

    private TreeListAdapter treeListAdapter;

    private ArrayList<TreeItem> treeItems;

    private androidx.appcompat.widget.Toolbar toolbar;

    private static final int ADD = -1;
    private static final int PERMISSION_REQUEST = 1;

    private TreeModel treeModel;

    private boolean requestAdd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate");

        treeModel = new TreeModel(this);
        treeItems = treeModel.getTreeList();

        tree_list = findViewById(R.id.tree_list);
        treeListAdapter = new TreeListAdapter(treeItems,
                this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        tree_list.setAdapter(treeListAdapter);
        tree_list.setLayoutManager(linearLayoutManager);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getPermission();

        try {
            Intent tree = getIntent();
            String name = tree.getStringExtra(TreeItem.TREE_NAME);
            String nickname = tree.getStringExtra(TreeItem.TREE_NICKNAME);
            int cycle = tree.getIntExtra(TreeItem.TREE_CYCLE, -1);
            double[] address = tree.getDoubleArrayExtra(TreeItem.TREE_ADDRESS);
            Log.e(TAG, " 인텐트 : " + name + " " + nickname + " " + cycle + " " + address);

            long id = treeModel.insert(name, nickname, cycle, address);
            TreeItem treeItem = new TreeItem(id, name, nickname, cycle, address);
            treeListAdapter.addTreeItem(treeItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.e(TAG, " onStart");
//        Intent tree = getIntent();
//        if (tree != null && requestAdd) {
//            requestAdd = false;
//            String name = tree.getStringExtra(TreeItem.TREE_NAME);
//            String nickname = tree.getStringExtra(TreeItem.TREE_NICKNAME);
//            //    int cycle = tree.getIntExtra(TreeItem.TREE_CYCLE, -1);
//            //      double[] address = tree.getDoubleArrayExtra(TreeItem.TREE_ADDRESS);
//            Log.d(TAG, "onStart: " + name);
//            Log.d(TAG, "onStart: " + nickname);
//            Log.d(TAG, "onStart: " + address);
//            Log.d(TAG, "onStart: " + address[0]);
//            Log.d(TAG, "onStart: " + address[1]);
//            long id = treeModel.insert(name, nickname, cycle, address);
            //           TreeItem treeItem = new TreeItem(id, name, nickname, cycle, address);
//            treeListAdapter.addTreeItem(treeItem);
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_tool, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.QR: {
                startActivity(new Intent(this, DetectQRActivity.class));
                return true;
            }
            case R.id.tree_add: {
                requestAdd = true;
//                startActivityForResult(new Intent(this, TreeAddActivity.class), ADD);
                startActivity(new Intent(this, TreeAddActivity.class));
                return true;
            }
            default: {
//                startActivity(new Intent(this, ));
                return true;
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        Log.d(TAG, "onActivityResult: " + requestCode);
        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, "onActivityResult: " + requestCode);
//        if (requestCode == ADD && resultCode == RESULT_OK) {
//        }
    }

    @Override
    public void onQRClick(int position) {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {


    }

    private void getPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CAMERA
                    },
                    PERMISSION_REQUEST);
        }
    }

}
