package comj.example.android.judgeme_androidapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import comj.example.android.judgeme_androidapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicoFragment extends Fragment {


    public PublicoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publico, container, false);
    }

}
