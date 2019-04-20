package com.fajarazay.github.samplepagination.feature;

import android.content.Context;

import com.fajarazay.github.samplepagination.model.UserItem;
import com.fajarazay.github.samplepagination.model.UserResponse;
import com.fajarazay.github.samplepagination.network.ApiFactory;
import com.fajarazay.github.samplepagination.network.ApiInterface;
import com.fajarazay.github.samplepagination.util.CheckInternetConnection;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fajarazay.github.samplepagination.listener.PaginationScrollListener.ITEM_PER_PAGE;

/**
 * Created by Fajar Septian on 19/04/2019.
 *
 * @Author Fajar Septian
 * @Email fajarajay10@gmail.com
 * @Github https://github.com/fajarazay
 */
public class MainPresenter {
    private MainView mainView;
    private Context context;

    private ArrayList<UserItem> userItemsList;

    public MainPresenter(Context context, MainView mainView) {
        this.mainView = mainView;
        this.context = context;
    }

    private void checkInternetConnection(String userName, int page) {
        if (CheckInternetConnection.isConnectedInternet(context)) {
            requestUsersGithubApi(userName, page);
        } else {
            mainView.showNetworkWarningDialog();
        }
    }

    void loadMoreUserList(String userName, int nextPage) {
        checkInternetConnection(userName, nextPage);
    }

    void getUserList(String userName, int pageStart) {
        if (userName.isEmpty()) {
            mainView.showMessageEmptyInputUsername();
        } else {
            checkInternetConnection(userName, pageStart);
        }
    }

    private void requestUsersGithubApi(String userName, final int page) {
        ApiInterface apiInterface = ApiFactory.create();
        final Call<UserResponse> userResponseCall;

        userResponseCall = apiInterface.getUserList(userName, page);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.message().equals("OK")) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            userItemsList = response.body().getItems();

                            if (page == 1) {
                                mainView.showData(userItemsList, totalPage(response.body().getTotalCount()));
                                if (userItemsList.size() < ITEM_PER_PAGE) {
                                    mainView.disableLoadingFooter();
                                    if (userItemsList.size() == 0) {
                                        mainView.showMessageEmptyResult();
                                    }
                                }
                            } else {
                                mainView.showLoadMoreData(userItemsList);
                            }
                        }
                    } else {
                        mainView.showMessageErrorRequest();
                    }
                } else if (response.message().equals("Forbidden")) {
                    mainView.showErrorLimitRequest();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                mainView.showFailureMessage(t.getLocalizedMessage());
            }
        });
    }

    private int totalPage(int totalCount) {
        int addPage = 0;
        if (totalCount <= 10) {
            return 1;
        } else {
            if (totalCount % 10 != 0) {
                addPage = 1;
            }
            return (totalCount / 10) + addPage;
        }
    }

}
