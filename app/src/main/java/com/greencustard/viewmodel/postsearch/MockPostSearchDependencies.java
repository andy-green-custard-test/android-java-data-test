package com.greencustard.viewmodel.postsearch;

import com.google.common.collect.Lists;
import com.greencustard.data.android.pojo.User;

import java.util.List;

import io.reactivex.Single;

public class MockPostSearchDependencies implements com.greencustard.viewmodel.postsearch.dependencies.PostSearchDependenciesInterface {

    private User mUser1 = new User(1,"1","1","1","1","1");
    private User mUser2 = new User(2,"2","2","2","2","2");
    private User mUser3 = new User(3,"3","3","3","3","3");

    @Override
    public Single<List<User>> allUsers() {
            return Single.just((List<User>) Lists.newArrayList(mUser1,mUser2,mUser3));
    }

    @Override
    public void onSelectUser(int selectedUserId) {

    }
}
