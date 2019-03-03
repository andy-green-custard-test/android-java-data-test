package com.greencustard.viewmodel.usersearch.state;

import java.util.Optional;

public class DefaultUserSearchState extends UserSearchState {


    public DefaultUserSearchState(Optional<Integer> selectedUserId) {
        super(selectedUserId);
    }
}
