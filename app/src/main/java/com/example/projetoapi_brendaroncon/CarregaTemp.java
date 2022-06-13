package com.example.projetoapi_brendaroncon;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class CarregaTemp extends AsyncTaskLoader<String> {
    private String mQueryString;
    CarregaTemp(Context context, String queryString){
        super(context);
    mQueryString = queryString;
    }
        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }
        @Nullable
        @Override
        public String loadInBackground() {
            return NetworkUtils.buscarInfos(mQueryString);
        }
    }

