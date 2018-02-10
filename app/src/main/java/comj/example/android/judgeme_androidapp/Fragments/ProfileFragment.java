package comj.example.android.judgeme_androidapp.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    private CircleImageView circleImageViewPhotoPerfil;

    private Uri photoSelecionada;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView textViewNome;
    private TextView textViewNick;
    private TextView textViewBio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        circleImageViewPhotoPerfil = view.findViewById(R.id.circleImageViewProfileFotoPerfil);
        circleImageViewPhotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreGaleria();
            }
        });

        textViewNome = view.findViewById(R.id.textViewProfileNomeUsuario);
        textViewNick = view.findViewById(R.id.textViewProfileNickUsuario);
        textViewBio = view.findViewById(R.id.textViewProfileAddDescricao);

        db.collection("usuarios")
                .document(Base64Custom.converterBase64(mAuth.getCurrentUser().getEmail()))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                textViewNome.setText(task.getResult().getString("nome_completo"));
                textViewNick.setText(task.getResult().getString("nickname"));
                textViewBio.setText(task.getResult().getString("bio"));

            }
        });

        carregarFotoDePerfil(view);

        return view;
    }

    private void carregarFotoDePerfil(final View view){
        storageRef.child("usuarios/" + Base64Custom.converterBase64(mAuth.getCurrentUser().getEmail()))
                .getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        try {
                            Uri uri = task.getResult();
                            Picasso.with(view.getContext()).load(uri).into(circleImageViewPhotoPerfil);
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

}
