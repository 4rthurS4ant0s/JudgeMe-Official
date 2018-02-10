package comj.example.android.judgeme_androidapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import comj.example.android.judgeme_androidapp.Adapters.CustomListViewAdapterMode1;
import comj.example.android.judgeme_androidapp.Adapters.CustomListViewSearch;
import comj.example.android.judgeme_androidapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    public ArrayList<HashMap<String, String>> searchList;
    public HashMap<String, String> data;

    private ListView listView;
    private CustomListViewSearch customListViewSearch;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchList = new ArrayList<>();
        data = new HashMap<>();

        db.collection("autoIncrementSearchListEmails")
                .document("usuarios")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                int qtd = Integer.parseInt(task.getResult().getString("quantidade"));

                for(int i = 1; i < qtd ; i++) {
                    data.put("qtd", String.valueOf(qtd - 1));
                    searchList.add(data);
                }
                listView =  view.findViewById(R.id.listViewSearch);

                customListViewSearch = new CustomListViewSearch(getContext(), searchList);
                listView.setAdapter(customListViewSearch);

            }
        });

        return view;
    }

}
