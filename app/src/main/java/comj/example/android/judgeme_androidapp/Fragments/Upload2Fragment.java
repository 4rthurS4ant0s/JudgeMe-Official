package comj.example.android.judgeme_androidapp.Fragments;


import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import comj.example.android.judgeme_androidapp.Helpers.Base64Custom;
import comj.example.android.judgeme_androidapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Upload2Fragment extends Fragment {


    public Upload2Fragment() {
        // Required empty public constructor
    }

    private Spinner spinnerCategorias;
    private ArrayAdapter arrayAdapter;

    private LinearLayout linearLayoutOption1;
    private LinearLayout linearLayoutOption2;

    private PhotoView photoUpload;

    //private Uri photoSelecionada;
    private Uri photo1;
    private Uri photo2;

    private TextView textViewDescricao;
    private TextView textViewPhoto1;
    private TextView textViewPhoto2;
    private TextView textViewErro;

    private ProgressBar progressBar;

    private EditText editTextDescricao;

    private RadioGroup radioGroupVisualizacao;
    private RadioButton radioButtonAmigos;
    private RadioButton radioButtonPublico;

    private Button buttonPublicar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_upload2, container, false);

        //gerando o spinner de categorias
        spinnerCategorias = view.findViewById(R.id.spinnerUpload2Categorias);
        arrayAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.hint_upload2_categorias, R.layout.spinner_items);
        spinnerCategorias.setAdapter(arrayAdapter);

        //modo de view 1 e 2
        linearLayoutOption1 = view.findViewById(R.id.linearLayoutUpload2Option1);
        linearLayoutOption2 = view.findViewById(R.id.linearLayoutUpload2Option2);

        textViewPhoto1 = view.findViewById(R.id.textViewUpload2Option1);
        textViewPhoto2 = view.findViewById(R.id.textViewUpload2Option2);

        editTextDescricao = view.findViewById(R.id.editTextUpload2Descricao);

        //setando o modo público como default
        radioGroupVisualizacao = view.findViewById(R.id.radioGroupUpload2Visualizacao);
        radioButtonAmigos = view.findViewById(R.id.radioButtonUpload2Amigos);
        radioButtonPublico = view.findViewById(R.id.radioButtonUpload2Publico);
        radioButtonPublico.setChecked(true);

        photoUpload = view.findViewById(R.id.photoViewUpload2Fotos);

        progressBar = view.findViewById(R.id.simpleProgressBarUpload2);

        /***-------------------ALTERNANDO ENTRE A FOTO 1 E 2-----------------------****/
        final Bundle mBundle;
        mBundle = getArguments();
        photo1 = Uri.parse(mBundle.getString("photo1"));
        photo2 = Uri.parse(mBundle.getString("photo2"));
        photoUpload.setImageURI(photo1);

        linearLayoutOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textViewPhoto1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                textViewPhoto2.setTextColor(getResources().getColor(R.color.hintColor));

                photo1 = Uri.parse(mBundle.getString("photo1"));
                photoUpload.setImageURI(photo1);


            }
        });

        linearLayoutOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textViewPhoto1.setTextColor(getResources().getColor(R.color.hintColor));
                textViewPhoto2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                photo2 = Uri.parse(mBundle.getString("photo2"));
                photoUpload.setImageURI(photo2);

            }
        });
        /***-------------------ALTERNANDO ENTRE A FOTO 1 E 2-----------------------****/

        buttonPublicar = view.findViewById(R.id.buttonUpload2Publicar);
        buttonPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //publicar
                String descricao = editTextDescricao.getText().toString();
                if(descricao.equals(null)){
                    descricao = "";
                }

                String categoria = spinnerCategorias.getSelectedItem().toString();

                String modoVisualizacao;
                int id = radioGroupVisualizacao.getCheckedRadioButtonId();
                if(id == R.id.radioButtonUpload2Amigos){
                    modoVisualizacao = radioButtonAmigos.getText().toString();
                }else{
                    modoVisualizacao = radioButtonPublico.getText().toString();
                }

                progressBar.setVisibility(View.VISIBLE);
                publicar(descricao, categoria, modoVisualizacao, photo1, photo2, view);

            }
        });

        return view;
    }

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore dbPublicar = FirebaseFirestore.getInstance();
    private FirebaseFirestore dbAutoIncrementar = FirebaseFirestore.getInstance();
    private DocumentReference mReference;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    //método para pegar publicar seguindo o processo:
    //primeiro : pega o autoIcrement das publicações
    //segundo : salvando os dados da publicação
    private void publicar(final String descricao, final String categoria, final String modoVisualizacao, final Uri photo1, final Uri photo2, final View view){

        String emailUsuario = mAuth.getCurrentUser().getEmail();
        db.collection("usuarios")
                .document(Base64Custom.converterBase64(emailUsuario))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        progressBar = view.findViewById(R.id.simpleProgressBarUpload2);
                        textViewErro = view.findViewById(R.id.textViewUpload2MenssagemErro);

                        final String email = mAuth.getCurrentUser().getEmail();

                        final String autoIncrementVotos = "0";
                        String votosPhoto1 = "0";
                        String votosPhoto2 = "0";

                        final HashMap<String, String> dadosPost = new HashMap<>();

                        dadosPost.put("email", email);
                        dadosPost.put("descricao", descricao);
                        dadosPost.put("categoria", categoria);
                        dadosPost.put("modoVisualizacao", modoVisualizacao);
                        dadosPost.put("autoIncrementVotos", "0");
                        dadosPost.put("votosPhoto1", "0");
                        dadosPost.put("votosPhoto2", "0");
                        dadosPost.put("qtdComments","0");
                        dadosPost.put("email", task.getResult().getString("email"));
                        dadosPost.put("nickname", task.getResult().getString("nickname"));
                        dadosPost.put("nome_completo", task.getResult().getString("nome_completo"));

                        db.collection("autoIncrementPublicacoes")
                                .document("publicacoes")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        final int autoIncrement = Integer.parseInt(task.getResult().getString("quantidade"));
                                        final HashMap<String, String> autoIncrementar = new HashMap<>();
                                        autoIncrementar.put("quantidade", String.valueOf(autoIncrement + 1));

                                        dbPublicar.collection("publicacoes")
                                                .document(String.valueOf(autoIncrement))
                                                .set(dadosPost)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if(task.isSuccessful()){

                                                            storageRef.child("publicacoes").child(String.valueOf(autoIncrement)).child("photo1").putFile(photo1);
                                                            storageRef.child("publicacoes").child(String.valueOf(autoIncrement)).child("photo2").putFile(photo2);
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            textViewErro.setText(R.string.hint_upload2_sucesso);
                                                            textViewErro.setVisibility(View.VISIBLE);

                                                            dbAutoIncrementar.collection("autoIncrementPublicacoes")
                                                                    .document("publicacoes").set(autoIncrementar);

                                                            updateUI();

                                                        }else{
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            textViewErro.setVisibility(View.VISIBLE);
                                                        }

                                                    }
                                                });

                                        Log.d("publish",descricao+" "+categoria+" "+modoVisualizacao+" "+String.valueOf(photo1)+" "+String.valueOf(photo2)+" "+String.valueOf(autoIncrement));

                                    }
                                });

                    }
                });

    }

    private void updateUI(){

        PublicoFragment publicoFragment = new PublicoFragment();

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.linearLayoutMainActivity, publicoFragment);
        fragmentTransaction.commit();

    }

}
