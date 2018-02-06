package comj.example.android.judgeme_androidapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import comj.example.android.judgeme_androidapp.Adapters.CustomListViewAdapterMode1;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publico, container, false);

        publicacaoList = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            data = new HashMap<>();
            data.put("nome", "Arthur Santos");
            data.put("nick", "@arthursantos");
            data.put("descricao", "Aqui fica a descrição");
            data.put("votosPhoto1", "5.674");
            data.put("votosPhoto2", "53.455");
            data.put("votosTotal", "59.129");
            data.put("commentsTotal", "1.763");
            //Toast.makeText(getContext(), nicks[i], Toast.LENGTH_SHORT).show();
            publicacaoList.add(data);
        }

        listView =  view.findViewById(R.id.listViewPublicoPosts);

        customListViewAdapterMode1 = new CustomListViewAdapterMode1(getContext(), publicacaoList);
        listView.setAdapter(customListViewAdapterMode1);

        return view;
    }

}
