package comj.example.android.judgeme_androidapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.regex.Pattern;

import comj.example.android.judgeme_androidapp.R;

public class CreateAccount3Activity extends Activity {

    private Spinner spinnerPaises;
    private ArrayAdapter arrayAdapter;

    private TextView textViewVoltar;
    private TextView textViewCancelar;

    private EditText editTextDDD;
    private EditText editTextTelefone;

    private Button buttonAvancar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account3);

        spinnerPaises = findViewById(R.id.spinnerCreateAccount3Paises);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.hint_step3_paises_ddd, R.layout.spinner_items);
        spinnerPaises.setAdapter(arrayAdapter);

        textViewVoltar = findViewById(R.id.textViewCreateAccountStep3Voltar);
        textViewVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount3Activity.this, CreateAccount2Activity.class);
                startActivity(intent);
                overridePendingTransitionExit();
                finish();

            }
        });

        textViewCancelar = findViewById(R.id.textViewCreateAccountStep3Cancelar);
        textViewCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount3Activity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransitionExit();
                finish();

            }
        });

        editTextDDD = findViewById(R.id.editTextCreateAccount3DDD);
        editTextTelefone = findViewById(R.id.editTextCreateAccount3Telefone);

        buttonAvancar = findViewById(R.id.buttomCreateAccount3Avancar);
        buttonAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



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
        Intent intent = new Intent(CreateAccount3Activity.this, CreateAccount2Activity.class);
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

    public boolean verificaTelefone(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() <= 6 || phone.length() >= 18) {
                // if(phone.length() != 10) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

}
