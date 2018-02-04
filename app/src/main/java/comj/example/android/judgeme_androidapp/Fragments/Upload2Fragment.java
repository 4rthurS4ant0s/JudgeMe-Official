package comj.example.android.judgeme_androidapp.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

import comj.example.android.judgeme_androidapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Upload2Fragment extends Fragment {


    public Upload2Fragment() {
        // Required empty public constructor
    }

    private Spinner spinnerCategorias;
    private ArrayAdapter arrayAdapter;

    private LinearLayout linearLayoutOption1;
    private LinearLayout linearLayoutOption2;

    private PhotoView photoUpload;

    //private Uri photoSelecionada;
    private Uri photo1;
    private Uri photo2;

    private TextView textViewDescricao;
    private TextView textViewPhoto1;
    private TextView textViewPhoto2;

    private EditText editTextCategoria;

    private RadioGroup radioGroupVisualizacao;
    private RadioButton radioButtonAmigos;
    private RadioButton radioButtonPublico;

    private Button buttonPublicar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload2, container, false);

        spinnerCategorias = view.findViewById(R.id.spinnerUpload2Categorias);
        arrayAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.hint_upload2_categorias, R.layout.spinner_items);
        spinnerCategorias.setAdapter(arrayAdapter);

        linearLayoutOption1 = view.findViewById(R.id.linearLayoutUpload2Option1);
        linearLayoutOption2 = view.findViewById(R.id.linearLayoutUpload2Option2);

        textViewPhoto1 = view.findViewById(R.id.textViewUpload2Option1);
        textViewPhoto2 = view.findViewById(R.id.textViewUpload2Option2);

        photoUpload = view.findViewById(R.id.photoViewUpload2Fotos);

        /***-------------------ALTERNANDO ENTRE A FOTO 1 E 2-----------------------****/
        final Bundle mBundle;
        mBundle = getArguments();
        photo1 = Uri.parse(mBundle.getString("photo1"));
        photoUpload.setImageURI(photo1);

        linearLayoutOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textViewPhoto1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                textViewPhoto2.setTextColor(getResources().getColor(R.color.hintColor));

                photo1 = Uri.parse(mBundle.getString("photo1"));
                photoUpload.setImageURI(photo1);


            }
        });

        linearLayoutOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textViewPhoto1.setTextColor(getResources().getColor(R.color.hintColor));
                textViewPhoto2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                photo2 = Uri.parse(mBundle.getString("photo2"));
                photoUpload.setImageURI(photo2);

            }
        });
        /***-------------------ALTERNANDO ENTRE A FOTO 1 E 2-----------------------****/

        return view;
    }

}
