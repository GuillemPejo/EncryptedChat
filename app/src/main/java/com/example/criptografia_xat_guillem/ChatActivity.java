package com.example.criptografia_xat_guillem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;
/**
 * Android client which works as a chat client
 * <p>
 * If is used on the Android emulator, follow these steps:
 * <p>
 * On a terminal:
 * telnet localhost 5554
 * auth
 * redir add tcp:5050:8189
 * telnet localhost 8189
 */
public class ChatActivity extends AppCompatActivity {

    private RecyclerView missatge;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    private Button btn_enviar;
    private EditText entrada;
    private List<Missatge> msgList = new ArrayList();
    private String username;
    private int joinCounter = 1;
    private int messagesCounter = 1;
    // Xifratge
    private SecretKey ClauSecretaSimetric = null;
    private PrivateKey ClauPrivadaAsimetric = null;
    //private PublicKey asymmetricPublicKey = null;
    private PrivateKey ClauPrivadaFirma = null;
    private PublicKey ClauPublicaFirma = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // USERNAME
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Nom d'usuari");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                username = input.getText().toString().toUpperCase();
            }
        });

        alertDialog.setNegativeButton("Cancel·lar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();

        missatge = findViewById(R.id.messages);
        layoutManager = new LinearLayoutManager(this);
        missatge.setLayoutManager(layoutManager);
        adapter = new MissatgeAdapter(msgList);
        missatge.setAdapter(adapter);
        btn_enviar = findViewById(R.id.buttonSend);
        entrada = findViewById(R.id.keyboardInput);

        // Send a message
        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    enviarMissatge();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void enviarMissatge() throws Exception {
        String msg = entrada.getText().toString();
        entrada.setText("");
        Encriptar();
        Missatge missatge = new Missatge(username, msg, ClauSecretaSimetric, ClauPrivadaAsimetric, ClauPrivadaFirma);
        msgList.add(missatge);
        System.out.println(msg.get);

        adapter = new MissatgeAdapter(msgList);
        adapter.notifyDataSetChanged();
        this.missatge.setAdapter(adapter);
    }


    public void Encriptar() throws Exception {

        // FIRMA DIGITAL
        FirmaDigital firmaDigital = new FirmaDigital();
        this.ClauPrivadaFirma = firmaDigital.getPrivateKey();
        this.ClauPublicaFirma = firmaDigital.getPublicKey();

        // XIFRAT SIMÈTRIC
        XifratSimetric xifratSimetric = new XifratSimetric();
        this.ClauSecretaSimetric = (SecretKey) xifratSimetric.getSecretKey();

        // XIFRAT ASIMÈTRIC
        XifratAsimetric xifratAsimetric = new XifratAsimetric();
        this.ClauPrivadaAsimetric = xifratAsimetric.getPrivateKey();
        //this.asymmetricPublicKey = asymmetricEncryption.getPublicKey();


    }


}



