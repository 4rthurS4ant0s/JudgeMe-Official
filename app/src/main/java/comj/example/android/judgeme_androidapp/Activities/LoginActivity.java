package comj.example.android.judgeme_androidapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.AcceptPendingException;

import comj.example.android.judgeme_androidapp.R;

public class LoginActivity extends Activity {

    private TextView tvEsqueceuSenha;
    private TextView tvLoginCriarConta;

    private EditText editTextEmail;
    private EditText editTextSenha;

    private CheckBox checkBoxMostrarSenha;

    private Button buttonLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvEsqueceuSenha = findViewById(R.id.textViewLoginEsqueceuSenha);
        tvLoginCriarConta = findViewById(R.id.textViewLoginCriarConta);

        tvLoginCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, CreateAccount1Activity.class);
                startActivity(intent);
                overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left);
                finish();

            }
        });

        buttonLogar = findViewById(R.id.buttomLogin);
        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransitionEnter();
                finish();

            }
        });

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.animator.slide_from_left, R.animator.slide_to_right);
    }

}
