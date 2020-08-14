package com.kim9212.ex66geocoding;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etAddress;
    EditText etLat, etLng;

    double lat,lng;
    double lat2,lng2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAddress= findViewById(R.id.et_address);
        etLat= findViewById(R.id.et_lat);
        etLng= findViewById(R.id.et_lng);

    }

    public void clickBtn(View view) {
        //주소 -> 좌표 (지오코딩)
        String addr= etAddress.getText().toString();

        //지오코딩 작업을 수행하는 객체 생성
        Geocoder geocoder= new Geocoder(this, Locale.KOREA);

        try {
            List<Address> addresses= geocoder.getFromLocationName(addr, 3);

            StringBuffer stringBuffer= new StringBuffer();
            for( Address t : addresses ){
                stringBuffer.append( t.getLatitude()+", "+ t.getLongitude()+"\n" );
            }

            android.app.AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage(stringBuffer.toString()).setPositiveButton("OK", null).create().show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void clickBtn2(View view) {
        //좌표 -> 주소 (역지오코딩)

        lat2=Double.parseDouble(etLat.getText().toString());
        lng2=Double.parseDouble(etLng.getText().toString());
        Geocoder geocoder= new Geocoder(this,Locale.KOREA);
        try {
            List<Address> addresses= geocoder.getFromLocation(lat2,lng2,3);
        StringBuffer buffer= new StringBuffer();
        for(Address t: addresses){
            buffer.append(t.getCountryName()+"\n");
            buffer.append(t.getPostalCode()+"\n");
            buffer.append(t.getAddressLine(0)+"\n"); //주소 1아니면 2아니면 3주세요
            buffer.append(t.getAddressLine(1)+"\n");
            buffer.append(t.getAddressLine(2)+"\n");

        buffer.append("-------------------\n");

        }
        new AlertDialog.Builder(this).setMessage(buffer.toString()).setPositiveButton("ok",null).create().show();

        lat=addresses.get(0).getLatitude();
        lng=addresses.get(0).getLongitude();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void clickshow(View view) {
        //지도앱실행하기
        Intent intent= new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri= Uri.parse("geo:"+lat+","+lng+"?q="+lat+","+lng);
        intent.setData(uri);

        startActivity(intent);//디바이스에 설치된지도앱이 실행됨

    }

    public void clickshow2(View view) {

        Uri uri=Uri.parse("geo:"+lat2+","+lng2+"?q="+lat2+","+lng2+"&z=10");//zoom:1-25
        Intent intent= new Intent(Intent.ACTION_VIEW,uri);//
        startActivity(intent);
    }
}
