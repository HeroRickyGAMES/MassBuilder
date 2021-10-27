package com.hrgstudios.massbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class CadastroAC extends AppCompatActivity {

    //comentei sobre eles para colocar-los aqui, não sei explicar direito mais fds kkkkk
    private EditText Editnome, EditEmailc, EditSenhac;
    private Button Bt_cadastro;

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
                }//e se não
                else{
                    CadastrarUsuario();
                }
            }
        });
    }


    //Metodo de cadastro do Usuario lá no banco de dados
    private void CadastrarUsuario(){
        String nome = Editnome.getText().toString();
        String email = EditEmailc.getText().toString();
        String senha = EditSenhac.getText().toString();



    }

    public void loginac(View view){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);


    }
}