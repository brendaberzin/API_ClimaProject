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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private EditText cidadeInput;
    private TextView tpAtual;
    private TextView tpMin;
    private TextView tpMax;
    private TextView nmCidade;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cidadeInput = findViewById(R.id.cidadeInput);
        tpAtual = findViewById(R.id.tempAtual);
        tpMin = findViewById(R.id.tempMin);
        tpMax = findViewById(R.id.tempMax);
        nmCidade = findViewById(R.id.nomeCidade);
        button1 = findViewById(R.id.button1);
        if (getSupportLoaderManager().getLoader(0) != null){
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }
    public void buscarInfos(View view){
        String queryString = cidadeInput.getText().toString();
        InputMethodManager inputManager = (InputMethodManager)
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
            queryString = args.getString("queryString");
        }
        return new CarregaTemp(this, queryString);
    }
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itensArray = jsonObject.getJSONArray("main");
            int i = 0;
            Double tempAtual = null;
            Double tempMin = null;
            Double tempMax = null;
            String nomeCidade = null;

            while( i< itensArray.length() &&
                    (tempAtual == null && tempMin == null && tempMax == null && nomeCidade == null)) {
                try {
                    JSONObject object = itensArray.getJSONObject(i);
                    tempAtual = object.getDouble("temp");
                    tempMin = object.getDouble("temp_min");
                    tempMax = object.getDouble("temp_max");
                    nomeCidade = object.getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i++;
            }
            tpAtual.setText(tempAtual.toString());
            tpMin.setText(tempMin.toString());
            tpMax.setText(tempMax.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }
}