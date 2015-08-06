package com.despectra.githubrepoview.viewmodel;

/**
 * Abstract presentation model class
 */
public abstract class ItemViewModel<BM> {
    BM mModel;

    public ItemViewModel(BM model) {
        mModel = model;
    }

}
