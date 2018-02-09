package comj.example.android.judgeme_androidapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import comj.example.android.judgeme_androidapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by arthu on 06/02/2018.
 */

public class CustomListViewAdapterMode1 extends BaseAdapter{

    private Context mContext;
    private ArrayList<HashMap<String, String>> usuarioPublicacao;
    private static LayoutInflater inflater = null;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestore dbUpdate = FirebaseFirestore.getInstance();
    public ArrayList<HashMap<String, String>> publicacaoList;
    public HashMap<String, String> data;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public CustomListViewAdapterMode1(Context context, ArrayList<HashMap<String, String>> data) {

        try {
            mContext = context;
            usuarioPublicacao = data;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }catch (Exception e){

        }

    }

    @Override
    public int getCount() {
        return usuarioPublicacao.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //View view = convertView;
        final View view = inflater.inflate(R.layout.list_post_mode1, null);

        //fotos
        final CircleImageView circleImageViewPhotoPerfil = view.findViewById(R.id.circleImageViewListPostMode1FotoPerfil);
        final PhotoView photoViewPhoto1 = view.findViewById(R.id.photoViewListPostMode1Photo1);
        final PhotoView photoViewPhoto2 = view.findViewById(R.id.photoViewListPostMode1Photo2);
        final CheckBox imageViewLike = view.findViewById(R.id.checkboxListPostMode1QuantidadeTotalLikes);

        //texto nome, nick e descricao
        final TextView textViewNomeUsuario = view.findViewById(R.id.textViewListPostMode1NomeUsuario);
        final TextView textViewNickUsuario = view.findViewById(R.id.textViewListPostMode1NickUsuario);
        final TextView textViewDescricao = view.findViewById(R.id.textViewListPostMode1DescricaoPost);

        //quantidade de likes
        final TextView textViewQtdLikesPhoto1 = view.findViewById(R.id.textViewListPostMode1QuantidadeLikesPhoto1);
        final TextView textViewQtdLikesPhoto2 = view.findViewById(R.id.textViewListPostMode1QuantidadeLikesPhoto2);
        final TextView textViewQtdTotalLikes = view.findViewById(R.id.textViewListPostMode1QuantidadeTotalLikes);

        //quantidade de comentarios
        TextView textViewQtdTotalComments = view.findViewById(R.id.textViewListPostMode1QuantidadeTotalComments);
        TextView textViewVerComments = view.findViewById(R.id.textViewListPostMode1VerComments);
        EditText editTextAddComments = view.findViewById(R.id.editTextListPostMode1AddComments);

        final HashMap<String, String> mPostData;

        mPostData = usuarioPublicacao.get(position);

        db.collection("publicacoes").document(String.valueOf(position)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                textViewNomeUsuario.setText(task.getResult().getString("nome_completo"));
                textViewNickUsuario.setText(task.getResult().getString("nickname"));
                textViewDescricao.setText(task.getResult().getString("descricao"));
                textViewQtdLikesPhoto1.setText(task.getResult().getString("votosPhoto1"));
                textViewQtdLikesPhoto2.setText(task.getResult().getString("votosPhoto2"));
                textViewQtdTotalLikes.setText(task.getResult().getString("autoIncrementVotos"));

            }
        });

        storageRef.child("publicacoes/" + String.valueOf(position) + "/" + "photo1")
                .getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        Uri uri = task.getResult();
                        Picasso.with(mContext).load(uri).into(photoViewPhoto1);
                        //Log.d("image ", "foi : " + String.valueOf(uri));

                    }
                });

        storageRef.child("publicacoes/" + String.valueOf(position) + "/" + "photo2")
                .getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        Uri uri = task.getResult();
                        Picasso.with(mContext).load(uri).into(photoViewPhoto2);
                        //Log.d("image ", "foi : " + String.valueOf(uri));

                    }
                });

        photoViewPhoto1.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {

                podeVotarPhoto1(position, view);
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });

        photoViewPhoto2.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                podeVotarPhoto2(position, view);
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });

        return view;

    }

    private void podeVotarPhoto1(final int position, final View view){

        final CheckBox imageViewLike = view.findViewById(R.id.checkboxListPostMode1QuantidadeTotalLikes);

        db.collection("publicacoes")
                .document(String.valueOf(position))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        final int qtdVotos = Integer.parseInt(task.getResult().getString("autoIncrementVotos"));

                        db.collection("publicacoes")
                                .document(String.valueOf(position))
                                .collection("votos")
                                .document("quem_votou")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @SuppressLint({"ResourceAsColor", "NewApi"})
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        boolean podeVotar = true;
                                        for(int i = 0; i <= qtdVotos; i++) {

                                            String email = task.getResult().getString(String.valueOf(i));
                                            //Log.d("quem votou: ", task.getResult().getString(String.valueOf(i)));
                                            if (mAuth.getCurrentUser().getEmail().equals(email)) {
                                                podeVotar = false;
                                            }

                                            if (i == qtdVotos) {
                                                Log.d("pode votar", String.valueOf(podeVotar) +" "+ mAuth.getCurrentUser().getEmail() +" "+ email);
                                                if(podeVotar == true){
                                                    darLikePhoto1(view, position);
                                                }
                                            }

                                        }

                                    }
                                });

                    }
                });

    }

    private void podeVotarPhoto2(final int position, final View view){

        db.collection("publicacoes")
                .document(String.valueOf(position))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        final int qtdVotos = Integer.parseInt(task.getResult().getString("autoIncrementVotos"));

                        db.collection("publicacoes")
                                .document(String.valueOf(position))
                                .collection("votos")
                                .document("quem_votou")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        boolean podeVotar = true;
                                        for(int i = 0; i <= qtdVotos; i++) {

                                            String email = task.getResult().getString(String.valueOf(i));
                                            //Log.d("quem votou: ", task.getResult().getString(String.valueOf(i)));
                                            if (mAuth.getCurrentUser().getEmail().equals(email)) {
                                                podeVotar = false;
                                            }

                                            if (i == qtdVotos) {
                                                Log.d("pode votar", String.valueOf(podeVotar) +" "+ mAuth.getCurrentUser().getEmail() +" "+ email);
                                                if(podeVotar == true){
                                                    darLikePhoto2(view, position);
                                                }
                                            }

                                        }

                                    }
                                });

                    }
                });

    }

    private void darLikePhoto1(View view, final int position){

        final TextView textViewQtdLikesPhoto1 = view.findViewById(R.id.textViewListPostMode1QuantidadeLikesPhoto1);
        final TextView textViewQtdTotalLikes = view.findViewById(R.id.textViewListPostMode1QuantidadeTotalLikes);

        db.collection("publicacoes")
                .document(String.valueOf(position))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                //Log.d("qtd de votos", task.getResult().getString("votosPhoto1"));
                final int quantidadeDeVotos = Integer.parseInt(task.getResult().getString("votosPhoto1"));
                dbUpdate.collection("publicacoes")
                        .document(String.valueOf(position))
                        .update("votosPhoto1", String.valueOf(quantidadeDeVotos + 1))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Log.d("deu like","deu like");
                        textViewQtdLikesPhoto1.setText(String.valueOf(quantidadeDeVotos + 1));

                    }
                });

                final int quantidadeDeVotosTOTAL = Integer.parseInt(task.getResult().getString("autoIncrementVotos"));
                dbUpdate.collection("publicacoes")
                        .document(String.valueOf(position))
                        .update("autoIncrementVotos", String.valueOf(quantidadeDeVotosTOTAL + 1))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                textViewQtdTotalLikes.setText(String.valueOf(quantidadeDeVotosTOTAL + 1));

                            }
                        });

            }
        });

        db.collection("publicacoes")
                .document(String.valueOf(position))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        final int qtdVotos = Integer.parseInt(task.getResult().getString("autoIncrementVotos"));

                        db.collection("publicacoes")
                                .document(String.valueOf(position))
                                .collection("votos")
                                .document("quem_votou")
                                .update(String.valueOf(qtdVotos + 1), mAuth.getCurrentUser().getEmail())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("voto adicionado","votou");
                            }
                        });
                    }
                });

    }

    private void darLikePhoto2(View view, final int position){

        final TextView textViewQtdLikesPhoto2 = view.findViewById(R.id.textViewListPostMode1QuantidadeLikesPhoto2);
        final TextView textViewQtdTotalLikes = view.findViewById(R.id.textViewListPostMode1QuantidadeTotalLikes);

        db.collection("publicacoes")
                .document(String.valueOf(position))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        //Log.d("qtd de votos", task.getResult().getString("votosPhoto1"));
                        final int quantidadeDeVotos = Integer.parseInt(task.getResult().getString("votosPhoto2"));
                        dbUpdate.collection("publicacoes")
                                .document(String.valueOf(position))
                                .update("votosPhoto2", String.valueOf(quantidadeDeVotos + 1))
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Log.d("deu like","deu like");
                                        textViewQtdLikesPhoto2.setText(String.valueOf(quantidadeDeVotos + 1));

                                    }
                                });

                        final int quantidadeDeVotosTOTAL = Integer.parseInt(task.getResult().getString("autoIncrementVotos"));
                        dbUpdate.collection("publicacoes")
                                .document(String.valueOf(position))
                                .update("autoIncrementVotos", String.valueOf(quantidadeDeVotosTOTAL + 1))
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        textViewQtdTotalLikes.setText(String.valueOf(quantidadeDeVotosTOTAL + 1));

                                    }
                                });

                    }
                });

    }

}
