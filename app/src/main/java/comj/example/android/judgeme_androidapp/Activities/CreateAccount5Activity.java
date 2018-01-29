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

public class CreateAccount5Activity extends Activity {

    private TextView textViewCancelar;
    private TextView textViewErro;
    private TextView textViewVoltar;

    private EditText editTextNickname;

    private String nickname;

    private Button buttonFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account5);

        textViewVoltar = findViewById(R.id.textViewCreateAccountStep5Voltar);
        textViewVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount5Activity.this, CreateAccount3Activity.class);
                startActivity(intent);
                overridePendingTransitionExit();
                finish();

            }
        });

        textViewCancelar = findViewById(R.id.textViewCreateAccountStep5Cancelar);
        textViewCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount5Activity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransitionExit();
                finish();

            }
        });

        textViewErro = findViewById(R.id.textViewCreateAccountStep5MenssagemErro);
        editTextNickname = findViewById(R.id.editTextCreateAccount5Nickname);

        buttonFinalizar = findViewById(R.id.buttomCreateAccount5Finalizar);
        buttonFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nickname = editTextNickname.getText().toString();

                if(nickname != null){

                    textViewErro.setVisibility(View.INVISIBLE);
                    SharedPreferencesCreateAccount sharedPreferencesCreateAccount = new SharedPreferencesCreateAccount(getApplicationContext());
                    sharedPreferencesCreateAccount.salvarUsuarioPreferenciasStep5(nickname);

                    sharedPreferencesCreateAccount.salvarDadosDoUsuario();

                    Intent intent = new Intent(CreateAccount5Activity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransitionExit();
                    finish();

                }else{

                    textViewErro.setVisibility(View.VISIBLE);
                    textViewErro.setText(R.string.hint_step5_nickname_vazio);

                }

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
        Intent intent = new Intent(CreateAccount5Activity.this, CreateAccount3Activity.class);
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
