package com.despectra.githubrepoview.viewmodel;

import com.despectra.githubrepoview.models.User;

/**
 * USer presentation model
 */
public class UserViewModel extends ItemViewModel<User> {

    public UserViewModel(User model) {
        super(model);
    }

    public String getLogin() {
        return mModel.getLogin();
    }

    public String getName() {
        return mModel.getName();
    }

    public String getShortInfo() {
        StringBuilder infoBuilder = new StringBuilder();
        if(!mModel.getCompany().isEmpty()) {
            infoBuilder.append(mModel.getCompany());
            infoBuilder.append(", ");
        }
        if(!mModel.getLocation().isEmpty()) {
            infoBuilder.append(mModel.getLocation());
        }
        return infoBuilder.length() > 0 ? infoBuilder.toString() : "<no data>";
    }

}
