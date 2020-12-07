package com.example.project_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class search_page extends AppCompatActivity {
    Spinner spinnerPhoneSelect;
    SeekBar priceBar;
    Button button;
    ArrayAdapter<String>arrayAdapterForPhones;
    String []phones;
    TextView textView;
    String username;
    RadioGroup romRadioGroup,ramRadioGroup;
    //RadioButton Parameters For Internal Storage
    RadioButton threeTwo,sixFour,twentyEight,twoSix;
    //RadioButton Parameters For RAM
    RadioButton three,four,six,eight,twel;
    double price;
    String selectRam=null;
    String selectedRom=null;
    DataBaseAssist userDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_search_page);


        romRadioGroup =findViewById(R.id.radioGroup);
        ramRadioGroup=findViewById(R.id.radioGroupRAm);
        userDatabase=new DataBaseAssist(search_page.this);
        if(getIntent().getStringExtra("username")!=null){
            //Getting the Username From The LoginActivity Class, using the PutExtra Feature
            //This is used to fetch the data from te database related to th user
            username=getIntent().getStringExtra("username");
        }



        //Radio Buttons are Being Hooked Up
        threeTwo=findViewById(R.id.radioButton);
        sixFour=findViewById(R.id.radioButton2);
        twentyEight=findViewById(R.id.radioButton3);
        twoSix=findViewById(R.id.radioButton4);
        //Hooking Up The RAM Radio Buttons
        three=findViewById(R.id.threeGb);
        four=findViewById(R.id.fourGb);
        six=findViewById(R.id.sixGB);
        eight=findViewById(R.id.eightGb);
        twel=findViewById(R.id.twelGB);
        //End of RAM
        //Setting the Spinner to Display The Phone Names
        spinnerPhoneSelect=findViewById(R.id.phoneSpinner);
        //button to test and search !
        button=findViewById(R.id.searchbutton);
        //TextView To Show The Change In The Price On The Spot
        textView=findViewById(R.id.price);
        //Progress bar To setting The price range in The Order of The 1000s
        priceBar=findViewById(R.id.priceBar);
        //Setting The Progres Value Initially To 8 So That Resulting Price Will Be 1000.00 x 8,000 Rs
        priceBar.setProgress(8);
        textView.setText((priceBar.getProgress()*1000.00)+"");
        priceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //Change in Progress Value  Will Be Reflected on the Spot !
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(Double.toString(1000.00*priceBar.getProgress()));
            }
            //Dead Methods
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //Phones Offered By The app
        phones=new String[]{"Samsung","Oppo","Vivo","One Plus","Redmi","Show All"};
        //Initailisign The ArrayAdapter
        arrayAdapterForPhones=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,phones);
        spinnerPhoneSelect.setAdapter(arrayAdapterForPhones);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                price=1000*priceBar.getProgress();
///////////////////Events For The Radio Button That Will Determine the Change in ROM////////////////////////
                if(threeTwo.isChecked()){
                    selectedRom=threeTwo.getText().toString();
                }
                else if(sixFour.isChecked()){
                    selectedRom=sixFour.getText().toString();
                }
                else if(twentyEight.isChecked()){
                    selectedRom=twentyEight.getText().toString();
                }
                else  if(twoSix.isChecked()){
                    selectedRom=twoSix.getText().toString();
                }

///////////////////Events For The Radio Button That Will Determine the Change in RAM////////////////////////
                if(three.isChecked()){
                    selectRam=three.getText().toString();
                }
                else if(four.isChecked()){
                    selectRam=four.getText().toString();
                }
                else if(eight.isChecked()){
                    selectRam=eight.getText().toString();
                }
                else if(six.isChecked()){
                    selectRam=six.getText().toString();
                }
                else if(twel.isChecked()){
                    selectRam=twel.getText().toString();
                }
                //The Infromation required to display the data in recycler view is passed via intent
                //The price and phone name are required parameters
                //ROM and RAM are Optional Params
                Intent intent=new Intent(search_page.this,search_result_page.class);
                //Passing The Intend Values to The PhoneDetails Class so that We Can Use the Phone make and price Information to Segregate The Data from the Database !
                intent.putExtra("make",spinnerPhoneSelect.getSelectedItem().toString());
                intent.putExtra("price",price);
                intent.putExtra("ram",selectRam);
                intent.putExtra("rom",selectedRom);
                Log.d("rom","Rom Selected is"+selectedRom);
                Log.d("ramSe","Ram Selected is"+selectRam);
                startActivity(intent);

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.select_phone_option_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Toast.makeText(getApplicationContext(),"Logged Out !",Toast.LENGTH_LONG).show();
                startActivity(new Intent(search_page.this,Login_page.class));
                return true;
            case R.id.profile:
                Intent intent=new Intent(search_page.this,Profile.class);
                String user[]=userDatabase.info(username);
                intent.putExtra("username",user[0]);
                intent.putExtra("email",user[1]);
                intent.putExtra("pass",user[2]);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}