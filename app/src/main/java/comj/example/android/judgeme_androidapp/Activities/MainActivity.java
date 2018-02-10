package comj.example.android.judgeme_androidapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.facebook.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import comj.example.android.judgeme_androidapp.Fragments.ProfileFragment;
import comj.example.android.judgeme_androidapp.Fragments.PublicoFragment;
import comj.example.android.judgeme_androidapp.Fragments.SearchFragment;
import comj.example.android.judgeme_androidapp.Fragments.Upload1Fragment;
import comj.example.android.judgeme_androidapp.Helpers.Base64Custom;
import comj.example.android.judgeme_androidapp.R;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayoutMainOptionView;
    private LinearLayout linearLayoutMainActivity;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_publico:
                    linearLayoutMainOptionView = findViewById(R.id.linearLayoutMainActivityOptionView);
                    linearLayoutMainOptionView.setVisibility(View.VISIBLE);
                    linearLayoutMainOptionView.getLayoutParams().height = 150;
                    linearLayoutMainOptionView.requestLayout();

                    linearLayoutMainActivity = findViewById(R.id.linearLayoutMainActivity);
                    linearLayoutMainActivity.getLayoutParams().height = 1250;
                    linearLayoutMainActivity.requestLayout();

                    PublicoFragment publicoFragment = new PublicoFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.linearLayoutMainActivity, publicoFragment)
                            .commit();

                    return true;
                case R.id.navigation_amigos:
                    linearLayoutMainOptionView = findViewById(R.id.linearLayoutMainActivityOptionView);
                    linearLayoutMainOptionView.setVisibility(View.VISIBLE);
                    linearLayoutMainOptionView.getLayoutParams().height = 150;
                    linearLayoutMainOptionView.requestLayout();

                    linearLayoutMainActivity = findViewById(R.id.linearLayoutMainActivity);
                    linearLayoutMainActivity.getLayoutParams().height = 1250;
                    linearLayoutMainActivity.requestLayout();

                    return true;
                case R.id.navigation_adiciona:
                    linearLayoutMainOptionView = findViewById(R.id.linearLayoutMainActivityOptionView);
                    linearLayoutMainOptionView.setVisibility(View.VISIBLE);
                    linearLayoutMainOptionView.getLayoutParams().height = 1;
                    linearLayoutMainOptionView.requestLayout();

                    linearLayoutMainActivity = findViewById(R.id.linearLayoutMainActivity);
                    linearLayoutMainActivity.getLayoutParams().height = 1450;
                    linearLayoutMainActivity.requestLayout();

                    Upload1Fragment upload1Fragment = new Upload1Fragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.linearLayoutMainActivity, upload1Fragment)
                            .commit();

                    return true;
                case R.id.navigation_pesquisa:
                    linearLayoutMainOptionView = findViewById(R.id.linearLayoutMainActivityOptionView);
                    linearLayoutMainOptionView.setVisibility(View.VISIBLE);
                    linearLayoutMainOptionView.getLayoutParams().height = 1;
                    linearLayoutMainOptionView.requestLayout();

                    linearLayoutMainActivity = findViewById(R.id.linearLayoutMainActivity);
                    linearLayoutMainActivity.getLayoutParams().height = 1450;
                    linearLayoutMainActivity.requestLayout();

                    SearchFragment searchFragment = new SearchFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.linearLayoutMainActivity, searchFragment)
                            .commit();

                    return true;
                case R.id.navigation_perfil:
                    linearLayoutMainOptionView = findViewById(R.id.linearLayoutMainActivityOptionView);
                    linearLayoutMainOptionView.setVisibility(View.VISIBLE);
                    linearLayoutMainOptionView.getLayoutParams().height = 1;
                    linearLayoutMainOptionView.requestLayout();

                    linearLayoutMainActivity = findViewById(R.id.linearLayoutMainActivity);
                    linearLayoutMainActivity.getLayoutParams().height = 1450;
                    linearLayoutMainActivity.requestLayout();

                    ProfileFragment profileFragment = new ProfileFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.linearLayoutMainActivity, profileFragment)
                            .commit();

                    return true;
            }
            return false;
        }
    };

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /***    CASO O USUÁRIO ESTEJA LOGANDO PELA PRIMEIRA VEZ OU PELO FACE OU PELO GOOGLE...ELE SERÁ MANDADO PARA "CADASTRAR A CONTA"       **/

        Profile profile = Profile.getCurrentProfile();

       db.collection("usuarios")
                .document(Base64Custom.converterBase64(mAuth.getCurrentUser().getEmail()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        if (document != null) {
                            Log.d("email", "Usuário já se registrou: ");
                        }
                    }else{
                        Log.d("email","Usuário precisa se registrar");

                        Intent intent = new Intent(MainActivity.this, CreateAccount1Activity.class);
                        startActivity(intent);
                        finish();

                    }

                }else{
                    Log.d("email","erro ao consultar");
                }

            }
        });

        //incializar o modo de públicação para o mundo
        PublicoFragment publicoFragment = new PublicoFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linearLayoutMainActivity, publicoFragment)
                .commit();

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
            Intent intent = new Intent(MainActivity.this, EditarPerfilActivity.class);
            startActivity(intent);
            //finish();
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
