package com.example.gullermin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gullermin.DataModal;
import com.example.gullermin.R;
import com.example.gullermin.RetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText nameEdt, usernameEdt;
    private Button postDataBtn;
    private TextView responseTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdt = findViewById(R.id.idEdtName);
        usernameEdt = findViewById(R.id.idEdtUsername);
        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);


        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameEdt.getText().toString().isEmpty() && usernameEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Porfavor ingrese ambos datos", Toast.LENGTH_SHORT).show();
                    return;
                }

                postData(nameEdt.getText().toString(), usernameEdt.getText().toString());
            }
        });
    }

    private void postData(String name, String username) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/users/")

                .addConverterFactory(GsonConverterFactory.create())

                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);


        DataModal modal = new DataModal(name, username);


        Call<DataModal> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                // mensaje que sale cuando tenemos respuesta de la API
                Toast.makeText(MainActivity.this, "Su registro ha sido añadido", Toast.LENGTH_LONG).show();

                // cuando obtengamos la llamada de la API
                // se dejan vacios los EditText
                usernameEdt.setText("");
                nameEdt.setText("");

                // we are getting response from our body
                // and passing it to our modal class.
                DataModal responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Codigo de Repuesta : " + response.code() + "\nNombre : " + responseFromAPI.getName() + "\n" + "Usuario : " + responseFromAPI.getUsername();

                // below line we are setting our
                // string to our text view.
                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }
}