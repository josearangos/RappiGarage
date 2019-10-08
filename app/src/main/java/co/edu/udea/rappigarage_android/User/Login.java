package co.edu.udea.rappigarage_android.User;

import androidx.appcompat.app.AppCompatActivity;
import co.edu.udea.rappigarage_android.MainActivity;
import co.edu.udea.rappigarage_android.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import co.edu.udea.rappigarage_android.User.API.PhoneNumber;
import co.edu.udea.rappigarage_android.User.API.Token;
import co.edu.udea.rappigarage_android.User.API.UserService;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;

public class Login extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://us-central1-rappi-garage.cloudfunctions.net/widgets/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void requestVerificationCode(String phone) {
        // Put loading text in btnLogin
        Button btnLogin = findViewById(R.id.btnLogin);
        String btnLoginText = btnLogin.getText().toString();
        btnLogin.setText("Cargando...");

        UserService userService = retrofit.create(UserService.class);
        PhoneNumber phoneNumber = new PhoneNumber(phone);
        Call<Token> call = userService.getVerificationCode(phoneNumber);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                System.out.println("Response is" + response.code());
                if(response.code() == 200) {
                    // Save token in shared preferences
                    System.out.println("Response is" + response.body());
                    Token token = response.body();
                    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", token.getToken());
                    editor.apply();
                    // Go to Main Activity
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    btnLogin.setText(btnLoginText);
                    Toast.makeText(getApplicationContext(), "Ooops, Algo ha ido mal! Inténtalo más tarde.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void handleLogin(View v) {
        EditText phoneNumberInput = findViewById(R.id.phoneNumberInput);
        String phoneNumber = phoneNumberInput.getText().toString();
        requestVerificationCode(phoneNumber);
    }
}