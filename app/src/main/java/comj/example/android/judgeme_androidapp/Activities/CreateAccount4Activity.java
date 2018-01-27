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
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import comj.example.android.judgeme_androidapp.Helpers.SharedPreferencesCreateAccount;
import comj.example.android.judgeme_androidapp.R;

import static android.content.ContentValues.TAG;

public class CreateAccount4Activity extends Activity {

    private TextView textViewVoltar;
    private TextView textViewCancelar;
    private TextView textViewReenviarCodigo;
    private TextView textViewMensagemPrincipal;
    private TextView textViewErro;

    private ProgressBar progressBar;

    //private EditText editTextCodigoConfimacao;

    private Button buttonAvancar;

    private SharedPreferencesCreateAccount sharedPreferencesCreateAccount;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account4);

        sharedPreferencesCreateAccount = new SharedPreferencesCreateAccount(this);
        textViewMensagemPrincipal = findViewById(R.id.textViewCreateAccountStep4MenssagemPricipal);
        textViewMensagemPrincipal.setText(R.string.hint_step4_message);
        textViewMensagemPrincipal.append(" "+sharedPreferencesCreateAccount.getDadosUsuario().get("telefone_mascara"));
        enviarToken();

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

//                Intent intent = new Intent(CreateAccount4Activity.this, CreateAccount5Activity.class);
//                startActivity(intent);
//                overridePendingTransitionEnter();
//                finish();

            }
        });

        textViewReenviarCodigo = findViewById(R.id.textViewCreateAccountStep4ReenviarCodigo);
        textViewReenviarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enviarToken();

            }
        });

        progressBar = findViewById(R.id.simpleProgressBarCreateAccountStep4);
        progressBar.setVisibility(View.VISIBLE);

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

    private void enviarToken(){

        SharedPreferencesCreateAccount sharedPreferencesCreateAccount = new SharedPreferencesCreateAccount(this);

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                textViewErro = findViewById(R.id.textViewCreateAccountStep4MenssagemErro);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                    textViewErro.setText(R.string.hint_step4_codigo_invalido);
                    textViewErro.setVisibility(View.VISIBLE);

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    textViewErro.setText(R.string.hint_step4_codigo_tempo_excedido);
                    textViewErro.setVisibility(View.VISIBLE);
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId + " "+ token);
                //Toast.makeText(getApplicationContext(),String.valueOf(verificationId),Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later

                // ...
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                sharedPreferencesCreateAccount.getDadosUsuario().get("telefone"),        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,      // Unit of timeout
                this,           // Activity (for callback binding)
                mCallbacks);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            mAuth.signOut();

                            Intent intent = new Intent(CreateAccount4Activity.this, CreateAccount5Activity.class);
                            startActivity(intent);
                            overridePendingTransitionEnter();
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}
