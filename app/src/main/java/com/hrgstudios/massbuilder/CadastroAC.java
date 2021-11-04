package com.hrgstudios.massbuilder;

import static com.google.rpc.context.AttributeContext.*;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.util.GAuthToken;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.rpc.context.AttributeContext;

import java.util.HashMap;
import java.util.Map;

public class CadastroAC extends AppCompatActivity {

    //comentei sobre eles para colocar-los aqui, não sei explicar direito mais fds kkkkk
    public EditText Editnome, EditEmailc, EditSenhac, editSobrenome, textIdade;
    public Button Bt_cadastro;
    public DatabaseReference referencia = FirebaseDatabase.getInstance().getReference("usuarios");
    public String uid;
    public FirebaseUser auth;


    //Data base
    public FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //IDS dos edittexts da tela
        Editnome = findViewById(R.id.Editnome);
        EditEmailc = findViewById(R.id.EditEmailc);
        EditSenhac = findViewById(R.id.EditSenhac);
        editSobrenome = findViewById(R.id.editSobrenome);
        textIdade = findViewById(R.id.textIdade);


        //ID do botão
        Bt_cadastro = findViewById(R.id.Bt_cadastro);


        //Ouvir eventos de click pelo botão
        Bt_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = Editnome.getText().toString();
                String email = EditEmailc.getText().toString();
                String senha = EditSenhac.getText().toString();
                String Sobrenome = editSobrenome.getText().toString();
                String Idade = textIdade.getText().toString();

                //e se...
                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty() || Sobrenome.isEmpty() || Idade.isEmpty()){

                    Snackbar snackbar = Snackbar.make(v, "Preencha todos os campos!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else{
                    CadastrarUsuario(v);
                }
            }
        });
    }


    //Metodo de cadastro do Usuario lá no banco de dados
    private void CadastrarUsuario(View v){

        String email = EditEmailc.getText().toString();
        String senha = EditSenhac.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){

               //Salvar dados do Usuario lá no database


               SalvarDadosUsuario();
               FirebaseAuth.getInstance().getUid();


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
        String sobrenome = editSobrenome.getText().toString();
        String idade = textIdade.getText().toString();

        Boolean isfuncionario = false;

        String getuID = EditEmailc.getText().toString().replaceAll("\\p{Punct}", "");

        referencia.child(getuID).child("nome").setValue(nome);
        referencia.child(getuID).child("sobrenome").setValue(sobrenome);
        referencia.child(getuID).child("Idade").setValue(idade);
        referencia.child(getuID).child("É funcionario").setValue(isfuncionario);


    }


    public void loginac(View view){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);


    }
}