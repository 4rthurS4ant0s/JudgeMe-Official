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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import comj.example.android.judgeme_androidapp.R;

public class EsqueceuSenhaActivity extends Activity {

    private TextView textViewMensagem;
    private TextView textViewErro;
    private TextView textViewCancelar;

    private ProgressBar progressBar;

    private EditText editTextEmail;

    private Button buttonEnviar;

    private String email;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueceu_senha);

        progressBar = findViewById(R.id.simpleProgressBarEsqueceuSenha);

        textViewCancelar = findViewById(R.id.textViewEsqueceuSenhaCancelar);
        textViewCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EsqueceuSenhaActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransitionExit();
                finish();

            }
        });

        buttonEnviar = findViewById(R.id.buttomEsqueceuSenhaEnviar);
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    editTextEmail = findViewById(R.id.editTextEsqueceuSenhaEmail);
                    email = editTextEmail.getText().toString();

                    enviarEmailParaMudarPassword(email);

                    progressBar.setVisibility(View.VISIBLE);

                }catch (Exception e){

                    progressBar.setVisibility(View.INVISIBLE);

                    textViewErro = findViewById(R.id.textViewEsqueceuSenhaErro);
                    textViewErro.setText(R.string.hint_esqueceu_senha_digite_um_email);
                    textViewErro.setVisibility(View.VISIBLE);

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
        Intent intent = new Intent(EsqueceuSenhaActivity.this, LoginActivity.class);
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

    private void enviarEmailParaMudarPassword(String e){
        if(mAuth != null) {
            mAuth.sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        textViewErro = findViewById(R.id.textViewEsqueceuSenhaErro);
                        textViewErro.setText(R.string.hint_esqueceu_senha_email_mudar_senha);
                        textViewErro.setVisibility(View.VISIBLE);

                        progressBar.setVisibility(View.INVISIBLE);

                    } else {
                        Log.d("dVerif", "erro 1");
                    }

                }
            });
        }else{
            Log.d("dVerif", "erro 2");
        }
    }

}
