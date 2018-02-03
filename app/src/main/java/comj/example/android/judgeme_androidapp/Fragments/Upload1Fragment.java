package comj.example.android.judgeme_androidapp.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;

import comj.example.android.judgeme_androidapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Upload1Fragment extends Fragment {

    public Upload1Fragment() {
        // Required empty public constructor
    }

    private LinearLayout linearLayoutOption1;
    private LinearLayout linearLayoutOption2;

    private TextView textViewPhoto1;
    private TextView textViewPhoto2;
    private TextView textViewErro;

    private PhotoView photoUpload;
    private ImageView photoBackEndClick;

    private Uri photoSelecionada;
    private Uri photo1;
    private Uri photo2;

    private boolean photoUm = true;

    private Button buttonAvancar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload1, container, false);

        linearLayoutOption1 = view.findViewById(R.id.linearLayoutUpload1Option1);
        linearLayoutOption2 = view.findViewById(R.id.linearLayoutUpload1Option2);

        textViewPhoto1 = view.findViewById(R.id.textViewUpload1Option1);
        textViewPhoto2 = view.findViewById(R.id.textViewUpload1Option2);
        textViewErro = view.findViewById(R.id.textViewUpload1MenssagemErro);

        photoUpload = view.findViewById(R.id.imageViewUpload1Fotos);

        /***-------------------ALTERNANDO ENTRE A FOTO 1 E 2-----------------------****/
        linearLayoutOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textViewPhoto1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                textViewPhoto2.setTextColor(getResources().getColor(R.color.hintColor));

                photoUm = true;
                //Toast.makeText(getContext(), String.valueOf(imagem1), Toast.LENGTH_LONG).show();
                if(photo1 == null){
                    photoUpload.setImageURI(null);
                    abreGaleria();
                }else{
                    photoUpload.setImageURI(photo1);
                }

            }
        });

        linearLayoutOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textViewPhoto1.setTextColor(getResources().getColor(R.color.hintColor));
                textViewPhoto2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

                photoUm = false;
                //Toast.makeText(getContext(), String.valueOf(imagem2), Toast.LENGTH_LONG).show();
                if(photo2 == null){
                    photoUpload.setImageURI(null);
                    abreGaleria();
                }else{
                    photoUpload.setImageURI(photo2);
                }

            }
        });
        /***-------------------ALTERNANDO ENTRE A FOTO 1 E 2-----------------------****/


        /**---------------------ABRINDO A GALERIA DO USUÁRIO ASSIM QUE ENTRA------------------------***/
        photoBackEndClick = view.findViewById(R.id.imageViewUpload1FotosBackEnd);
        photoBackEndClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreGaleria();
            }
        });
        photoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreGaleria();
            }
        });
        abreGaleria();
        /**---------------------ABRINDO A GALERIA DO USUÁRIO ASSIM QUE ENTRA------------------------***/


        buttonAvancar = view.findViewById(R.id.buttomUpload1Avancar);
        buttonAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (photo1 != null && photo2 != null) {
                        textViewErro.setVisibility(View.INVISIBLE);

                        Bundle bundle = new Bundle();
                        bundle.putString("photo1", String.valueOf(photo1));
                        bundle.putString("photo2", String.valueOf(photo2));

                        Upload2Fragment upload2Fragment = new Upload2Fragment();

                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        upload2Fragment.setArguments(bundle);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.replace(R.id.linearLayoutMainActivity, upload2Fragment);
                        fragmentTransaction.commit();
                    } else {
                        textViewErro.setVisibility(View.VISIBLE);
                        //textViewErro.setText(R.string.hint_upload1_erro);
                    }

                }catch (Exception e){
                    textViewErro.setVisibility(View.VISIBLE);
                    //textViewErro.setText(R.string.hint_upload1_erro);
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 123){

                try {

                    photoSelecionada = data.getData();
                    //Toast.makeText(getContext(), String.valueOf(imagemSelecionada), Toast.LENGTH_LONG).show();

                    if (photoUm == true) {
                        photo1 = photoSelecionada;
                        photoUpload.setImageURI(null);
                        //Toast.makeText(getContext(), String.valueOf(imagemSelecionada), Toast.LENGTH_LONG).show();
                        photoUpload.setImageURI(photo1);
                    } else {
                        photo2 = photoSelecionada;
                        photoUpload.setImageURI(null);
                        //Toast.makeText(getContext(), String.valueOf(imagemSelecionada), Toast.LENGTH_LONG).show();
                        photoUpload.setImageURI(photo2);
                    }

                }catch (Exception e){

                    //Toast.makeText(getContext(), "Erro ao selecionar foto", Toast.LENGTH_LONG).show();

                }

            }
        }
    }

    private void abreGaleria(){

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);//abrindo a galeria

        startActivityForResult(Intent.createChooser(intent, "Select an image"), 123);
        //Toast.makeText(getContext(),"Loading", Toast.LENGTH_LONG).show();

    }

}
