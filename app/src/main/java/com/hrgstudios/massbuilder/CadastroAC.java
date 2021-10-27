package com.hrgstudios.massbuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadastroAC extends AppCompatActivity {

    //comentei sobre eles para colocar-los aqui, não sei explicar direito mais fds kkkkk
    private EditText Editnome, EditEmailc, EditSenhac;
    private Button Bt_cadastro;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //IDS dos edittexts da tela
        Editnome = findViewById(R.id.Editnome);
        EditEmailc = findViewById(R.id.EditEmailc);
        EditSenhac = findViewById(R.id.EditSenhac);

        //ID do botão
        Bt_cadastro = findViewById(R.id.Bt_cadastro);


        //Ouvir eventos de click pelo botão
        Bt_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = Editnome.getText().toString();
                String email = EditEmailc.getText().toString();
                String senha = EditSenhac.getText().toString();

                //e se...
                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){

                    Snackbar snackbar = Snackbar.make(v, "Preencha todos os campos!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }/*e se não*/else{
                    CadastrarUsuario(v);
                }
            }
        });
    }


    //Metodo de cadastro do Usuario lá no banco de dados
    private void CadastrarUsuario(View v){
        String nome = Editnome.getText().toString();
        String email = EditEmailc.getText().toString();
        String senha = EditSenhac.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){

               //Salvar dados do Usuario lá no database
               SalvarDadosUsuario();

               Snackbar snackbar = Snackbar.make(v, "Cadastro Realizado com sucesso!", Snackbar.LENGTH_LONG);
               snackbar.show();

           }else{
               String erro;
               try {
                   throw task.getException();
               }catch (FirebaseAuthWeakPasswordException e) {
                   erro = "Digite uma senha com no minimo 6 caracteres!";
               }catch(FirebaseAuthUserCollisionException e) {
                   erro = "Essa conta já existe!";
               }catch (FirebaseAuthInvalidCredentialsException e){
                   erro = "Verifique se seu email está digitado corretamente!";

               }catch(Exception e){
                   erro = "Erro ao cadastrar usuário!";
               }

               //Snackbar com erros
               Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_LONG);
               snackbar.show();

                }
            }
        });


    }

    public void SalvarDadosUsuario(){

        String nome = Editnome.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();

        usuarios.put("nome", nome);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(userID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
              Log.d("db", "Foi um sucesso cadastrar os dados no DB!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Ocorreu um erro ao cadastrar os dados no DB!" + e.toString());
            }
        });

    }


    public void loginac(View view){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);


    }
}