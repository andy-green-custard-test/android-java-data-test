package com.greencustard.viewmodel.postsearch;

import com.google.common.collect.Lists;
import com.greencustard.data.android.pojo.User;

import java.util.List;

import io.reactivex.Single;

class MockPostSearchDependencies implements com.greencustard.viewmodel.postsearch.dependencies.PostSearchDependenciesInterface {

    private User mUser1 = new User(1,"1","1","1","1","1");
    private User mUser2 = new User(2,"2","2","2","2","2");
    private User mUser3 = new User(3,"3","3","3","3","3");

    @Override
    public Single<List<User>> usersByName(String name) {
        if (name.equals("")) {
            return Single.just((List<User>) Lists.newArrayList(mUser1,mUser2,mUser3));
        }
        else if (name.equals("1")) {
            return Single.just((List<User>) Lists.newArrayList(mUser1));
        }
        else {
            return Single.just((List<User>)Lists.<User>newArrayList());
        }
    }

    @Override
    public void onSelectUser(int selectedUserId) {

    }
}
