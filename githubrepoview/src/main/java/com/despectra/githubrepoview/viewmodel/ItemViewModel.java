package com.despectra.githubrepoview.viewmodel;

import com.despectra.githubrepoview.Utils;
import com.google.gson.Gson;

/**
 * Abstract presentation model class
 */
public abstract class ItemViewModel<BM> {
    BM mModel;
    Class<BM> mModelClass;

    public ItemViewModel(BM model, Class<BM> modelClass) {
        mModel = model;
        mModelClass = modelClass;
    }

    public String serialize() {
        Gson gson = Utils.getDefaultGsonInstance();
        return gson.toJson(mModel, mModelClass);
    }

    BM getModel() {
        return mModel;
    }
}
