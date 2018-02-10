package comj.example.android.judgeme_androidapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import comj.example.android.judgeme_androidapp.Helpers.Base64Custom;
import comj.example.android.judgeme_androidapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditarPerfilActivity extends Activity {

    private CircleImageView circleImageViewPhotoPerfil;

    private Uri photoSelecionada;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText editTextNome;
    private EditText editTextNick;
    private EditText editTextBio;
    private EditText editTextTelefone;

    private TextView textViewNome;
    private TextView textViewNick;
    private TextView textViewBio;

    private TextView textViewCancelar;
    private TextView textViewSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        circleImageViewPhotoPerfil = findViewById(R.id.circleImageViewEditarPerfilFotoPerfil);
        circleImageViewPhotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreGaleria();
            }
        });

        editTextTelefone = findViewById(R.id.editTextEditarPerfilMudarTelefone);
        editTextNome = findViewById(R.id.editTextEditarPerfilMudarNome);
        editTextNick = findViewById(R.id.editTextEditarPerfilMudarNickname);
        editTextBio = findViewById(R.id.editTextEditarPerfilMudarBio);

        textViewNome = findViewById(R.id.textViewEditarPerfilNomeUsuario);
        textViewNick = findViewById(R.id.textViewEditarPerfilNickUsuario);
        textViewBio = findViewById(R.id.textViewEditarPerfilAddDescricao);

        textViewCancelar = findViewById(R.id.textViewEditarPerfilCancelar);
        textViewSalvar = findViewById(R.id.textViewEditarPerfilSalvar);

        textViewCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textViewSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarDados(editTextNome.getText().toString(), editTextNick.getText().toString(), editTextBio.getText().toString());
                finish();
            }
        });

        db.collection("usuarios")
                .document(Base64Custom.converterBase64(mAuth.getCurrentUser().getEmail()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        editTextNome.setText(task.getResult().getString("nome_completo"));
                        editTextNick.setText(task.getResult().getString("nickname"));
                        editTextBio.setText(task.getResult().getString("bio"));
                        editTextTelefone.setText(task.getResult().getString("telefone_mascara"));

                        textViewNome.setText(task.getResult().getString("nome_completo"));
                        textViewNick.setText(task.getResult().getString("nickname"));
                        textViewBio.setText(task.getResult().getString("bio"));

                    }
                });
        carregarFotoDePerfil();

        editTextNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editTextNome.getText().length() >= 0) {
                    textViewNome.setText(editTextNome.getText().toString());
                }
            }
        });

        editTextNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editTextNick.getText().length() >= 0) {
                    textViewNick.setText(editTextNick.getText().toString());
                }
            }
        });

        editTextBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editTextBio.getText().length() >= 0) {
                    textViewBio.setText(editTextBio.getText().toString());
                }
            }
        });

    }

    private void salvarDados(String nome, String nick, String bio){

        db.collection("usuarios").document(Base64Custom.converterBase64(mAuth.getCurrentUser().getEmail())).update("nome_completo", nome);
        db.collection("usuarios").document(Base64Custom.converterBase64(mAuth.getCurrentUser().getEmail())).update("nickname", nick);
        db.collection("usuarios").document(Base64Custom.converterBase64(mAuth.getCurrentUser().getEmail())).update("bio", bio);
        storageRef.child("usuarios").child(Base64Custom.converterBase64(mAuth.getCurrentUser().getEmail())).putFile(photoSelecionada);

    }

    private void carregarFotoDePerfil(){
        storageRef.child("usuarios/" + Base64Custom.converterBase64(mAuth.getCurrentUser().getEmail()))
                .getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        try {
                            Uri uri = task.getResult();
                            Picasso.with(getApplicationContext()).load(uri).into(circleImageViewPhotoPerfil);
                        }catch (Exception e){

                        }
                        //Log.d("image ", "foi : " + String.valueOf(uri));

                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 123) {

                try {

                    photoSelecionada = data.getData();
                    //Toast.makeText(getContext(), String.valueOf(imagemSelecionada), Toast.LENGTH_LONG).show();
                    circleImageViewPhotoPerfil.setImageURI(null);
                    //Toast.makeText(getContext(), String.valueOf(imagemSelecionada), Toast.LENGTH_LONG).show();
                    circleImageViewPhotoPerfil.setImageURI(photoSelecionada);
                    storageRef.child("usuarios").child(Base64Custom.converterBase64(mAuth.getCurrentUser().getEmail())).putFile(photoSelecionada);

                } catch (Exception e) {

                    //Toast.makeText(getContext(), "Erro ao selecionar foto", Toast.LENGTH_LONG).show();

                }

            }
        }
    }

    private void abreGaleria(){

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);//abrindo a galeria

        startActivityForResult(Intent.createChooser(intent, "Select an image"), 123);
        //Toast.makeText(getContext(),"Loading", Toast.LENGTH_LONG).show();

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

}
