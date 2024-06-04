package com.example.smartlight;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class MainActivity extends AppCompatActivity {

    ToggleButton led_btn,pir_btn,ldr_btn;
    View ring_led,ring_pir,ring_ldr;
    TextView tv_led,tv_pir,tv_ldr;
    String led_status,pir_status,ldr_status;
    RelativeLayout loadingCircle;
    private static final String STATUS_ON  = "ON";
    private static final String STATUS_OFF  = "OFF";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        loadingCircle = findViewById(R.id.loadingPanel);

        led_btn = findViewById(R.id.toggleButton);
        ring_led = findViewById(R.id.ring);
        tv_led = findViewById(R.id.textViewLED);


        led_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBtnOnOff(led_btn,ring_led,tv_led);
                if(led_btn.isChecked()){
                    led_status = STATUS_ON;
                }
                else{
                    led_status = STATUS_OFF;
                }
                led_status = btnStatusCheck(led_btn,ring_led,tv_led);
            }
        });

        ldr_btn = findViewById(R.id.toggleButtonLDR);
        ring_ldr = findViewById(R.id.ringLDR);
        tv_ldr = findViewById(R.id.textViewLDR);

        ldr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBtnOnOff(ldr_btn,ring_ldr,tv_ldr);
                if(ldr_btn.isChecked()){
                    ldr_status = STATUS_ON;
                }
                else{
                    ldr_status = STATUS_OFF;
                }
                ldr_status = btnStatusCheck(ldr_btn,ring_ldr,tv_ldr);
            }
        });

        pir_btn = findViewById(R.id.toggleButtonPIR);
        ring_pir = findViewById(R.id.ringPIR);
        tv_pir = findViewById(R.id.textViewPIR);

        pir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleBtnOnOff(pir_btn,ring_pir,tv_pir);
                if(pir_btn.isChecked()){
                    pir_status = STATUS_ON;
                }
                else{
                    pir_status = STATUS_OFF;
                }
                pir_status = btnStatusCheck(pir_btn,ring_pir,tv_pir);
            }
        });
    }
    public int sendData() {
        try {
            // Send data to NodeMCU via HTTP POST request
            String nodeMCUUrl = "http://192.168.4.1/led";
            String dataToSend = "{\"led\":{\"status\":\"" + led_status + "\", \"pir_status\":\"" + pir_status + "\", \"ldr_status\":\"" + ldr_status + "\"}}";
            URL url = new URL(nodeMCUUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(dataToSend.getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            connection.disconnect(); // Close the connection
            return responseCode;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return -1; // Indicates a URL error
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return -2; // Indicates an I/O error
        }
    }

    public void toggleBtnOnOff(ToggleButton btn, View ring,TextView tv){
        if(btn.isChecked()){
            btn.setTextColor(getResources().getColor(R.color.green));
            ring.setBackground(getResources().getDrawable(R.drawable.green_ring_design));
            tv.setTextColor(getResources().getColor(R.color.green));
        }
        else{
            btn.setTextColor(getResources().getColor(R.color.gray));
            ring.setBackground(getResources().getDrawable(R.drawable.gray_ring_design));
            tv.setTextColor(getResources().getColor(R.color.midnight_blue));
        }
    }

    public String btnStatusCheck(ToggleButton btn,View ring,TextView tv){

        String status;
        if(btn.isChecked()){
            status = STATUS_ON;
            int response = sendData();
            if(response != 200){
                btn.setChecked(false);
                toggleBtnOnOff(btn,ring,tv);
                status = STATUS_OFF;
            }
            else{
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            status = STATUS_OFF;
            int response = sendData();
            if(response != 200){
                btn.setChecked(true);
                toggleBtnOnOff(btn,ring,tv);
                status = STATUS_ON;
            }
            else{
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }
        return status;
    }

}