package comj.example.android.judgeme_androidapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comj.example.android.judgeme_androidapp.R;

public class CreateAccount5Activity extends Activity {

    private TextView textViewCancelar;

    private Button buttonFinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account5);

        textViewCancelar = findViewById(R.id.textViewCreateAccountStep5Cancelar);
        textViewCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount5Activity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransitionExit();
                finish();

            }
        });

        buttonFinalizar = findViewById(R.id.buttomCreateAccount5Finalizar);
        buttonFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount5Activity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransitionExit();
                finish();

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
        Intent intent = new Intent(CreateAccount5Activity.this, CreateAccount4Activity.class);
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
