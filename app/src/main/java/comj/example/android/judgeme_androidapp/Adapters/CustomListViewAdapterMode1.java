package comj.example.android.judgeme_androidapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    public ArrayList<HashMap<String, String>> publicacaoList;
    public HashMap<String, String> data;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        view = inflater.inflate(R.layout.list_post_mode1, null);

        //fotos
        CircleImageView circleImageViewPhotoPerfil = view.findViewById(R.id.circleImageViewListPostMode1FotoPerfil);
        final PhotoView photoViewPhoto1 = view.findViewById(R.id.photoViewListPostMode1Photo1);
        final PhotoView photoViewPhoto2 = view.findViewById(R.id.photoViewListPostMode1Photo2);
        ImageView imageViewLike = view.findViewById(R.id.imageViewListPostMode1QuantidadeTotalLikes);

        //texto nome, nick e descricao
        final TextView textViewNomeUsuario = view.findViewById(R.id.textViewListPostMode1NomeUsuario);
        final TextView textViewNickUsuario = view.findViewById(R.id.textViewListPostMode1NickUsuario);
        final TextView textViewDescricao = view.findViewById(R.id.textViewListPostMode1DescricaoPost);

        //quantidade de likes
        TextView textViewQtdLikesPhoto1 = view.findViewById(R.id.textViewListPostMode1QuantidadeLikesPhoto1);
        TextView textViewQtdLikesPhoto2 = view.findViewById(R.id.textViewListPostMode1QuantidadeLikesPhoto2);
        TextView textViewQtdTotalLikes = view.findViewById(R.id.textViewListPostMode1QuantidadeTotalLikes);

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

            }
        });

        storageRef.child("publicacoes/" + String.valueOf(position) + "/" + "photo1")
                .getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        Uri uri = task.getResult();
                        Picasso.with(mContext).load(uri).into(photoViewPhoto1);
                        Log.d("image ", "foi : " + String.valueOf(uri));

                    }
                });

        storageRef.child("publicacoes/" + String.valueOf(position) + "/" + "photo2")
                .getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        Uri uri = task.getResult();
                        Picasso.with(mContext).load(uri).into(photoViewPhoto2);
                        Log.d("image ", "foi : " + String.valueOf(uri));

                    }
                });


        return view;

    }
}
