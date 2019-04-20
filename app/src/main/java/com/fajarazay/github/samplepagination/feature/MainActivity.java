package com.fajarazay.github.samplepagination.feature;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fajarazay.github.samplepagination.R;
import com.fajarazay.github.samplepagination.adapter.UserAdapter;
import com.fajarazay.github.samplepagination.listener.PaginationScrollListener;
import com.fajarazay.github.samplepagination.model.UserItem;
import com.fajarazay.github.samplepagination.util.HandleKeyboard;
import com.fajarazay.github.samplepagination.util.NetworkWarningDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainView {
    private static final int PAGE_START = 1;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<UserItem> userItemList = new ArrayList<>();
    private MainPresenter mainPresenter;
    private EditText editTextUserName;
    private ProgressBar progressBar;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        editTextUserName = findViewById(R.id.edt_username);
        progressBar = findViewById(R.id.progress_bar);

        mainPresenter = new MainPresenter(this, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        initAdapter();

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainPresenter.loadMoreUserList(editTextUserName.getText().toString(),
                                currentPage);
                    }
                }, 1000);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        editTextUserName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    recyclerView.scrollToPosition(0);
                    isLastPage = false;
                    currentPage = PAGE_START;
                    progressBar.setVisibility(View.VISIBLE);
                    mainPresenter.getUserList(editTextUserName.getText().toString().trim(), currentPage);
                    HandleKeyboard.hideSoftKeyboard(v.getContext(), editTextUserName);
                    return true;
                }
                return false;
            }
        });

    }

    private void initAdapter() {
        userAdapter = new UserAdapter(userItemList);
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public void showData(ArrayList<UserItem> userItemsList, int totalCount) {
        TOTAL_PAGES = totalCount;
        userAdapter.setDataList(userItemsList);
        if (currentPage < TOTAL_PAGES) {
            userAdapter.addLoadingFooter();
        } else {
            isLastPage = true;
        }
        hideLoading();
    }

    @Override
    public void showFailureMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        hideLoading();
    }

    @Override
    public void showMessageEmptyResult() {
        Toast.makeText(this, getString(R.string.text_message_empty_result), Toast.LENGTH_SHORT).show();
        hideLoading();
        userAdapter.clear();
    }

    @Override
    public void showErrorLimitRequest() {
        Toast.makeText(this, getString(R.string.text_message_error_limit_request), Toast.LENGTH_SHORT).show();
        hideLoading();
    }

    @Override
    public void showMessageEmptyInputUsername() {
        Toast.makeText(this, getString(R.string.text_empty_username), Toast.LENGTH_SHORT).show();
        editTextUserName.setError("This field is required.");
        editTextUserName.requestFocus();
        hideLoading();
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void disableLoadingFooter() {
        userAdapter.disableLoadingFooter();
    }

    @Override
    public void showNetworkWarningDialog() {
        hideLoading();
        NetworkWarningDialog.showWarning(this, getString(R.string.text_check_internet));
    }

    @Override
    public void showMessageErrorRequest() {
        Toast.makeText(this, getString(R.string.text_message_error_request), Toast.LENGTH_SHORT).show();
        hideLoading();
    }

    @Override
    public void showLoadMoreData(ArrayList<UserItem> userItemsList) {
        userAdapter.removeLoadingFooter();
        isLoading = false;
        hideLoading();
        userAdapter.addAll(userItemsList);
        if (currentPage != TOTAL_PAGES) {
            userAdapter.addLoadingFooter();
        } else {
            isLastPage = true;
        }
    }

}
