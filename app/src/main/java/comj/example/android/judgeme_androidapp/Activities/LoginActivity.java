package comj.example.android.judgeme_androidapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.nio.channels.AcceptPendingException;

import comj.example.android.judgeme_androidapp.R;

public class LoginActivity extends Activity {

    private TextView textViewEsqueceuSenha;
    private TextView textViewLoginCriarConta;
    private TextView textViewEmailVerificar;

    private ProgressBar progressBar;

    private EditText editTextEmail;
    private EditText editTextSenha;

    private String email, senha;

    private CheckBox checkBoxMostrarSenha;

    private Button buttonLogar;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioJaLogado();

        textViewEsqueceuSenha = findViewById(R.id.textViewLoginEsqueceuSenha);
        textViewEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, EsqueceuSenhaActivity.class);
                startActivity(intent);
                overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left);
                finish();

            }
        });

        textViewLoginCriarConta = findViewById(R.id.textViewLoginCriarConta);
        textViewLoginCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, CreateAccount1Activity.class);
                startActivity(intent);
                overridePendingTransition(R.animator.slide_from_right, R.animator.slide_to_left);
                finish();

            }
        });

        editTextEmail = findViewById(R.id.editTextLoginEmail);
        editTextSenha = findViewById(R.id.editTextLoginSenha);

        progressBar = findViewById(R.id.simpleProgressBarLogin);

        buttonLogar = findViewById(R.id.buttomLogin);
        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = editTextEmail.getText().toString();
                senha = editTextSenha.getText().toString();

                try {//caso email e senha estejam vazios ele nao avanca

                    mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful() == true) {//caso o usuario exista

                                final FirebaseUser user = mAuth.getCurrentUser();

                                if (user.isEmailVerified() == true) {//caso o usuario esteja com email verificado

                                    Log.d("dVerif", "verificado");

                                    textViewEmailVerificar = findViewById(R.id.textViewLoginEmailVerificar);
                                    textViewEmailVerificar.setVisibility(View.INVISIBLE);

                                    progressBar.setVisibility(View.VISIBLE);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    overridePendingTransitionEnter();
                                    finish();

                                } else {//caso o usuario nao esteja com email verificado

                                    Log.d("dVerif", "n verificado");
                                    enviarEmailDeVerificacao();

                                }

                                textViewEmailVerificar = findViewById(R.id.textViewLoginEmailVerificar);
                                textViewEmailVerificar.setText(R.string.hint_login_email_verificar);
                                textViewEmailVerificar.setVisibility(View.INVISIBLE);

                                progressBar.setVisibility(View.INVISIBLE);

                            } else {//caso o usuario nao exista

                                textViewEmailVerificar = findViewById(R.id.textViewLoginEmailVerificar);
                                textViewEmailVerificar.setText(R.string.hint_login_credenciais_invalidas);
                                textViewEmailVerificar.setVisibility(View.VISIBLE);

                                progressBar.setVisibility(View.INVISIBLE);

                            }


                        }
                    });

                }catch (Exception e) {//caso o usuario nao informe o email ou senha

                    textViewEmailVerificar = findViewById(R.id.textViewLoginEmailVerificar);
                    textViewEmailVerificar.setText(R.string.hint_login_credenciais_invalidas);
                    textViewEmailVerificar.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.INVISIBLE);

                }

            }
        });

        checkBoxMostrarSenha = findViewById(R.id.checkBoxLoginMostarSenha);
        /**-----------------ALTERNANDO A VISIBILIDADE DA SENHA-----------------**/
        checkBoxMostrarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int start, end;
                if(checkBoxMostrarSenha.isChecked()){
                    //Toast.makeText(getApplicationContext(),"  marcado ",Toast.LENGTH_SHORT).show();
                    start = editTextSenha.getSelectionStart();
                    end = editTextSenha.getSelectionEnd();
                    editTextSenha.setTransformationMethod(null);
                    editTextSenha.setSelection(start,end);
                }else{
                    //Toast.makeText(getApplicationContext(),"desmarcado",Toast.LENGTH_SHORT).show();
                    start = editTextSenha.getSelectionStart();
                    end = editTextSenha.getSelectionEnd();
                    editTextSenha.setTransformationMethod(new PasswordTransformationMethod());;
                    editTextSenha.setSelection(start,end);
                }
            }
        });
        /**-----------------ALTERNANDO A VISIBILIDADE DA SENHA-----------------**/

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

    private void enviarEmailDeVerificacao(){
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    textViewEmailVerificar = findViewById(R.id.textViewLoginEmailVerificar);
                    textViewEmailVerificar.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.INVISIBLE);

                }else{
                    Log.d("dVerif","erro");
                }


            }
        });
    }

    private void usuarioJaLogado(){
        if(mAuth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransitionEnter();
            finish();
        }
    }

}
