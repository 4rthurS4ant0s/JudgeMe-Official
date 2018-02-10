package comj.example.android.judgeme_androidapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import comj.example.android.judgeme_androidapp.Helpers.Base64Custom;
import comj.example.android.judgeme_androidapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by arthu on 10/02/2018.
 */

public class CustomListViewSearch extends BaseAdapter {

    private Context mContext;
    private ArrayList<HashMap<String, String>> usuarioSearch;
    private static LayoutInflater inflater = null;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CustomListViewSearch(Context context, ArrayList<HashMap<String, String>> data) {

        try {
            mContext = context;
            usuarioSearch = data;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }catch (Exception e){

        }

    }

    @Override
    public int getCount() {
        return usuarioSearch.size();
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

        final View view = inflater.inflate(R.layout.list_search, null);

        final HashMap<String, String> mSearchData;

        mSearchData = usuarioSearch.get(position);

        CircleImageView circleImageView = view.findViewById(R.id.circleImageViewListSearchFotoPerfil);
        final TextView textViewNome = view.findViewById(R.id.textViewListSearchNomeUsuario);
        final TextView textViewNick = view.findViewById(R.id.textViewListSearchNickUsuario);

        db.collection("searchListEmails")
                .document("usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                int newPosition = position;
                if(newPosition > Integer.parseInt(mSearchData.get("qtd"))){
                    newPosition = Integer.parseInt(mSearchData.get("qtd"));
                }else{
                    newPosition += 1;
                }

                String email = task.getResult().getString(String.valueOf(newPosition));

                //Log.d("emails",String.valueOf(newPosition));
                //Log.d("emails",task.getResult().getString(String.valueOf(newPosition)));
                db.collection("usuarios")
                        .document(Base64Custom.converterBase64(email))
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        textViewNome.setText(task.getResult().getString("nome_completo"));
                        textViewNick.setText(task.getResult().getString("nickname"));

                    }
                });

            }
        });

        return view;
    }
}
