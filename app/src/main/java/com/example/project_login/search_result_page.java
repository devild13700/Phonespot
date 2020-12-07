package com.example.project_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
//
public class search_result_page extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Phone>phones;
    CustomAdapter customAdapter;
    PhoneDatabase phoneDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_page);
        recyclerView = findViewById(R.id.recycler);

        phones = new ArrayList<>();


        phoneDatabase = new PhoneDatabase(search_result_page.this);
        if(phoneDatabase.selectAllPhones().getCount()==0){
            addPhonesToDB();
        }

        //addToList();
        //First of All We Check The Intend is Null Or Not !
        if(getIntent()!=null){
            //Then it Checks Whether The Intent Contains the Rom and Ram Inforamtion,if They Both are Null We Can show The Phones Based on The Price(Show All) or On The Basis is name and Price
            if(getIntent().getStringExtra("rom")==null && getIntent().getStringExtra("ram")==null){
                //Show All Price
                if(getIntent().getStringExtra("make").equals("Show All")){
                    addToListShowAll(getIntent().getDoubleExtra("price",10000.99));
                }
                //Based on The name and price
                else {
                    addToListBasedOnName(getIntent().getStringExtra("make"),getIntent().getDoubleExtra("price",10000.99));
                }
            }
            //Checking The Condition Where the Rom is Given While is Ram is Not !
            else if(getIntent().getStringExtra("rom")!=null && getIntent().getStringExtra("ram")==null){
                //Show All Price
                if(getIntent().getStringExtra("make").equals("Show All")){
                    addToListShowAllPriceAndRom(getIntent().getDoubleExtra("price",10000.99),getIntent().getStringExtra("rom"));

                }
                //Based on The name,price and rom
                else {
                    addToListNamePriceAndRom(getIntent().getStringExtra("make"),getIntent().getDoubleExtra("price",10000.99),getIntent().getStringExtra("rom"));
                }
            }
            //Checking The Condition Where the Ram is Given While is Rom is Not !
            else if(getIntent().getStringExtra("rom")==null && getIntent().getStringExtra("ram")!=null){
                //Show All Price
                if(getIntent().getStringExtra("make").equals("Show All")){
                    addToListShowAllPriceAndRam(getIntent().getDoubleExtra("price",10000.99),getIntent().getStringExtra("ram"));

                }
                //Based on The name,price and ram
                else {
                    addToListShowAllANamePriceAndRam(getIntent().getStringExtra("make"),getIntent().getDoubleExtra("price",10000.99),getIntent().getStringExtra("ram"));
                }
            }
            //Checking The Condition Where both RAM and ROM are Given !
            else if(getIntent().getStringExtra("rom")!=null && getIntent().getStringExtra("ram")!=null){
                //Show All Price
                if(getIntent().getStringExtra("make").equals("Show All")){
                    //Adding all The Phones to The ArrayList based on Price range,ram and the Rom
                    addToListShowAllPriceRomAndRam(getIntent().getDoubleExtra("price",10000.99),getIntent().getStringExtra("rom"),getIntent().getStringExtra("ram"));

                }
                //Based on The name,price and ram
                else {
                    //Adding The Phones to The ArrayList based on The Make,Price range,ram and the Rom
                    addToListShowAllANamePriceAndRomAndRam(getIntent().getStringExtra("make"),getIntent().getDoubleExtra("price",10000.99),getIntent().getStringExtra("rom"),getIntent().getStringExtra("ram"));
                }
            }
        }
        Toast.makeText(getApplicationContext(),getIntent().getStringExtra("make")+" "+getIntent().getDoubleExtra("price",100),Toast.LENGTH_LONG).show();
        customAdapter = new CustomAdapter(this, phones);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
    //Adding all the Phones to teh ArraysList Based on the price,ram and rom
    private void addToListShowAllPriceRomAndRam(double price, String ram, String rom) {
        Cursor cursor=phoneDatabase.selectAllPhonesPriceRamRom(price,ram,rom);
        while (cursor.moveToNext()){
            phones.add(new Phone(cursor.getString(0)+"",cursor.getString(1)+"",cursor.getString(2)+"",cursor.getDouble(3),cursor.getString(4)+"",cursor.getString(5)+"",cursor.getString(6)+"",cursor.getString(7)+"",cursor.getString(8),cursor.getString(9)));
            Log.d("buy",cursor.getString(4));
        }
    }
    //Adding all the Phones to teh ArraysList Based on the price
    private void addToListShowAll(double price){
        Cursor cursor=phoneDatabase.selectAllPhones(price);
        while (cursor.moveToNext()){
            phones.add(new Phone(cursor.getString(0)+"",cursor.getString(1)+"",cursor.getString(2)+"",cursor.getDouble(3),cursor.getString(4)+"",cursor.getString(5)+"",cursor.getString(6)+"",cursor.getString(7)+"",cursor.getString(8),cursor.getString(9)));
            Log.d("buy",cursor.getString(4));
        }
    }
    //Adding all the Phones to the ArraysList Based on the price and rom
    private void addToListShowAllPriceAndRom(double price,String rom){
        Cursor cursor=phoneDatabase.selectAllPhones(price,rom);
        while (cursor.moveToNext()){
            phones.add(new Phone(cursor.getString(0)+"",cursor.getString(1)+"",cursor.getString(2)+"",cursor.getDouble(3),cursor.getString(4)+"",cursor.getString(5)+"",cursor.getString(6)+"",cursor.getString(7)+"",cursor.getString(8),cursor.getString(9)));
            Log.d("buy",cursor.getString(4));
        }
    }
    //Adding all the Phones to the ArraysList Based on the name and price
    private void addToListBasedOnName(String name, double price){
        Cursor cursor = phoneDatabase.selectAllPhonesByNamePrice(name, price);
        while (cursor.moveToNext()) {
            phones.add(new Phone(cursor.getString(0) + "", cursor.getString(1) + "", cursor.getString(2) + "", cursor.getDouble(3), cursor.getString(4) + "", cursor.getString(5) + "", cursor.getString(6) + "", cursor.getString(7) + "",cursor.getString(8),cursor.getString(9)));
        }
    }
    //Adding all the Phones to teh ArraysList Based on the price,name and rom
    private void addToListNamePriceAndRom(String name, double price, String rom){
        Cursor cursor = phoneDatabase.selectAllPhonesByNamePriceAndROM(name, price,rom);
        while (cursor.moveToNext()) {
            phones.add(new Phone(cursor.getString(0) + "", cursor.getString(1) + "", cursor.getString(2) + "", cursor.getDouble(3), cursor.getString(4) + "", cursor.getString(5) + "", cursor.getString(6) + "", cursor.getString(7) + "",cursor.getString(8),cursor.getString(9)));
        }
    }
    //Adding all the Phones to teh ArraysList Based on the price,ram and rom and make
    private void addToListShowAllANamePriceAndRomAndRam(String name,double price,String rom,String ram){
        Cursor cursor=phoneDatabase.selectAllPhonesByNamePriceROMAndRAM(name,price,rom,ram);
        while (cursor.moveToNext()){
            phones.add(new Phone(cursor.getString(0)+"",cursor.getString(1)+"",cursor.getString(2)+"",cursor.getDouble(3),cursor.getString(4)+"",cursor.getString(5)+"",cursor.getString(6)+"",cursor.getString(7)+"",cursor.getString(8),cursor.getString(9)));
            Log.d("buy",cursor.getString(4));
        }
    }
    //Adding to The ArraysList Based on the name,price and ram
    private void addToListShowAllANamePriceAndRam(String name,double price,String ram){
        Cursor cursor=phoneDatabase.selectAllPhonesByNamePriceAndRAM(name,price,ram);
        while (cursor.moveToNext()){
            phones.add(new Phone(cursor.getString(0)+"",cursor.getString(1)+"",cursor.getString(2)+"",cursor.getDouble(3),cursor.getString(4)+"",cursor.getString(5)+"",cursor.getString(6)+"",cursor.getString(7)+"",cursor.getString(8),cursor.getString(9)));
            Log.d("buy",cursor.getString(4));
        }
    }
    //Based in Ram and The price range only
    private void addToListShowAllPriceAndRam(double price,String ram){
        Cursor cursor=phoneDatabase.selectAllPhonesByPriceAndRAM(price,ram);
        while (cursor.moveToNext()){
            phones.add(new Phone(cursor.getString(0)+"",cursor.getString(1)+"",cursor.getString(2)+"",cursor.getDouble(3),cursor.getString(4)+"",cursor.getString(5)+"",cursor.getString(6)+"",cursor.getString(7)+"",cursor.getString(8),cursor.getString(9)));
            Log.d("buy",cursor.getString(4));
        }
    }
    //Adding all The phones, independent of the Brand and specs
    void addPhonesToDB(){
        //Samsung Phones
        phoneDatabase.addPhoneInfo("Samsung","Galaxy A10s","https://m.media-amazon.com/images/I/81e6XvJzKgL._AC_UY218_.jpg",10489.00,"https://www.amazon.in/Samsung-Galaxy-Storage-Additional-Exchange/dp/B07SBJZS5C/ref=sr_1_9?dchild=1&keywords=smartphones&qid=1606138906&refinements=p_n_feature_eight_browse-bin%3A8561114031%2Cp_n_feature_seven_browse-bin%3A8561132031%2Cp_89%3ASamsung&rnid=3837712031&s=electronics&sr=1-9","4.0","3GB","32GB","4000mAH","MediaTek MT6762 octa core processor");
        phoneDatabase.addPhoneInfo("Samsung","Galaxy M11","https://m.media-amazon.com/images/I/71hqi389rcL._AC_UY218_.jpg",9999.00,"https://www.amazon.in/Samsung-Metallic-Storage-Additional-Exchange/dp/B086K9YCVN/ref=sr_1_6?dchild=1&keywords=smartphones&qid=1606138906&refinements=p_n_feature_eight_browse-bin%3A8561114031%2Cp_n_feature_seven_browse-bin%3A8561132031%2Cp_89%3ASamsung&rnid=3837712031&s=electronics&sr=1-6","4.1","3GB","32GB","5000mAH","1.8GHz Qualcomm SDM450-F01 octa core processor");
        phoneDatabase.addPhoneInfo("Samsung","Galaxy M21","https://m.media-amazon.com/images/I/71dujTTJDZL._AC_UY218_.jpg",13999.00,"https://www.amazon.in/Samsung-Galaxy-Midnight-Blue-Storage/dp/B07HGJJ559/ref=sr_1_1?dchild=1&keywords=smartphones&qid=1606139488&refinements=p_n_feature_seven_browse-bin%3A8561132031%7C8561133031%2Cp_89%3ASamsung%2Cp_n_feature_eight_browse-bin%3A8561116031&rnid=8561129031&s=electronics&sr=1-1","4.2","4GB","64GB","6000mAH","Exynos 9611 2.3GHz,1.7GHz Octa-Core processor");
        phoneDatabase.addPhoneInfo("Samsung","Galaxy M11","https://m.media-amazon.com/images/I/718dLm80KcL._AC_UY218_.jpg",11999.00,"https://www.amazon.in/Samsung-Galaxy-Storage-Additional-Exchange/dp/B086KCC68B/ref=sr_1_4?dchild=1&keywords=smartphones&qid=1606139488&refinements=p_n_feature_seven_browse-bin%3A8561132031%7C8561133031%2Cp_89%3ASamsung%2Cp_n_feature_eight_browse-bin%3A8561116031&rnid=8561129031&s=electronics&sr=1-4","4.1","4GB","64GB","5000mAH","1.8GHz Qualcomm SDM450-F01 octa core processor");
        phoneDatabase.addPhoneInfo("Samsung","Galaxy A21s","https://m.media-amazon.com/images/I/811RH5e-snL._AC_UY218_.jpg",14999.00,"https://www.amazon.in/Samsung-Galaxy-Storage-Additional-Exchange/dp/B086KC85W8/ref=sr_1_8?dchild=1&keywords=smartphones&qid=1606139488&refinements=p_n_feature_seven_browse-bin%3A8561132031%7C8561133031%2Cp_89%3ASamsung%2Cp_n_feature_eight_browse-bin%3A8561116031&rnid=8561129031&s=electronics&sr=1-8","3.6","4GB","64GB","5000mAH","2.0GHz Exynos 850 octa-core processor");
        phoneDatabase.addPhoneInfo("Samsung","Galaxy M31","https://m.media-amazon.com/images/I/71-Su4Wr0HL._AC_UY218_.jpg",15999.00,"https://www.amazon.in/Samsung-Galaxy-Ocean-Blue-Storage/dp/B07HGJKDQL/ref=sr_1_1?dchild=1&keywords=smartphones&qid=1606140012&refinements=p_89%3ASamsung%2Cp_n_feature_eight_browse-bin%3A8561116031%2Cp_n_feature_seven_browse-bin%3A16757454031%7C8561132031&rnid=8561129031&s=electronics&sr=1-1","4.2","6GB","64GB","6000mAH","2.3GHz + 1.7GHz Exynos 9611 Octa core processor");
        phoneDatabase.addPhoneInfo("Samsung","Galaxy A71","https://m.media-amazon.com/images/I/81e6XvJzKgL._AC_UY218_.jpg",29999.00,"https://www.amazon.in/gp/slredirect/picassoRedirect.html/ref=pa_sp_atf_electronics_sr_pg1_1?ie=UTF8&adId=A07003152YYRDEZZ4YOLV&url=%2FSamsung-Galaxy-Storage-Additional-Exchange%2Fdp%2FB07X9YNP5M%2Fref%3Dsr_1_2_sspa%3Fdchild%3D1%26keywords%3Dsamsung%26qid%3D1606140574%26refinements%3Dp_n_feature_seven_browse-bin%253A16757455031%26rnid%3D8561129031%26s%3Delectronics%26sr%3D1-2-spons%26psc%3D1&qualifier=1606140584&id=6849709272327052&widgetName=sp_atf","4.3","8GB","128GB","4500mAH","2.3GHz+1.7GHz Qualcomm SM7150 octa core processor");
        phoneDatabase.addPhoneInfo("Samsung","Galaxy M51","https://m.media-amazon.com/images/I/713AhSUtbHL._AC_UY218_.jpg",24999.00,"https://www.amazon.in/gp/slredirect/picassoRedirect.html/ref=pa_sp_atf_electronics_sr_pg1_1?ie=UTF8&adId=A001861434VOTEG3D8QD4&url=%2FSamsung-Galaxy-Celestial-Black-Storage%2Fdp%2FB085J1CPCW%2Fref%3Dsr_1_1_sspa%3Fdchild%3D1%26keywords%3Dsamsung%26qid%3D1606140574%26refinements%3Dp_n_feature_seven_browse-bin%253A16757455031%26rnid%3D8561129031%26s%3Delectronics%26sr%3D1-1-spons%26psc%3D1&qualifier=1606140584&id=6849709272327052&widgetName=sp_atf","4.3","8GB","128GB","7000mAH"," 2.2GHz+1.8GHz Qualcomm SD730G octa core processor");
        phoneDatabase.addPhoneInfo("Samsung","Galaxy Z Fold2 5G","https://m.media-amazon.com/images/I/71U9nzW+XsL._AC_UY218_.jpg",149999.00,"https://www.amazon.in/Samsung-Galaxy-Storage-Additional-Exchange/dp/B086KFBNV5/ref=sr_1_1?dchild=1&keywords=samsung&qid=1606140838&refinements=p_n_feature_seven_browse-bin%3A16757455031%2Cp_n_feature_eight_browse-bin%3A14267636031%2Cp_89%3ASamsung&rnid=3837712031&s=electronics&sr=1-1","4.6","12GB","256GB","4500mAH","1.8GHz+2.4GHz+3.09GHz Qualcomm SM8250-2-AB octa core processor");
        phoneDatabase.addPhoneInfo("Samsung","Galaxy Note 10","https://m.media-amazon.com/images/I/710H5rWYI0L._AC_UY218_.jpg",51989.00,"https://www.amazon.in/Samsung-Galaxy-Storage-Additional-Exchange/dp/B07PQ7DK2N/ref=sr_1_10?dchild=1&keywords=samsung&qid=1606140838&refinements=p_n_feature_seven_browse-bin%3A16757455031%2Cp_n_feature_eight_browse-bin%3A14267636031%2Cp_89%3ASamsung&rnid=3837712031&s=electronics&sr=1-10","4.4","8GB","256GB","3500mAH","2.7GHz Exynos 9825 octa core processor");
        phoneDatabase.addPhoneInfo("Samsung","Galaxy M21","https://m.media-amazon.com/images/I/71QLvGIAq5L._AC_UY218_.jpg",15999.00,"https://www.amazon.in/Samsung-Galaxy-Raven-Black-Storage/dp/B07HGH8ML7/ref=sr_1_1?crid=13FYOKMM863W9&dchild=1&keywords=samsung+6gb+128gb&qid=1606713729&refinements=p_n_feature_eight_browse-bin%3A8561112031%2Cp_n_feature_seven_browse-bin%3A16757454031&rnid=8561129031&s=electronics&sprefix=Samsung+6gb+128%2Caps%2C310&sr=1-1","4.2","6GB","128GB","6000mAH","Exynos 9611,2.3GHz,1.7GHz Octa-Core processor");

        //End Of Samsung
        //Oppo Phones
        phoneDatabase.addPhoneInfo("Oppo","Reno 3 Pro","https://m.media-amazon.com/images/I/81dxd7vkdZL._AC_UY218_.jpg",27990.00,"https://www.amazon.in/Midnight-Storage-Additional-Exchange-Offers/dp/B084457WQB/ref=sr_1_1?dchild=1&keywords=Oppo+smartphones&qid=1606141309&refinements=p_n_feature_seven_browse-bin%3A16757455031%2Cp_n_feature_eight_browse-bin%3A14267636031&rnid=8561111031&s=electronics&sr=1-1","4.0","8GB","256GB","4025mAH","2.2GHz MediaTek Helio P95 octa core processor");
        phoneDatabase.addPhoneInfo("Oppo","Reno 2","https://m.media-amazon.com/images/I/81kmbC6WfjL._AC_UY218_.jpg",38990.00,"https://www.amazon.in/Luminous-Storage-Additional-Exchange-Offers/dp/B07SBJXQC7/ref=sr_1_4?dchild=1&keywords=Oppo+smartphones&qid=1606141309&refinements=p_n_feature_seven_browse-bin%3A16757455031%2Cp_n_feature_eight_browse-bin%3A14267636031&rnid=8561111031&s=electronics&sr=1-4","4.4","8GB","256GB","4000mAH","2.2GHz Qualcomm 730G octa core processor");
        phoneDatabase.addPhoneInfo("Oppo","Reno 2 Z","https://m.media-amazon.com/images/I/71p33agBY2L._AC_UY218_.jpg",27490.00,"https://www.amazon.in/Reno2-Storage-Additional-Exchange-Offers/dp/B07S7DXJWT/ref=sr_1_5?dchild=1&keywords=Oppo+smartphones&qid=1606141309&refinements=p_n_feature_seven_browse-bin%3A16757455031%2Cp_n_feature_eight_browse-bin%3A14267636031&rnid=8561111031&s=electronics&sr=1-5","4.3","8GB","256GB","400mAH","2.2GHz Helio P90 octa core processor");
        phoneDatabase.addPhoneInfo("Oppo","A52","https://m.media-amazon.com/images/I/71FPmXaDfbL._AC_UY218_.jpg",13990.00,"https://www.amazon.in/Oppo-Twilight-Storage-Additional-Exchange/dp/B086KCDPMR/ref=sr_1_1?dchild=1&keywords=Oppo+smartphones&qid=1606141665&refinements=p_n_feature_eight_browse-bin%3A14267636031%7C8561112031%2Cp_n_feature_seven_browse-bin%3A16757454031&rnid=8561129031&s=electronics&sr=1-1","4.1","6GB","128GB","5000mAH","2.0GHz Qualcomm Snapdragon 665 octa core processor");
        phoneDatabase.addPhoneInfo("Oppo","A31","https://m.media-amazon.com/images/I/61CnyJ-IbML._AC_UY218_.jpg",11990.00,"https://www.amazon.in/Oppo-Fantasy-Storage-Additional-Exchange/dp/B08444SXZ6/ref=sr_1_2?dchild=1&keywords=Oppo+smartphones&qid=1606141665&refinements=p_n_feature_eight_browse-bin%3A14267636031%7C8561112031%2Cp_n_feature_seven_browse-bin%3A16757454031&rnid=8561129031&s=electronics&sr=1-2","4.1","6GB","128GB","4230mAH","2.3GHz Mediatek 6765 octa core processor");
        phoneDatabase.addPhoneInfo("Oppo","A53","https://m.media-amazon.com/images/I/71QJyXZYPuL._AC_UY218_.jpg",15499.00,"https://www.amazon.in/Fancy-Storage-Additional-Exchange-Offers/dp/B089MRYXN7/ref=sr_1_5?dchild=1&keywords=Oppo+smartphones&qid=1606141665&refinements=p_n_feature_eight_browse-bin%3A14267636031%7C8561112031%2Cp_n_feature_seven_browse-bin%3A16757454031&rnid=8561129031&s=electronics&sr=1-5","4.0","6GB","128GB","5000mAH","1.8GHz Qualcomm Snapdragon 460 octa core processor");
        phoneDatabase.addPhoneInfo("Oppo","A12","https://m.media-amazon.com/images/I/71-nSoWl8iL._AC_UY218_.jpg",11490.00,"https://www.amazon.in/OPPO-Storage-Additional-Exchange-Offers/dp/B08444X3CN/ref=sr_1_1?dchild=1&keywords=oppo&qid=1606142095&refinements=p_89%3AOppo%2Cp_n_feature_eight_browse-bin%3A8561116031&rnid=8561129031&s=electronics&sr=1-1","4.0","4GB","64GB","4230mAH","2.3GHz MediaTek Helio P35 octa core processor");
        phoneDatabase.addPhoneInfo("Oppo","A5S","https://m.media-amazon.com/images/I/71ZU4VsevqL._AC_UY218_.jpg",8990.00,"https://www.amazon.in/Black-Storage-Additional-Exchange-Offers/dp/B07PP2K69Z/ref=sr_1_3?dchild=1&keywords=Oppo+A5S&qid=1606279310&s=electronics&sr=1-3","4.1","3GB","32GB","4230mAH","2.4GHz Mediatek MT6765 octa core processor");
        phoneDatabase.addPhoneInfo("Oppo","A15","https://m.media-amazon.com/images/I/71wPwmxo2NL._AC_UY218_.jpg",9990.00,"https://www.amazon.in/Test-TST_Exclusive1018-Exclusive-1018/dp/B08445GY29/ref=sr_1_3?dchild=1&keywords=oppo&qid=1606191957&refinements=p_n_feature_seven_browse-bin%3A8561132031&rnid=8561129031&s=electronics&sr=1-3","3.8","3GB","32GB","4230mAH","2.3GHz MediaTek Helio P35 octa core processor");
        //End Of Oppo
        //Vivo
        phoneDatabase.addPhoneInfo("Vivo","Y50","https://m.media-amazon.com/images/I/514ETCdN3CL._AC_UY218_.jpg",16490.00,"https://www.amazon.in/gp/slredirect/picassoRedirect.html/ref=pa_sp_atf_electronics_sr_pg1_1?ie=UTF8&adId=A06788371W60J10GOSUAG&url=%2FVivo-Storage-Additional-Exchange-Offers%2Fdp%2FB086KDZGTZ%2Fref%3Dsr_1_1_sspa%3Fdchild%3D1%26keywords%3DVivo%26qid%3D1606197666%26refinements%3Dp_n_feature_seven_browse-bin%253A16757455031%26rnid%3D8561129031%26s%3Delectronics%26sr%3D1-1-spons%26psc%3D1&qualifier=1606197673&id=1014131249803151&widgetName=sp_atf","4.0","8GB","128GB","5000mAH","2.0GHz Qualcomm Snapdragon 665 octa core processor");
        phoneDatabase.addPhoneInfo("Vivo","V20","https://m.media-amazon.com/images/I/613-W6czeJL._AC_UY218_.jpg",24990.00,"https://www.amazon.in/Test-TST_Exclusive1011-Exclusive-1011/dp/B084459L1C/ref=sr_1_2?dchild=1&keywords=Vivo&qid=1606197666&refinements=p_n_feature_seven_browse-bin%3A16757455031&rnid=8561129031&s=electronics&sr=1-2","4.3","8GB","128GB","4000mAH","Qualcomm Snapdragon 720G octa core processor");
        phoneDatabase.addPhoneInfo("Vivo","Y20","https://m.media-amazon.com/images/I/71SVzNmepQL._AC_UY218_.jpg",13990.00,"https://www.amazon.in/gp/slredirect/picassoRedirect.html/ref=pa_sp_atf_electronics_sr_pg1_1?ie=UTF8&adId=A10363793MDAU9DQCMPOZ&url=%2FVivo-Purist-Storage-Additional-Exchange%2Fdp%2FB089MT22SH%2Fref%3Dsr_1_1_sspa%3Fdchild%3D1%26keywords%3DVivo%26qid%3D1606198026%26refinements%3Dp_n_feature_seven_browse-bin%253A16757454031%26rnid%3D8561129031%26s%3Delectronics%26sr%3D1-1-spons%26psc%3D1&qualifier=1606198030&id=6707354744850596&widgetName=sp_atf","4.1","6GB","64GB","5000mAH","Qualcomm Snapdragon 460 octa core processor");
        phoneDatabase.addPhoneInfo("Vivo","Z1x","https://m.media-amazon.com/images/I/4163N5X5v7L._AC_UY218_.jpg",19990.00,"https://www.amazon.in/Vivo-Z1x-64GB-Fusion-Blue/dp/B0826217JG/ref=sr_1_6?dchild=1&keywords=Vivo&qid=1606198026&refinements=p_n_feature_seven_browse-bin%3A16757454031&rnid=8561129031&s=electronics&sr=1-6","4.8","6GB","64GB","4500mAH","Qualcomm Snapdragon 712 AIE Processor");
        phoneDatabase.addPhoneInfo("Vivo","Y20","https://m.media-amazon.com/images/I/71SVzNmepQL._AC_UY218_.jpg",12990.00,"https://www.amazon.in/gp/slredirect/picassoRedirect.html/ref=pa_sp_atf_electronics_sr_pg1_1?ie=UTF8&adId=A086505434CHEYNPAIZU5&url=%2FVivo-Obsidian-Storage-Additional-Exchange%2Fdp%2FB089MS3GL9%2Fref%3Dsr_1_1_sspa%3Fdchild%3D1%26keywords%3DVivo%26qid%3D1606198310%26refinements%3Dp_n_feature_seven_browse-bin%253A8561133031%26rnid%3D8561129031%26s%3Delectronics%26sr%3D1-1-spons%26psc%3D1&qualifier=1606198315&id=3552055794466947&widgetName=sp_atf","4.1","4GB","64GB","5000mAH","Qualcomm Snapdragon 460 octa core processor");
        phoneDatabase.addPhoneInfo("Vivo","Y30","https://m.media-amazon.com/images/I/51320C1CrsL._AC_UY218_.jpg",11990.00,"https://www.amazon.in/Vivo-Emerald-Storage-Additional-Exchange/dp/B086KG11W3/ref=sr_1_3?dchild=1&keywords=Vivo&qid=1606198310&refinements=p_n_feature_seven_browse-bin%3A8561133031&rnid=8561129031&s=electronics&sr=1-3","4.2","4GB","128GB","500mAH","MediaTek Helio P35 octa core processor");
        phoneDatabase.addPhoneInfo("Vivo","Y91i","https://m.media-amazon.com/images/I/51+yaqN2O0L._AC_UY218_.jpg",8490.00,"https://www.amazon.in/Vivo-Storage-Additional-Exchange-Offers/dp/B086KCCSF7/ref=sr_1_2?dchild=1&keywords=Vivo&qid=1606198569&refinements=p_n_feature_seven_browse-bin%3A8561132031&rnid=8561129031&s=electronics&sr=1-2","4.2","3GB","32GB","4030mAH","2.0GHz Mediatek Helio P22 octa core processor");
        phoneDatabase.addPhoneInfo("Vivo","Y11","https://m.media-amazon.com/images/I/71BBqZGY3dL._AC_UY218_.jpg",8990.00,"https://www.amazon.in/Vivo-Mineral-Storage-Additional-Exchange/dp/B07XD2FWBC/ref=sr_1_4?dchild=1&keywords=Vivo&qid=1606198569&refinements=p_n_feature_seven_browse-bin%3A8561132031&rnid=8561129031&s=electronics&sr=1-4","4.1","3GB","32GB","5000mAH"," Qualcomm Snapdragon 439 octa core processor");
        phoneDatabase.addPhoneInfo("Vivo","Y20i","https://m.media-amazon.com/images/I/71INGYhIf0L._AC_UY218_.jpg",11490.00,"https://www.amazon.in/Vivo-Nebula-Storage-Additional-Exchange/dp/B089MQ7JQ3/ref=sr_1_5?dchild=1&keywords=Vivo&qid=1606198569&refinements=p_n_feature_seven_browse-bin%3A8561132031&rnid=8561129031&s=electronics&sr=1-5","4.1","3GB","64GB","5000mAH","Qualcomm Snapdragon 460 octa core processor");
        //End Of Vivo
        //Redmi
        phoneDatabase.addPhoneInfo("Redmi","Note 8 Pro","https://m.media-amazon.com/images/I/61ACGAKmw3L._AC_UY218_.jpg",18499.00,"https://www.amazon.in/Renewed-Redmi-Note-Storage-Processor/dp/B084KV1R4J/ref=sr_1_1?dchild=1&keywords=redmi&qid=1606199221&refinements=p_n_feature_seven_browse-bin%3A16757455031&rnid=8561129031&s=electronics&sr=1-1","3.7","8GB","128GB","5000mAH","Helio G90T gaming processor");
        phoneDatabase.addPhoneInfo("Redmi","K20 Pro","https://m.media-amazon.com/images/I/31J0tdzHjyL._AC_UY218_.jpg",25999.00,"https://www.amazon.in/Renewed-Redmi-Carbon-Black-Storage/dp/B081858KB9/ref=sr_1_2?dchild=1&keywords=redmi&qid=1606199221&refinements=p_n_feature_seven_browse-bin%3A16757455031&rnid=8561129031&s=electronics&sr=1-2","2.4","8GB","256GB","400mAH","2.84GHz Kryo 485 + 3x2.42GHz Kryo 485 + 4x1.8GHz Kryo 485 Qualcomm Snapdragon 855 octa core processor");
        phoneDatabase.addPhoneInfo("Redmi","Note 9 Pro Max","https://m.media-amazon.com/images/I/81u6E5niDiL._AC_UY218_.jpg",16999.00,   "https://www.amazon.in/Test-Exclusive-557/dp/B077PWJ8RS/ref=sr_1_1?dchild=1&keywords=redmi&qid=1606199461&refinements=p_n_feature_seven_browse-bin%3A16757454031&rnid=8561129031&s=electronics&sr=1-1","4.1","6GB","64GB","5020mAH","2.3GHz Qualcomm Snapdragon 720G");
        phoneDatabase.addPhoneInfo("Redmi","Note 9","https://m.media-amazon.com/images/I/4163N5X5v7L._AC_UY218_.jpg",16990.00,"https://www.amazon.in/Redmi-Note-Pebble-Grey-Storage/dp/B086977TR6/ref=sr_1_2?dchild=1&keywords=redmi+note+9&qid=1606279773&s=electronics&sr=1-2","4.1","4GB","64GB","5020mAH","2.0GHz Mediatek Helio G85 octa core processor");
        phoneDatabase.addPhoneInfo("Redmi","9","https://m.media-amazon.com/images/I/71uZrDPrsRL._AC_UY218_.jpg",8999.00,"https://www.amazon.in/Redmi-Sky-Blue-64GB-Storage/dp/B08697N43N/ref=sr_1_1?dchild=1&keywords=redmi&qid=1606200469&refinements=p_n_feature_seven_browse-bin%3A16757454031%7C8561133031&rnid=8561129031&s=electronics&sr=1-1","4.0","4GB","64GB","5000mAH","2.3GHz Mediatek Helio G35 octa core processor");
        phoneDatabase.addPhoneInfo("Redmi","9 Prime","https://m.media-amazon.com/images/I/719lhUzicYL._AC_UY218_.jpg",9990.00,"https://www.amazon.in/Redmi-Prime-Storage-Display-Camera/dp/B08697MJDK/ref=sr_1_7?dchild=1&keywords=redmi&qid=1606200469&refinements=p_n_feature_seven_browse-bin%3A16757454031%7C8561133031&rnid=8561129031&s=electronics&sr=1-7","4.2","4GB","64GB","5020mAH","2.0 GHz Mediatek Helio G80 octa core processor");
        phoneDatabase.addPhoneInfo("Redmi","9A","https://m.media-amazon.com/images/I/51+yaqN2O0L._AC_UY218_.jpg",7499.00,"https://www.amazon.in/Redmi-9A-Midnight-3GB-32GB/dp/B08697KLZP/ref=sr_1_1?dchild=1&keywords=redmi&qid=1606200685&refinements=p_n_feature_seven_browse-bin%3A8561132031&rnid=8561129031&s=electronics&sr=1-1","4.2","3GB","32GB","5000mAH","2.0GHz clock speed Mediatek Helio G25 octa core processor");
        phoneDatabase.addPhoneInfo("Redmi","8A","https://m.media-amazon.com/images/I/71BBqZGY3dL._AC_UY218_.jpg",8999.00,"https://www.amazon.in/Redmi-8A-Dual-Midnight-Storage/dp/B086982VLB/ref=sr_1_3?dchild=1&keywords=redmi&qid=1606200685&refinements=p_n_feature_seven_browse-bin%3A8561132031&rnid=8561129031&s=electronics&sr=1-3","4.0","3GB","64GB","5000mAH","1.95Ghz Snapdragon 439 processor");
        phoneDatabase.addPhoneInfo("Redmi","MI 6 Pro","https://m.media-amazon.com/images/I/819ak1fO+1L._AC_UY218_.jpg",12851.00,"https://www.amazon.in/REDMI-Note-PRO-64GB-RED/dp/B07N8SDZ4M/ref=sr_1_9?dchild=1&keywords=Redmi&qid=1606713971&refinements=p_89%3ARedmi%2Cp_n_feature_seven_browse-bin%3A16757454031%2Cp_n_feature_eight_browse-bin%3A8561116031&rnid=8561111031&s=electronics&sr=1-9","4.2","6GB","64GB","4000mAH","1.8GHz Qualcomm Snapdragon 636 octa core processor");

        //End Of Redmi
        //One Plus
        phoneDatabase.addPhoneInfo("One Plus","Nord 5G","https://m.media-amazon.com/images/I/71aqNzEqj0L._AC_UY218_.jpg",27999.00,"https://www.amazon.in/OnePlus-Nord-Marble-128GB-Storage/dp/B086977J3K/ref=sr_1_1?dchild=1&keywords=oneplus&qid=1606200914&refinements=p_89%3AOnePlus%2Cp_n_feature_seven_browse-bin%3A16757455031&rnid=8561129031&s=electronics&sr=1-1","4.1","8GB","128GB","4115mAH","2.4GHz Kryo 475 Prime + 2.2GHz Kryo 475 Gold + 6x1.8GHz Kryo 475 Silver Qualcomm Snapdragon 765G 5G Mobile Platform octa core processor");
        phoneDatabase.addPhoneInfo("One Plus","Nord 5G","https://m.media-amazon.com/images/I/71zlbKfhFsL._AC_UY218_.jpg",29999.00,"https://www.amazon.in/OnePlus-Nord-Gray-256GB-Storage/dp/B08697WT6D/ref=sr_1_2?dchild=1&keywords=oneplus&qid=1606200914&refinements=p_89%3AOnePlus%2Cp_n_feature_seven_browse-bin%3A16757455031&rnid=8561129031&s=electronics&sr=1-2","4.1","12GB","256GB","4115mAH","2.4GHz Kryo 475 Prime + 2.2GHz Kryo 475 Gold + 6x1.8GHz Kryo 475 Silver Qualcomm Snapdragon 765G 5G mobile platform octa core processor");
        phoneDatabase.addPhoneInfo("One Plus","8T 5G","https://m.media-amazon.com/images/I/71m05O2uNdL._AC_UY218_.jpg",45990.00,"https://www.amazon.in/OnePlus-Mirror-Black-128GB-Storage/dp/B085J19V4P/ref=sr_1_4?dchild=1&keywords=oneplus&qid=1606200914&refinements=p_89%3AOnePlus%2Cp_n_feature_seven_browse-bin%3A16757455031&rnid=8561129031&s=electronics&sr=1-4","4.2","12GB","256GB","4500mAH","2.86 GHz Qualcomm Snapdragon 865 Octa-core Processor");
        phoneDatabase.addPhoneInfo("One Plus","8","https://m.media-amazon.com/images/I/71vjeMN4V9L._AC_UY218_.jpg",41999.00,"https://www.amazon.in/OnePlus-Onyx-Black-128GB-Storage/dp/B071Z97T2C/ref=sr_1_7?dchild=1&keywords=oneplus&qid=1606200914&refinements=p_89%3AOnePlus%2Cp_n_feature_seven_browse-bin%3A16757455031&rnid=8561129031&s=electronics&sr=1-7","4.3","8GB","128GB","4300mAH"," 2.86GHz of clock speed with Qualcomm Snapdragon 865 powered by Kryo 585 CPU octa core processor");
        phoneDatabase.addPhoneInfo("One Plus","7T Pro","https://m.media-amazon.com/images/I/61FRLa8IFTL._AC_UY218_.jpg",43990.00,"https://www.amazon.in/Test-Exclusive-749/dp/B07DJ8K2KT/ref=sr_1_10?dchild=1&keywords=oneplus&qid=1606200914&refinements=p_89%3AOnePlus%2Cp_n_feature_seven_browse-bin%3A16757455031&rnid=8561129031&s=electronics&sr=1-10","4.4","8GB","256GB","4085mAH",".96GHz Qualcomm Snapdragon 855 Plus AI Engine octa-core, 7nm processor");
        phoneDatabase.addPhoneInfo("One Plus","8 Pro","https://m.media-amazon.com/images/I/61YSMhOd5EL._AC_UY218_.jpg",59999.00,"https://www.amazon.in/Test-Exclusive-545/dp/B077Q42GDF/ref=sr_1_10?dchild=1&keywords=oneplus&qid=1606714272&refinements=p_n_feature_seven_browse-bin%3A16757454031%7C16757455031&rnid=8561129031&s=electronics&sr=1-10","4.3","12GB","256GB","4150mAH","Qualcomm Snapdragon 865, powered by Kryo 585 CPU octa core processor");
        phoneDatabase.addPhoneInfo("One Plus","7T","https://m.media-amazon.com/images/I/71ncRs6HzyL._AC_UY218_.jpg",37999.00,"https://www.amazon.in/Test-Exclusive-748/dp/B07DJLVJ5M/ref=sr_1_9?dchild=1&keywords=oneplus&qid=1606714272&refinements=p_n_feature_seven_browse-bin%3A16757454031%7C16757455031&rnid=8561129031&s=electronics&sr=1-9","4.5","8GB","256GB","3800mAH","2.96GHz Qualcomm Snapdragon 855 Plus AI Engine octa-core, 7nm processor");
        phoneDatabase.addPhoneInfo("One Plus","5T","https://m.media-amazon.com/images/I/81qqBAKRhTL._AC_UY218_.jpg",37999.00,"https://www.amazon.in/Certified-REFURBISHED-OnePlus-5T-Midnight/dp/B078T6991W/ref=sr_1_19?dchild=1&keywords=oneplus&qid=1606714272&refinements=p_n_feature_seven_browse-bin%3A16757454031%7C16757455031&rnid=8561129031&s=electronics&sr=1-19#customerReviews","3.0","8GB","128GB","3300mAH","2.45GHz Qualcomm MSM8998 Snapdragon 835 dual core processor");
        //One Plus
    }
}