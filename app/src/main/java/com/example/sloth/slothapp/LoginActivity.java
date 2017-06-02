package com.example.sloth.slothapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.MalformedURLException;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button BTLogin, BTCreate;
    private EditText ETMail, ETPassword;
    private DBHelper db;
    private Session session;
    private String server = "https://api.sloth-project.tk";
    private RestTemplate rest;
    private HttpHeaders headers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBHelper(this);
        session = new Session(this);
        BTLogin = (Button) findViewById(R.id.BTLogin);
        BTCreate = (Button) findViewById(R.id.BTLoginCreate);
        ETMail = (EditText) findViewById(R.id.ETLoginMail);
        ETPassword = (EditText) findViewById(R.id.ETLoginPassword);

        BTLogin.setOnClickListener(this);
        BTCreate.setOnClickListener(this);


        if (session.loggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BTLogin:
                // Sign in User then goes to MainActivity
                try {
                    login();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.BTLoginCreate:
                // Goes to RegisterActivity
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            default:
        }
    }

    private void login() throws MalformedURLException {
        String email = ETMail.getText().toString();
        String password = ETPassword.getText().toString();
        String uri = "/authenticate";
        String json;

        if (email != "" || password !="") {
            this.rest = new RestTemplate();
            this.headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            headers.add("Accept", "*/*");

            json = "{\"name\" : \"" + email + "\", \"password\" : \"" + password + "\"}";
            HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
            ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.POST, requestEntity, String.class);
            String response = responseEntity.getBody();

            session.setLoggedIn(true);
            Toast.makeText(getApplicationContext(), "User logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "Informations incorrectes", Toast.LENGTH_SHORT).show();
        }
    }
}


