package com.greencustard.viewmodel.postsearch.dependencies;

import com.greencustard.data.android.pojo.User;

import java.util.List;

import io.reactivex.Single;

public interface PostSearchDependenciesInterface {

    Single<List<User>> allUsers();

    void onSelectUser(int selectedUserId);

}
