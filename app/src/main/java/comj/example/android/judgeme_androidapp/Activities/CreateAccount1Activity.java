package comj.example.android.judgeme_androidapp.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Calendar;

import comj.example.android.judgeme_androidapp.R;

public class CreateAccount1Activity extends Activity {

    private Spinner spinnerGeneros;
    private ArrayAdapter arrayAdapter;

    private EditText editTextNome;
    private EditText editTextSobrenome;
    private EditText editTextNascimento;

    private TextView textViewCancelar;

    private Button buttonAvancar;

    private String nome, sobrenome, nomeCompleto, genero, nascimento;

    private int pYear;
    private int pMonth;
    private int pDay;
    /** This integer will uniquely define the dialog to be used for displaying date picker.*/
    static final int DATE_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account1);

        spinnerGeneros = findViewById(R.id.spinnerCreateAccount1Generos);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.hint_step1_generos, R.layout.spinner_items);
        spinnerGeneros.setAdapter(arrayAdapter);

        editTextNascimento = findViewById(R.id.editTextCreateAccount1Nascimento);
        /**-----------MASCARANDO O FORMATO DA EDITTEXT DA DATA DE NASCIMENTO-------------**/
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(editTextNascimento, smf);
        editTextNascimento.addTextChangedListener(mtw);
        /**-----------MASCARANDO O FORMATO DA EDITTEXT DA DATA DE NASCIMENTO-------------**/

        /**-----------------------------MOSTRANDO O CALENDÁRIO PARA QUE O USUÁRIO POSSA SELECIONAR O DIA DO NASCIMENTO--------------------------------------**/
        editTextNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        /** Get the current date */
        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);

        /** Display the current date in the TextView */
        updateDisplay();
        /**-----------------------------MOSTRANDO O CALENDÁRIO PARA QUE O USUÁRIO POSSA SELECIONAR O DIA DO NASCIMENTO--------------------------------------**/

        editTextNome = findViewById(R.id.editTextCreateAccount1PrimeiroNome);
        editTextSobrenome = findViewById(R.id.editTextCreateAccount1Sobrenome);

        textViewCancelar = findViewById(R.id.textViewCreateAccountStep1Cancelar);
        textViewCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount1Activity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransitionExit();
                finish();

            }
        });

        buttonAvancar = findViewById(R.id.buttomCreateAccount1Avancar);
        buttonAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nome = editTextNome.getText().toString();
                sobrenome = editTextSobrenome.getText().toString();

                nome = nome.trim();
                sobrenome = sobrenome.trim();

                genero = spinnerGeneros.getSelectedItem().toString();
                nascimento = editTextNascimento.getText().toString();

                if(nome.isEmpty() || sobrenome.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nome ou sobrenome incompleto(s)",Toast.LENGTH_SHORT).show();
                }else{
                    nomeCompleto = nome+" "+sobrenome;

                    Intent intent = new Intent(CreateAccount1Activity.this, CreateAccount2Activity.class);
                    startActivity(intent);
                    overridePendingTransitionEnter();
                    finish();

                }

            }
        });

    }

    /**-----------------------------MOSTRANDO O CALENDÁRIO PARA QUE O USUÁRIO POSSA SELECIONAR O DIA DO NASCIMENTO--------------------------------------**/
    /** Callback received when the user "picks" a date in the dialog */
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;
                    updateDisplay();
                }
            };

    /** Updates the date in the TextView */
    private void updateDisplay() {

        String day = String.valueOf(pDay);
        String month = String.valueOf(pMonth+1);
        String year = String.valueOf(pYear);
        if(pDay<10){
            day = "0"+day;
        }
        if(pMonth+1<10){
            month = "0"+month;
        }
        editTextNascimento.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(day).append("/")
                        .append(month).append("/")
                        .append(year).append(" "));

    }

    /** Create a new dialog for date picker */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);
        }
        return null;
    }
    /**-----------------------------MOSTRANDO O CALENDÁRIO PARA QUE O USUÁRIO POSSA SELECIONAR O DIA DO NASCIMENTO--------------------------------------**/

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CreateAccount1Activity.this, LoginActivity.class);
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
