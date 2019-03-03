package com.greencustard.viewmodel.postsearch.state;

import com.greencustard.data.android.pojo.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DefaultPostSearchState extends PostSearchState {
    public DefaultPostSearchState() {
        super(Optional.<Integer>empty(), Collections.<Post>emptyList());
    }
}
