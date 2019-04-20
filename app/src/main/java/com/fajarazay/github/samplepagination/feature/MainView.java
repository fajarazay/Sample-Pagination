package com.fajarazay.github.samplepagination.feature;

import com.fajarazay.github.samplepagination.model.UserItem;

import java.util.ArrayList;

/**
 * Created by Fajar Septian on 19/04/2019.
 *
 * @Author Fajar Septian
 * @Email fajarajay10@gmail.com
 * @Github https://github.com/fajarazay
 */
public interface MainView {
    void showData(ArrayList<UserItem> userItemsList, int totalCount);

    void showFailureMessage(String message);

    void showMessageEmptyResult();

    void showErrorLimitRequest();

    void showMessageEmptyInputUsername();

    void hideLoading();

    void disableLoadingFooter();

    void showNetworkWarningDialog();

    void showMessageErrorRequest();

    void showLoadMoreData(ArrayList<UserItem> userItemsList);
}
