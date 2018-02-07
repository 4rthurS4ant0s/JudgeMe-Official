package comj.example.android.judgeme_androidapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

import comj.example.android.judgeme_androidapp.Adapters.CustomListViewAdapterMode1;
import comj.example.android.judgeme_androidapp.Helpers.Base64Custom;
import comj.example.android.judgeme_androidapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicoFragment extends Fragment {

    public PublicoFragment() {
        // Required empty public constructor
    }

    public ArrayList<HashMap<String, String>> publicacaoList;
    public HashMap<String, String> data;

    private ListView listView;
    private CustomListViewAdapterMode1 customListViewAdapterMode1;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_publico, container, false);

        publicacaoList = new ArrayList<>();

        db.collection("autoIncrementPublicacoes")
                .document("publicacoes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        final int autoIncrement = Integer.parseInt(task.getResult().getString("quantidade"));
                        data = new HashMap<>();

                        Log.d("publicacao","entrando no for");

                        for( int i = (autoIncrement - 1); i >= 0; i--){

                            final int finalI = i;
                            db.collection("publicacoes").document(String.valueOf(i)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    data.put("nome", task.getResult().getString("nome_completo"));
                                    data.put("nick", task.getResult().getString("nickname"));
                                    data.put("descricao", task.getResult().getString("descricao"));

                                    data.put("votosPhoto1", task.getResult().getString("votosPhoto1"));
                                    data.put("votosPhoto2", task.getResult().getString("votosPhoto2"));
                                    data.put("votosTotal", task.getResult().getString("autoIncrementVotos"));

                                    data.put("commentsTotal", task.getResult().getString("qtdComments"));

                                    Log.d("publicacao",String.valueOf(finalI));
                                    publicacaoList.add(data);

                                    if(finalI == 0){
                                        Log.d("publicacao","acabou o for");
                                        listView =  view.findViewById(R.id.listViewPublicoPosts);

                                        customListViewAdapterMode1 = new CustomListViewAdapterMode1(getContext(), publicacaoList);
                                        listView.setAdapter(customListViewAdapterMode1);
                                    }

                                }
                            });

                        }

                    }
                });

        return view;
    }

}
