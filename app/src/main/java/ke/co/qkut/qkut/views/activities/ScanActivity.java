package ke.co.qkut.qkut.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;

import ke.co.qkut.qkut.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    TextView scanimage;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView=findViewById(R.id.qrCodeScanner);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
       scanimage=findViewById(R.id.scanimage);
        // Set the scanner view as the content view
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();

        setContentView(R.layout.activity_scan);

        new IntentIntegrator(this).initiateScan();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        // Log.v("tag", rawResult.getText()); // Prints scan results
        // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        scanimage.setText(rawResult.getText());
        onBackPressed();

        // If you would like to resume scanning, call this method below:

    }
}
