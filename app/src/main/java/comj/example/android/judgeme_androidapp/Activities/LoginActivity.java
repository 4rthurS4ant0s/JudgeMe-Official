package comj.example.android.judgeme_androidapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.nio.channels.AcceptPendingException;

import comj.example.android.judgeme_androidapp.R;

public class LoginActivity extends Activity {

    private TextView tvEsqueceuSenha;
    private TextView tvLoginCriarConta;

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

            }
        });

    }

}
