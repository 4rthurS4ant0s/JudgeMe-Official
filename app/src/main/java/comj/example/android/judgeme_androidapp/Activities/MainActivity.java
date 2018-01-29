package comj.example.android.judgeme_androidapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.three_dots, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_configuracao){
            // do something
        }
        if(id == R.id.menu_perfil){
            // do something
        }
        if(id == R.id.menu_logout){
            // do something
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();



            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransitionExit();

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
