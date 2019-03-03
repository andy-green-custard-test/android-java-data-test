package com.greencustard.viewmodel.postsearch.state;


import com.greencustard.data.android.pojo.Post;
import com.greencustard.viewmodel.usersearch.state.UserSearchState;

import java.util.List;
import java.util.Optional;

public class UserSelectedPostSearchState extends PostSearchState {

    public UserSelectedPostSearchState(int selectedUserId, List<Post> posts) {
        super(Optional.of(selectedUserId),posts);
    }
}
