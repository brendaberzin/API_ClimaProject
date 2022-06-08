package com.example.projetoapi_brendaroncon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private EditText cidadeInput;
    private TextView tpAtual;
    private TextView tpMin;
    private TextView tpMax;
    private TextView nmCidade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cidadeInput = findViewById(R.id.cidadeInput);
        tpAtual = findViewById(R.id.tempAtual);
        tpMin = findViewById(R.id.tempMin);
        tpMax = findViewById(R.id.tempMax);
        nmCidade = findViewById(R.id.nomeCidade);
        if (getSupportLoaderManager().getLoader(0) != null){
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    public void buscarInfos(View view){
        String queryString = cidadeInput.getText().toString();
        InputMethodManager inputManager = (InputMethodManager);
        getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        ConnectivityManager conMan = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(conMan != null){
            networkInfo = conMan.getActiveNetworkInfo();
        }
        if(networkInfo != null && networkInfo.isConnected() && queryString.length() != 0){
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            cidadeInput.setText(" ");
            nmCidade.setText("Carregando");
        }
        else{
            if(queryString.length() == 0) {
                cidadeInput.setText(" ");
                nmCidade.setText("Informe uma cidade");
            } else {
                cidadeInput.setText(" ");
                nmCidade.setText("Verifique sua conexão");
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if(args != null) {
            queryString = args.getString("queryString")
        }
        return new CarregaTemp(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itensArray = jsonObject.getJSONArray("itens");
            int i = 0;
            String tempAtual = null;
            String tempMin = null;
            String tempMax = null;
            String nomeCidade = null;

            while( i< itensArray.length() &&
                    (tempAtual == null && tempMin == null && tempMax == null && nomeCidade == null)) {
                JSONObject  = new JSONObject(data);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}