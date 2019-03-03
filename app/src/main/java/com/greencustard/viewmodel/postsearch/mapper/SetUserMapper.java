package com.greencustard.viewmodel.postsearch.mapper;

import com.greencustard.data.android.pojo.Post;
import com.greencustard.viewmodel.postsearch.dependencies.PostSearchDependencies;
import com.greencustard.viewmodel.postsearch.intention.PostSearchIntention;
import com.greencustard.viewmodel.postsearch.intention.SetUserIntention;
import com.greencustard.viewmodel.postsearch.state.PostSearchState;
import com.greencustard.viewmodel.postsearch.state.UserSelectedPostSearchState;
import com.greencustard.viewmodel.usersearch.state.UserSearchState;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class SetUserMapper extends PostSearchMapper {


    public SetUserMapper(PostSearchDependencies dependencies) {
        super(dependencies);
    }

    @Override
    public boolean supports(PostSearchIntention intention) {
        return intention instanceof SetUserIntention;
    }

    @Override
    public Single<PostSearchState> map(PostSearchState state, PostSearchIntention intention) {
        final int userId = ((SetUserIntention) intention).getUserId();

        return mDependencies
                .postsByUserId(userId)
                .map(new Function<List<Post>, PostSearchState>() {
                    @Override
                    public PostSearchState apply(List<Post> posts) throws Exception {
                        return new UserSelectedPostSearchState(userId, posts);
                    }
                });
    }
}
