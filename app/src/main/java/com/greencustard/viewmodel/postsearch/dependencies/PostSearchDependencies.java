package com.greencustard.viewmodel.postsearch.dependencies;

import com.greencustard.data.android.pojo.Comment;
import com.greencustard.data.android.pojo.Post;

import java.util.List;

import io.reactivex.Single;

public interface PostSearchDependencies {

    Single<List<Post>> postsByUserId(int userId);

}
