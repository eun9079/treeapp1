package com.tree.treeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TreeListAdapter extends RecyclerView.Adapter {


    private ArrayList<TreeItem> treeItems;

    private TreeItemTools treeItemTools;

    public TreeListAdapter(ArrayList<TreeItem> treeItems, TreeItemTools treeItemTools) {
        this.treeItems = treeItems;
        this.treeItemTools = treeItemTools;
    }

    public void addTreeItem(TreeItem treeItem) {
        treeItems.add(0, treeItem);
        notifyItemInserted(0);
    }


    public void removeTreeItem(int position) {
        treeItems.remove(position);
        notifyItemRemoved(position);
    }

    public class TreeItemView extends RecyclerView.ViewHolder {

        TextView treeName;
        TextView treeNickname;

        Button printQR;

        public TreeItemView(@NonNull View itemView) {
            super(itemView);

            treeName = itemView.findViewById(R.id.tree_name);
            treeNickname = itemView.findViewById(R.id.tree_nickname);
            printQR = itemView.findViewById(R.id.print_qr);

        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_item, parent, false);
        final TreeItemView treeItemView = new TreeItemView(holder);

        treeItemView.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                treeItemTools.onItemClick(treeItemView.getAdapterPosition());
            }
        });
        treeItemView.printQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                treeItemTools.onQRClick(treeItemView.getAdapterPosition());
            }
        });

        treeItemView.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                treeItemTools.onItemLongClick(treeItemView.getAdapterPosition());
                return false;
            }
        });

        return treeItemView;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TreeItem treeItem = treeItems.get(position);

        TreeItemView treeItemView = (TreeItemView) holder;

        treeItemView.treeName.setText(treeItem.getName());
        treeItemView.treeNickname.setText(treeItem.getNickname());

    }

    @Override
    public int getItemCount() {
        return treeItems.size();
    }

    public interface TreeItemTools {
        void onQRClick(int position);

        void onItemClick(int position);

        void onItemLongClick(int position);
    }

}
