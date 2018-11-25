package com.example.sloth.slothapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button BTRegister;
    private TextView TVBack;
    private EditText ETMail, ETName, ETPassword, ETPasswordConfirm;
    private String server = "https://api.sloth-project.tk";
    private RestTemplate rest;
    private HttpHeaders headers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //db = new DBHelper(this);
        BTRegister = (Button)findViewById(R.id.BTRegisterCreate);
        TVBack = (TextView)findViewById(R.id.TVRegisterBack);
        ETMail = (EditText)findViewById(R.id.ETRegisterMail);
        ETName = (EditText)findViewById(R.id.ETRegisterName);
        ETPassword = (EditText)findViewById(R.id.ETRegisterPassword);
        ETPasswordConfirm = (EditText)findViewById(R.id.ETRegisterPasswordConfirm);

        BTRegister.setOnClickListener(this);
        TVBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTRegisterCreate:
                    // Store new user then go to LoginActivity
                try {
                    register();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.TVRegisterBack:
                    // Goes back to LoginActivity
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
            default:
        }
    }

    private void register() throws IOException {
        String email = ETMail.getText().toString();
        String name  = ETName.getText().toString();
        String password = ETPassword.getText().toString();
        String passwordConfirm = ETPasswordConfirm.getText().toString();
        String uri = "/signup";
        String json;
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(email);

        if (m.find()) {
            if (password.equals(passwordConfirm)) {
                this.rest = new RestTemplate();
                this.headers = new HttpHeaders();
                headers.add("Content-Type", "application/json");
                headers.add("Accept", "*/*");

                json = "{\"name\" : \"" +email+"\", \"password\" : \"" + password + "\"}";
                HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
                ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.POST, requestEntity, String.class);
                String response = responseEntity.getBody();

                Toast.makeText(getApplicationContext(), "User created: " + response, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Adresse Email non-valide", Toast.LENGTH_SHORT).show();
        }





    }
}
