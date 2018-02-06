package comj.example.android.judgeme_androidapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

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
        PhotoView photoViewPhoto1 = view.findViewById(R.id.photoViewListPostMode1Photo1);
        PhotoView photoViewPhoto2 = view.findViewById(R.id.photoViewListPostMode1Photo2);
        ImageView imageViewLike = view.findViewById(R.id.imageViewListPostMode1QuantidadeTotalLikes);

        //texto nome, nick e descricao
        TextView textViewNomeUsuario = view.findViewById(R.id.textViewListPostMode1NomeUsuario);
        TextView textViewNickUsuario = view.findViewById(R.id.textViewListPostMode1NickUsuario);
        TextView textViewDescricao = view.findViewById(R.id.textViewListPostMode1DescricaoPost);

        //quantidade de likes
        TextView textViewQtdLikesPhoto1 = view.findViewById(R.id.textViewListPostMode1QuantidadeLikesPhoto1);
        TextView textViewQtdLikesPhoto2 = view.findViewById(R.id.textViewListPostMode1QuantidadeLikesPhoto2);
        TextView textViewQtdTotalLikes = view.findViewById(R.id.textViewListPostMode1QuantidadeTotalLikes);

        //quantidade de comentarios
        TextView textViewQtdTotalComments = view.findViewById(R.id.textViewListPostMode1QuantidadeTotalComments);
        TextView textViewVerComments = view.findViewById(R.id.textViewListPostMode1VerComments);
        EditText editTextAddComments = view.findViewById(R.id.editTextListPostMode1AddComments);

        HashMap<String, String> mPostData = new HashMap<>();

        mPostData = usuarioPublicacao.get(position);

        textViewNomeUsuario.setText(mPostData.get("nome"));

        return view;

    }
}
