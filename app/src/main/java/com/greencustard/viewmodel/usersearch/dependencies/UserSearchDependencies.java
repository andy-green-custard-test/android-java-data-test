package com.greencustard.viewmodel.usersearch.dependencies;

import com.greencustard.data.android.pojo.User;

import java.util.List;

import io.reactivex.Single;

public interface UserSearchDependencies {

    Single<List<User>> allUsers();

    void onSelectUser(int selectedUserId);

}
