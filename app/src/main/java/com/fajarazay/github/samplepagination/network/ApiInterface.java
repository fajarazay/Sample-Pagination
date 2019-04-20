package com.fajarazay.github.samplepagination.network;

import com.fajarazay.github.samplepagination.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.fajarazay.github.samplepagination.listener.PaginationScrollListener.ITEM_PER_PAGE;

/**
 * Created by Fajar Septian on 19/04/2019.
 *
 * @Author Fajar Septian
 * @Email fajarajay10@gmail.com
 * @Github https://github.com/fajarazay
 */
public interface ApiInterface {
    @GET("search/users?per_page=" + ITEM_PER_PAGE)
    Call<UserResponse> getUserList(@Query("q") String userName,
                                   @Query("page") int page);
}
