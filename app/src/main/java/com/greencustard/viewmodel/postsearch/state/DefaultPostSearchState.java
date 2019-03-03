package com.greencustard.viewmodel.postsearch.state;

import java.util.Optional;

public class DefaultPostSearchState extends PostSearchState {


    public DefaultPostSearchState(Optional<Integer> selectedUserId) {
        super(selectedUserId);
    }
}
