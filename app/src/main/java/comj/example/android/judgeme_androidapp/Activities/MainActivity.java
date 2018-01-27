package comj.example.android.judgeme_androidapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import comj.example.android.judgeme_androidapp.R;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_publico:

                    return true;
                case R.id.navigation_amigos:

                    return true;
                case R.id.navigation_adiciona:

                    return true;
                case R.id.navigation_pesquisa:

                    return true;
                case R.id.navigation_perfil:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        sharedPreferences = getSharedPreferences("com.myAppName", MODE_PRIVATE);
        primeiraVez();

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

    private void primeiraVez(){

        if (sharedPreferences.getBoolean("firstRun", true)) {
            //You can perform anything over here. This will call only first time

            //Levar o usuário pra passar pelo processo de editar perfil

            Log.v("TAG","primeira vez");
            editor = sharedPreferences.edit();
            editor.putBoolean("firstRun", false);
            editor.commit();

        }

    }

}