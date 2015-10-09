package io.redshoes.app.newsfeed;

/**
 * Created by vinicius on 08/10/15.
 */
public enum NewsFeedAction {
    LOAD_NEWS("newsfeed/", "GET"),
    SHOUT_NEWS("newsfeed/shout/", "POST");

    public String actionContext;
    public String actionType;

    private NewsFeedAction(String actionContext, String actionType) {
        this.actionContext = actionContext;
        this.actionType = actionType;
    }


}