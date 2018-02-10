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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchList = new ArrayList<>();
        data = new HashMap<>();
        for(int i = 0; i < 10; i++) {
            data.put("nome", "arthur");
            searchList.add(data);
        }
        listView =  view.findViewById(R.id.listViewSearch);

        customListViewSearch = new CustomListViewSearch(getContext(), searchList);
        listView.setAdapter(customListViewSearch);

        return view;
    }

}
