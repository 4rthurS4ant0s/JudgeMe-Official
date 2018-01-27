package comj.example.android.judgeme_androidapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comj.example.android.judgeme_androidapp.Helpers.SharedPreferencesCreateAccount;
import comj.example.android.judgeme_androidapp.R;

public class CreateAccount2Activity extends Activity {

    private TextView textViewVoltar;
    private TextView textViewCancelar;
    private TextView textViewErro;

    private EditText editTextEmail;
    private EditText editTextSenha;
    private EditText editTextConfirmarSenha;

    private Button buttonAvancar;

    private String email, senha, confirmarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account2);

        textViewErro = findViewById(R.id.textViewCreateAccountStep2MenssagemErro);

        textViewVoltar = findViewById(R.id.textViewCreateAccountStep2Voltar);
        textViewVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount2Activity.this, CreateAccount1Activity.class);
                startActivity(intent);
                overridePendingTransitionExit();
                finish();

            }
        });

        textViewCancelar = findViewById(R.id.textViewCreateAccountStep2Cancelar);
        textViewCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount2Activity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransitionExit();
                finish();

            }
        });

        editTextEmail = findViewById(R.id.editTextCreateAccount2Email);
        editTextSenha = findViewById(R.id.editTextCreateAccount2Senha);
        editTextConfirmarSenha = findViewById(R.id.editTextCreateAccount2ConfirmarSenha);

        buttonAvancar = findViewById(R.id.buttomCreateAccount2Avancar);
        buttonAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = editTextEmail.getText().toString();
                senha = editTextSenha.getText().toString();
                confirmarSenha = editTextConfirmarSenha.getText().toString();

                if(!verificaEmailValido(email)){
                    textViewErro.setText(R.string.hint_step2_erro_email_invalido);
                    textViewErro.setVisibility(View.VISIBLE);
                }else{

                    if(!verificaTamanhoDaSenha(senha)){
                        textViewErro.setText(R.string.hint_step2_erro_tamanho_senha);
                        textViewErro.setVisibility(View.VISIBLE);
                    }else{

                        if(!verificaSeSenhasCoincidem(senha, confirmarSenha)){
                            textViewErro.setText(R.string.hint_step2_erro_cofirma_senha);
                            textViewErro.setVisibility(View.VISIBLE);
                        }else{

                            textViewErro.setVisibility(View.INVISIBLE);

                            SharedPreferencesCreateAccount preferencesUser = new SharedPreferencesCreateAccount(CreateAccount2Activity.this);
                            preferencesUser.salvarUsuarioPreferenciasStep2( email, senha);

                            Intent intent = new Intent(CreateAccount2Activity.this, CreateAccount3Activity.class);
                            startActivity(intent);
                            overridePendingTransitionEnter();
                            finish();

                        }

                    }

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
        Intent intent = new Intent(CreateAccount2Activity.this, CreateAccount1Activity.class);
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

    public boolean verificaEmailValido(String email)
    {
        String expression = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"

                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."

                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"

                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"

                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    public boolean verificaTamanhoDaSenha(String senha)
    {
        if(senha.length() >= 6 && senha.length() <= 18){
            return true;
        }else{
            return false;
        }
    }

    public boolean verificaSeSenhasCoincidem(String senha, String confirmarSenha)
    {
        if(senha.equals(confirmarSenha)){
            return true;
        }else{
            return false;
        }
    }

}
