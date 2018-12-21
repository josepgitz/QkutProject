package ke.co.qkut.qkut.util.factory;

import android.content.Context;
import android.graphics.Bitmap;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;
import pl.droidsonroids.gif.GifImageView;

public class QrEngine {
    static  String details;

   private  QrEngine(GifImageView imageView){
       // Whatever you need to encode in the QR code
       MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
       try {
           BitMatrix bitMatrix = multiFormatWriter.encode(details, BarcodeFormat.QR_CODE,200,200);
           BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
           Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
           imageView.setImageBitmap(bitmap);
       } catch (WriterException e) {
           e.printStackTrace();
       }
   }
    public  static  void  init(GifImageView imageView, AppCompatActivity appCompatActivity){

        getQRFromUser(appCompatActivity,imageView);
       return  ;

    }
    public static  void getQRFromUser(final AppCompatActivity appCompatActivity, final GifImageView imageView){

        NetworkConnection.makeAGetRequest(URL.USER_QR, GLobalHeaders.getGlobalHeaders(appCompatActivity), new OnReceivingResult() {
            @Override
            public void onErrorResult(IOException e) {

            }

            @Override
            public void onReceiving100SeriesResponse(RemoteResponse remoteResponse) {

            }

            @Override
            public void onReceiving200SeriesResponse(RemoteResponse remoteResponse) {
                try {
                    JSONObject jsonObject= new JSONObject(remoteResponse.getMessage());
                    details=jsonObject.getJSONObject("data").getString("qrcode");
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            new QrEngine(imageView);
                            //;

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReceiving300SeriesResponse(RemoteResponse remoteResponse) {

            }

            @Override
            public void onReceiving400SeriesResponse(RemoteResponse remoteResponse) {

            }

            @Override
            public void onReceiving500SeriesResponse(RemoteResponse remoteResponse) {

            }
        });
    }

}
