package comj.example.android.judgeme_androidapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.BaseAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by arthu on 27/01/2018.
 */

public class SharedPreferencesCreateAccount {

    private Context context;
    private SharedPreferences sharedPreferences;
    private static final String NOME_ARQUIVO = "judgeme.preferences";
    private static final int MODE = 0;
    private SharedPreferences.Editor editor;

    private static final String CHAVE_NOME_COMPLETO = "nome_completo";
    private static final String CHAVE_GENERO = "genero";
    private static final String CHAVE_NASCIMENTO = "nascimento";
    private static final String CHAVE_EMAIL = "email";
    private static final String CHAVE_SENHA = "senha";
    private static final String CHAVE_TELEFONE = "telefone";
    private static final String CHAVE_TELEFONE_MASCARA = "telefone_mascara";
    private static final String CHAVE_NICKNAME = "nickname";

    public SharedPreferencesCreateAccount(Context contextParameter){

        context = contextParameter;
        sharedPreferences = context.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = sharedPreferences.edit();

    }

    public void salvarUsuarioPreferenciasStep1( String nome, String sobrenome, String nome_completo, String genero, String nascimento){

        editor.putString(CHAVE_NOME_COMPLETO, nome_completo);
        editor.putString(CHAVE_GENERO, genero);
        editor.putString(CHAVE_NASCIMENTO, nascimento);
        editor.commit();

    }

    public void salvarUsuarioPreferenciasStep2( String email, String senha){

        editor.putString(CHAVE_EMAIL, email);
        editor.putString(CHAVE_SENHA, senha);
        editor.commit();

    }

    public void salvarUsuarioPreferenciasStep3( String telefone, String telefoneMascara){

        editor.putString(CHAVE_TELEFONE, telefone);
        editor.putString(CHAVE_TELEFONE_MASCARA, telefoneMascara);
        editor.commit();

    }

    public void salvarUsuarioPreferenciasStep5( String nickname){

        editor.putString(CHAVE_NICKNAME, nickname);
        editor.commit();

    }

    public HashMap<String, String> getDadosUsuario(){

        HashMap<String, String> dadosUsuario = new HashMap<>();

        dadosUsuario.put(CHAVE_NOME_COMPLETO, sharedPreferences.getString(CHAVE_NOME_COMPLETO, null));
        dadosUsuario.put(CHAVE_GENERO, sharedPreferences.getString(CHAVE_GENERO, null));
        dadosUsuario.put(CHAVE_NASCIMENTO, sharedPreferences.getString(CHAVE_NASCIMENTO, null));
        dadosUsuario.put(CHAVE_EMAIL, sharedPreferences.getString(CHAVE_EMAIL, null));
        dadosUsuario.put(CHAVE_SENHA, sharedPreferences.getString(CHAVE_SENHA, null));
        dadosUsuario.put(CHAVE_TELEFONE, sharedPreferences.getString(CHAVE_TELEFONE, null));
        dadosUsuario.put(CHAVE_TELEFONE_MASCARA, sharedPreferences.getString(CHAVE_TELEFONE_MASCARA, null));
        dadosUsuario.put(CHAVE_NICKNAME, sharedPreferences.getString(CHAVE_NICKNAME, null));

        return dadosUsuario;
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void salvarDadosDoUsuario(){

        final String email = getDadosUsuario().get("email");
        final String senha = getDadosUsuario().get("senha");

        db.collection("usuarios").document(Base64Custom.converterBase64(email))
                .set(getDadosUsuario())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("teste", "DocumentSnapshot successfully written!");

                        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                Log.d("teste", "Account successfully created!");

                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("teste", "Error writing document", e);
                    }
                });

    }

}
