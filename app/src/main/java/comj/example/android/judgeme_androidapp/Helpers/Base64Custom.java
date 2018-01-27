package comj.example.android.judgeme_androidapp.Helpers;

import android.util.Base64;

/**
 * Created by arthu on 27/01/2018.
 */

public class Base64Custom {

    public  static String converterBase64(String texto){

        String textoConvertido = Base64.encodeToString(texto.getBytes(), Base64.DEFAULT);
        return  textoConvertido.trim();//.trim() remove qualquer espaço gerado

    }

    public static String decodificarBase64(String texto){

        byte[] byteDecodificado = Base64.decode(texto, Base64.DEFAULT);
        return new String(byteDecodificado);

    }

}
