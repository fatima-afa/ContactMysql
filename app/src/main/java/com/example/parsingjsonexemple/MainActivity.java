package com.example.parsingjsonexemple;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    JSONArray jsonArray = null;
    contact[] contactArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetAllContacts().execute();

       // ArrayList<contact> arrayList = new ArrayList<>();

        CustomAdapter adapter = new CustomAdapter(this, contactArray);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "Hi",Toast.LENGTH_LONG).show();

            }
        });
    }

    private class GetAllContacts extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {

            //1-- make connection with database ( web services )
            try{
                URL url = new URL("http://10.0.2.2:8080/contacts/getAllContacts.php");
                URLConnection urlConnection = url.openConnection();

                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String ligne;
                while ((ligne = bufferedReader.readLine()) != null){
                    jsonArray = new JSONArray(ligne);
                }

                Log.d("result", jsonArray.toString());

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            Gson gson=new Gson();
            contactArray=gson.fromJson(jsonArray.toString(),contact[].class);
            return null;
        }
    }

    public class CustomAdapter extends BaseAdapter {

        Context context;
        contact[] arrayList;

        public CustomAdapter(Context context, contact[] arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            return arrayList.length;
        }

        @Override
        public Object getItem(int position) {
            return arrayList[position];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
            }
            TextView name, job, email, phone;
            name = (TextView) convertView.findViewById(R.id.txt_name);
            job = (TextView) convertView.findViewById(R.id.txt_job);
            email = (TextView) convertView.findViewById(R.id.txt_email);
            phone = (TextView) convertView.findViewById(R.id.txt_phone);
            Button callbtn = (Button) convertView.findViewById(R.id.btn_call);

            name.setText(arrayList[position].getName());
            job.setText(arrayList[position].getJob());
            email.setText(arrayList[position].getEmail());
            phone.setText(arrayList[position].getPhone());

            final contact temp = arrayList[position];


            callbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri telephone = Uri.parse("tel:" + temp.getPhone());
                    Intent secondeActivite = new Intent(Intent.ACTION_DIAL, telephone);
                    startActivity(secondeActivite);

                }
            });

            return convertView;
        }
    }

}