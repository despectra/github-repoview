package com.despectra.githubrepoview.viewmodel;

import com.despectra.githubrepoview.models.Branch;

/**
 * Branch presentation model
 */
public class BranchViewModel extends ItemViewModel<Branch> {

    public BranchViewModel(Branch model) {
        super(model, Branch.class);
    }

    public String getName() {
        return mModel.getName();
    }
}
