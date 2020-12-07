package com.example.project_login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login_page extends AppCompatActivity {
    EditText pass,username;
    Button log;
    DataBaseAssist dataBaseAssist;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    TextView textView;
    Boolean savedLogin;
    CheckBox rememberMe;
    long timePressed;
    InternetConnectivityCheck internetConnectivityCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        log=findViewById(R.id.Logbtn);
        pass=findViewById(R.id.passLog);
        username=findViewById(R.id.userLog);
        textView=findViewById(R.id.signPrompt);
        rememberMe=findViewById(R.id.rememberme);
        internetConnectivityCheck=new InternetConnectivityCheck(this);
        sharedPreferences=getSharedPreferences("loginRef",MODE_PRIVATE);
        sharedPreferencesEditor=sharedPreferences.edit();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If The USer Clicks on
                startActivity(new Intent(Login_page.this,Signup_page.class));
            }
        });
        //Initializing the DatabaseAssist Object !
        dataBaseAssist=new DataBaseAssist(Login_page.this);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetConnectivityCheck.checkConn()){
                    login();
                }
                else{
                    AlertDialog.Builder networkAlertBuilder= new AlertDialog.Builder(Login_page.this);
                    networkAlertBuilder.setTitle("No Network")
                            .setMessage("Check Network Connectivty !")
                            .setPositiveButton("Network Settings", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(getApplicationContext(),Login_page.class));
                                    finish();
                                }
                            });
                    AlertDialog alertDialog=networkAlertBuilder.create();
                    alertDialog.show();
                }

            }
        });
        savedLogin=sharedPreferences.getBoolean("saveLogin",true);
        if(savedLogin==true){
            username.setText(sharedPreferences.getString("username",null));
            pass.setText(sharedPreferences.getString("password",null));
        }
    }

    private void login() {
        //Setting The String Values
        String user=username.getText().toString();
        String password=pass.getText().toString();
        if(!password.equals("") && !user.equals("")){
            if(dataBaseAssist.checkCurrentUser(user).moveToNext()){
                if (dataBaseAssist.authUser(user,password).moveToNext()){
                    Toast.makeText(Login_page.this,"Okay !",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(Login_page.this,search_page.class);
                    intent.putExtra("username",user);
                    startActivity(intent);
                    //Checks if the Remember Me Check Box is checked if the Remember Me
                    if(rememberMe.isChecked()){
                        sharedPreferencesEditor.putBoolean("saveLogin",true);
                        //Setting The Username.
                        sharedPreferencesEditor.putString("username",user);
                        //Setting The Password
                        sharedPreferencesEditor.putString("password",password);
                        sharedPreferencesEditor.commit();

                    }

                }
                else {
                    Toast.makeText(Login_page.this,"Wrong Password !",Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(),"Username Doesn't Exists !",Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"Fields Cannot Be Left Blank !",Toast.LENGTH_LONG).show();
        }
    }
    //This function is invokes when the user presses the back button, and intent is passed from the profile activity to current activity !
    @Override
    public void onBackPressed() {
        //If the Intent Status is not null, then if the user presses the Back button twice the App prompts to Exit and On Second Touch,We Move To the Background !
        if(getIntent().getStringExtra("status")!=null){
            if(timePressed+2000>System.currentTimeMillis()){
                moveTaskToBack(true);
            }
            else {
                Toast.makeText(getBaseContext(),"Press Again To Exit !",Toast.LENGTH_SHORT).show();
            }
            timePressed=System.currentTimeMillis();
        }
    }
}

