package comj.example.android.judgeme_androidapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import comj.example.android.judgeme_androidapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by arthu on 10/02/2018.
 */

public class CustomListViewSearch extends BaseAdapter {

    private Context mContext;
    private ArrayList<HashMap<String, String>> usuarioSearch;
    private static LayoutInflater inflater = null;

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
    public View getView(int position, View convertView, ViewGroup parent) {

        final View view = inflater.inflate(R.layout.list_search, null);

        final HashMap<String, String> mSearchData;

        mSearchData = usuarioSearch.get(position);

        CircleImageView circleImageView = view.findViewById(R.id.circleImageViewListSearchFotoPerfil);
        TextView textViewNome = view.findViewById(R.id.textViewListSearchNomeUsuario);
        TextView textViewNick = view.findViewById(R.id.textViewListSearchNickUsuario);

        textViewNome.setText(mSearchData.get("nome"));

        return view;
    }
}
