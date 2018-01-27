package comj.example.android.judgeme_androidapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        buttonLogar = findViewById(R.id.buttomLogin);
        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseUser user = mAuth.getCurrentUser();
                email = editTextEmail.getText().toString();
                senha = editTextSenha.getText().toString();

                mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful() == true){

                            if(user.isEmailVerified() == true){

                                Log.d("dVerif","verificado");

                                textViewEmailVerificar = findViewById(R.id.textViewLoginEmailVerificar);
                                textViewEmailVerificar.setVisibility(View.INVISIBLE);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransitionEnter();
                                finish();

                            }else{

                                Log.d("dVerif","n verificado");
                                enviarEmailDeVerificacao();

                            }

                            textViewEmailVerificar = findViewById(R.id.textViewLoginEmailVerificar);
                            textViewEmailVerificar.setText(R.string.hint_login_email_verificar);
                            textViewEmailVerificar.setVisibility(View.INVISIBLE);

                        }else{

                            textViewEmailVerificar = findViewById(R.id.textViewLoginEmailVerificar);
                            textViewEmailVerificar.setText(R.string.hint_login_credenciais_invalidas);
                            textViewEmailVerificar.setVisibility(View.VISIBLE);

                        }


                    }
                });

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
