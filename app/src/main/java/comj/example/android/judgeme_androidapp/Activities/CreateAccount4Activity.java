package comj.example.android.judgeme_androidapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import comj.example.android.judgeme_androidapp.Helpers.SharedPreferencesCreateAccount;
import comj.example.android.judgeme_androidapp.R;

public class CreateAccount4Activity extends Activity {

    private TextView textViewVoltar;
    private TextView textViewCancelar;
    private TextView textViewMensagemPrincipal;

    private EditText editTextCodigoConfimacao;

    private Button buttonAvancar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account4);

        SharedPreferencesCreateAccount sharedPreferencesCreateAccount = new SharedPreferencesCreateAccount(this);

        textViewVoltar = findViewById(R.id.textViewCreateAccountStep4Voltar);
        textViewVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount4Activity.this, CreateAccount3Activity.class);
                startActivity(intent);
                overridePendingTransitionExit();
                finish();

            }
        });

        textViewCancelar = findViewById(R.id.textViewCreateAccountStep4Cancelar);
        textViewCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount4Activity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransitionExit();
                finish();

            }
        });

        buttonAvancar = findViewById(R.id.buttomCreateAccount4Avancar);
        buttonAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount4Activity.this, CreateAccount5Activity.class);
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
        Intent intent = new Intent(CreateAccount4Activity.this, CreateAccount3Activity.class);
        startActivity(intent);
        overridePendingTransitionExit();
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
