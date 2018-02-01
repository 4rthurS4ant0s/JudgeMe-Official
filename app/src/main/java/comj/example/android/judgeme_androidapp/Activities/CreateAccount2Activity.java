package comj.example.android.judgeme_androidapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryListenOptions;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comj.example.android.judgeme_androidapp.Helpers.Base64Custom;
import comj.example.android.judgeme_androidapp.Helpers.SharedPreferencesCreateAccount;
import comj.example.android.judgeme_androidapp.R;

public class CreateAccount2Activity extends Activity {

    private TextView textViewVoltar;
    private TextView textViewCancelar;
    private TextView textViewErro;

    private EditText editTextEmail;
    private EditText editTextSenha;
    private EditText editTextConfirmarSenha;

    private ProgressBar progressBar;

    private Button buttonAvancar;

    private String email, senha, confirmarSenha;

    private FirebaseAuth mFireAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account2);

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

        progressBar = findViewById(R.id.simpleProgressBarCreateAccountStep2);

        editTextEmail = findViewById(R.id.editTextCreateAccount2Email);
        editTextSenha = findViewById(R.id.editTextCreateAccount2Senha);
        editTextConfirmarSenha = findViewById(R.id.editTextCreateAccount2ConfirmarSenha);

        if(mFireAuth.getCurrentUser() != null){//caso o usuário esteja vindo do google ou facebook
            editTextEmail.setText(mFireAuth.getCurrentUser().getEmail());
            email = mFireAuth.getCurrentUser().getEmail();
            editTextEmail.setEnabled(false);

            editTextSenha.setText("default");
            senha = editTextSenha.getText().toString();
            editTextSenha.setEnabled(false);

            editTextConfirmarSenha.setText("default");
            confirmarSenha = editTextConfirmarSenha.getText().toString();
            editTextConfirmarSenha.setEnabled(false);

            progressBar.setVisibility(View.VISIBLE);
            Log.d("credenciais",email+senha+confirmarSenha);
            proximaEtapa(email, senha, confirmarSenha);
        }

        buttonAvancar = findViewById(R.id.buttomCreateAccount2Avancar);
        buttonAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                textViewErro.setVisibility(View.INVISIBLE);

                email = editTextEmail.getText().toString();
                senha = editTextSenha.getText().toString();
                confirmarSenha = editTextConfirmarSenha.getText().toString();

                DocumentReference docRef = db.collection("usuarios").document(Base64Custom.converterBase64(email));
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()) {
                                if (document != null) {
                                    Log.d("email", "Email já sendo usado: " + task.getResult().getData());
                                    textViewErro.setText(R.string.hint_step2_erro_email_em_uso);
                                    textViewErro.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }else{
                                Log.d("email","Email inutilazado");
                                progressBar.setVisibility(View.VISIBLE);
                                proximaEtapa(email, senha, confirmarSenha);
                            }

                        }else{
                            Log.d("email","erro ao consultar");
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });

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

    public void proximaEtapa(String email, String senha, String confirmarSenha){

        progressBar = findViewById(R.id.simpleProgressBarCreateAccountStep2);
        textViewErro = findViewById(R.id.textViewCreateAccountStep2MenssagemErro);

        if(!verificaEmailValido(email)){
            textViewErro.setText(R.string.hint_step2_erro_email_invalido);
            textViewErro.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }else{

            if(!verificaTamanhoDaSenha(senha)){
                textViewErro.setText(R.string.hint_step2_erro_tamanho_senha);
                textViewErro.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }else{

                if(!verificaSeSenhasCoincidem(senha, confirmarSenha)){
                    textViewErro.setText(R.string.hint_step2_erro_cofirma_senha);
                    textViewErro.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }else{

                    progressBar.setVisibility(View.INVISIBLE);

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

}
