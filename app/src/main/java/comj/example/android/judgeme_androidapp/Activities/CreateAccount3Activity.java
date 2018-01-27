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
import android.widget.Toast;

import java.util.regex.Pattern;

import comj.example.android.judgeme_androidapp.R;

public class CreateAccount3Activity extends Activity {

    private Spinner spinnerPaises;
    private ArrayAdapter arrayAdapter;

    private TextView textViewVoltar;
    private TextView textViewCancelar;

    private EditText editTextDDDregiao;
    private EditText editTextTelefone;

    private Button buttonAvancar;

    private String dddPais, dddRegiao, numero, numeroCompleto, numeroCompletoComMascara;
    private String[] mDDD;
    private ArrayAdapter<String> adapterDDDList;

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

        editTextDDDregiao = findViewById(R.id.editTextCreateAccount3DDD);
        editTextTelefone = findViewById(R.id.editTextCreateAccount3Telefone);

        mDDD = getResources().getStringArray(R.array.hint_get_ddd);
        adapterDDDList = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                mDDD);

        buttonAvancar = findViewById(R.id.buttomCreateAccount3Avancar);
        buttonAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //captando os numeros
            dddPais = mDDD[spinnerPaises.getSelectedItemPosition()];
            dddRegiao = editTextDDDregiao.getText().toString();

            //formatando o DDDregiao para (XX)
            dddRegiao = dddRegiao.replace("(","");
            dddRegiao = dddRegiao.replace(")","");
            dddRegiao = "(" + dddRegiao + ")";

            numero = editTextTelefone.getText().toString();

            if(verificaTelefone(numero)){//pode avançar

                numeroCompletoComMascara = dddPais + dddRegiao + numero;
                numeroCompleto = dddPais + dddRegiao + numero;
                numeroCompleto = numeroCompleto.replace("+","");
                numeroCompleto = numeroCompleto.replace("(","");
                numeroCompleto = numeroCompleto.replace(")","");
                numeroCompleto = numeroCompleto.replace("-","");
                numeroCompleto = numeroCompleto.replace(" ","");
                numeroCompleto = numeroCompleto.replace(".","");
                numeroCompleto = numeroCompleto.replace("N","");
                numeroCompleto = numeroCompleto.replace(",","");
                numeroCompleto = numeroCompleto.replace("*","");

                //Toast.makeText(getApplicationContext(),numeroCompleto,Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),numeroCompletoComMascara, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateAccount3Activity.this, CreateAccount4Activity.class);
                startActivity(intent);
                overridePendingTransitionEnter();
                finish();

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
