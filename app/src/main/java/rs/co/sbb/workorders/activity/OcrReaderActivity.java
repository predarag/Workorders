package rs.co.sbb.workorders.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rs.co.sbb.workorders.R;
import rs.co.sbb.workorders.activity.ocr.IntentIntegrator;
import rs.co.sbb.workorders.activity.ocr.IntentResult;

public class OcrReaderActivity extends AppCompatActivity implements View.OnClickListener {

    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private TextView textValue;
    private Button scanBtn;

    private static final int RC_OCR_CAPTURE = 9003;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "OcrReaderActivity";

    private EditText etMac;
    private EditText etSerial;
    private EditText etMtaMac;

    private String clickType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_reader);

 ;
        //textValue = (TextView) findViewById(R.id.text_value);
        etMac = (EditText) findViewById(R.id.etMac);
        etSerial = (EditText) findViewById(R.id.etSerial);
        etMtaMac = (EditText) findViewById(R.id.etMtaMac);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnMac) {

            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
            clickType = "mac";
        }


        if (v.getId() == R.id.btnScanSerial) {

            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
            clickType = "serial";
        }

        if (v.getId() == R.id.btnScanMta) {

            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
            clickType = "mta";
        }
    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            //formatTxt.setText("FORMAT: " + scanFormat);
            //formatTxt.setText("FORMAT: " + scanFormat);
            if(clickType.equals("mac"))
                etMac.setText(scanContent);
            if(clickType.equals("serial"))
                etSerial.setText(scanContent);
            if(clickType.equals("mta"))
                etMtaMac.setText(scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
