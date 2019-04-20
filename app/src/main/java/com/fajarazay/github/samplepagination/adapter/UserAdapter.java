package com.fajarazay.github.samplepagination.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fajarazay.github.samplepagination.R;
import com.fajarazay.github.samplepagination.model.UserItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fajar Septian on 16/04/2019.
 *
 * @Author Fajar Septian
 * @Email fajarajay10@gmail.com
 * @Github https://github.com/fajarazay
 */
public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private ArrayList<UserItem> userItemList;
    private boolean isLoadingAdded = false;

    public UserAdapter(ArrayList<UserItem> userItemList) {
        this.userItemList = userItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_user, viewGroup, false);
//            Toast.makeText(view.getContext(), "VIEW_TYPE_ITEM", Toast.LENGTH_SHORT).show();

            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_loading, viewGroup, false);
            //Toast.makeText(view.getContext(), "VIEW_TYPE_LOADING", Toast.LENGTH_SHORT).show();

            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        // Toast.makeText(viewHolder.itemView.getContext(), "(getItemViewType(position) "+getItemViewType(position), Toast.LENGTH_SHORT).show();
        switch (getItemViewType(position)) {
            case VIEW_TYPE_ITEM:
                ViewHolder holder = (ViewHolder) viewHolder;
                holder.bindView(position);
                //     Toast.makeText(viewHolder.itemView.getContext(), "onBindViewHolder VIEW_TYPE_ITEM", Toast.LENGTH_SHORT).show();
                break;

            case VIEW_TYPE_LOADING:
                //      Toast.makeText(viewHolder.itemView.getContext(), "onBindViewHolder VIEW_TYPE_LOADING", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public int getItemCount() {
        Log.d(UserAdapter.class.getSimpleName(), "userItemList.size() " + userItemList.size());
        return userItemList == null ? 0 : userItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(UserAdapter.class.getSimpleName(), "userItemList.size()-1 " + userItemList.size());
        Log.d(UserAdapter.class.getSimpleName(), "isLoadingAdded " + isLoadingAdded);
        return (position == userItemList.size() - 1 && isLoadingAdded) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setDataList(ArrayList<UserItem> data) {
        if (userItemList == null) {
            userItemList = new ArrayList<>();
        }
        userItemList.clear();
        userItemList.addAll(data);
        notifyDataSetChanged();
    }

    public void add(UserItem userItem) {
        userItemList.add(userItem);
        notifyItemInserted(userItemList.size() - 1);
    }

    public void addAll(List<UserItem> data) {
        for (UserItem userItem : data) {
            add(userItem);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new UserItem());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = userItemList.size() - 1;
        UserItem userItem = getItem(position);

        if (userItem != null) {
            userItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void disableLoadingFooter() {
        isLoadingAdded = false;
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    private void remove(UserItem userItem) {
        int position = userItemList.indexOf(userItem);
        if (position > -1) {
            userItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private UserItem getItem(int position) {
        return userItemList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewUserName;
        private ImageView imageViewAvatar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.txtUserName);
            imageViewAvatar = itemView.findViewById(R.id.imgAvatar);
        }

        void bindView(int position) {
            UserItem userItem = userItemList.get(position);
            Glide.with(itemView).load(userItem.getAvatarUrl()).into(imageViewAvatar);
            textViewUserName.setText(userItem.getLogin());
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
