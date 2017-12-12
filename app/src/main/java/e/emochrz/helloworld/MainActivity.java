package e.emochrz.helloworld;

//package com.example.androidclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import e.emochrz.helloworld.R;

public class MainActivity extends Activity {

    private static final String addresDefault = "127.0.0.1";
    private static final int port = 7891;

    TextView textResponse, temperature, humidity, brightness;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = findViewById(R.id.address);
        editTextPort = findViewById(R.id.port);
        buttonConnect = findViewById(R.id.connect);
        textResponse = findViewById(R.id.response);
        temperature = findViewById(R.id.temperatureName);
        humidity = findViewById(R.id.humidityName);
        brightness = findViewById(R.id.brightnessName);
        buttonUpdate = findViewById(R.id.buttonName);
        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        buttonConnect.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                buttonConnect.setText("CONNECTED!");
                editTextAddress.setText(addresDefault);
                editTextPort.setText(port);
            }});

        buttonUpdate = findViewById(R.id.buttonName);

        buttonUpdate.setOnClickListener(new OnClickListener() {
            // *** USTAWIAM PUSTY TEKST
            @Override
            public void onClick(View v) {
                temperature.setText("16*C");
            }
        });
    }

    OnClickListener buttonConnectOnClickListener =
            new OnClickListener(){

        @Override
        public void onClick(View arg0) {
     /*
      * You have to verify editTextAddress and
      * editTextPort are input as correct format.
      */

     MyClientTask myClientTask = new MyClientTask(
        editTextAddress.getText().toString(),
        Integer.parseInt(editTextPort.getText().toString()));
        myClientTask.execute();
                }};

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String response;

        MyClientTask(String addr, int port){
            dstAddress = addr;
            dstPort = port;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                Socket socket = new Socket(dstAddress, dstPort);
                InputStream inputStream = socket.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream =
                        new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];

                //////
                PrintStream out = new PrintStream(socket.getOutputStream());
                out.println("Hello world!");
                //////
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }

                socket.close();
                response = byteArrayOutputStream.toString("UTF-8");

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textResponse.setText(response);
            super.onPostExecute(result);
        }

    }

}

//
//
//
//import android.support.v7.app.AppCompatActivity;
//        import android.os.Bundle;
//
////package com.example.androidclient;
//
//        import java.io.ByteArrayOutputStream;
//        import java.io.IOException;
//        import java.io.InputStream;
//        import java.io.OutputStream;
//        import java.net.Socket;
//        import java.net.UnknownHostException;
//
//        import android.os.AsyncTask;
//        import android.os.Bundle;
//        import android.app.Activity;
//        import android.view.View;
//        import android.view.View.OnClickListener;
//        import android.widget.Button;
//        import android.widget.EditText;
//        import android.widget.SeekBar;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//import e.emochrz.helloworld.R;
//
//    public class MainActivity extends AppCompatActivity {
//    TextView temperature, humidity, brightness;
//    Button button;
//    OutputStream byteArrayOutputStream;
//    Socket socket;
//    byte[] buffer = new byte[1024];
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        temperature = findViewById(R.id.temperatureName);
//        humidity = findViewById(R.id.humidityName);
//        brightness = findViewById(R.id.brightnessName);
//        button = findViewById(R.id.buttonName);
//
////        PWM_byte = (byte) PWM;
////        TextViewresponse.setText(S);
//
//        button.setOnClickListener(new OnClickListener() {
//            // *** USTAWIAM PUSTY TEKST
//            @Override
//            public void onClick(View v) {
//                temperature.setText("16*C");
//            }
//        });
////        SetSeekBarPWM();
//    }
//
////    public void EdytujTekst(View view)
////    {
////        String S = "PWM: " + editTextPWM_value.getText().toString();
////        PWM = Integer.parseInt(editTextPWM_value.getText().toString());     // konwertuje na inta
////        PWM_byte = (byte) PWM;
////        TextViewresponse.setText(S);
////    }
////
//    public void SetSeekBarPWM() {
//        temperature.setText("PWM: " + 1);
//        // *** PRZYCISK "NASŁUCHUJE"
//        OnClickListener buttonConnectOnClickListener = new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                // Co sie stanie po wciśnięciuCONNETCT?
//
//                myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()));
//                myClientTask.execute();
//
//            }
//        };
//    }
//
//    public class MyClientTask extends AsyncTask<Void, Void, Void>
//    {
//        MyClientTask(String addr, int port)
//        {
//            String dstAddress = addr;
//            int dstPort = port;
//        }
//
//        // *** KLIENT
//        @Override
//        protected Void doInBackground(Void... arg0)
//        {
//            // *** WYSYŁAM
//            try
//            {
//                socket = new Socket("172.24.1.1", 8080);
//                byteArrayOutputStream = socket.getOutputStream();
//            }
//            catch (IOException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }
//}