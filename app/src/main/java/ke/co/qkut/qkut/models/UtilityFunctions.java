package ke.co.qkut.qkut.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;

import ke.co.qkut.qkut.R;


public class UtilityFunctions {


    public UtilityFunctions(){

    }
    //validate phone number
    //----------------------------------------------------------------------------------------
    public static boolean validatePhoneNumber(String phone){
        String regexStr = "^[+]?[0-9]{9,13}$";
       return phone.matches(regexStr) == true;
    }

    //validate an email address
    //-----------------------------------------------------------------------------------------
    public static boolean validateEmail(String email){
        String regexStr = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(regexStr)== true;
    }

    //save a string to shared preferences
    //-------------------------------------------------------------------------------------------
    public static void writeToSharedPreferences(Context context, String preferenceName, String key, String value){
        final SharedPreferences prefs =  context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //read a string to shared preferences
    //-------------------------------------------------------------------------------------------
    public static String readFromSharedPreferences(Context context, String preferenceName, String key){

        SharedPreferences prefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        return prefs.getString(key, null);
    }
    //save binary data to local storage
    //-------------------------------------------------------------------------------------------
    public static void saveToLocalStorage(Context context, String filename, byte[] fileContents)
            throws IOException {

        try {
            FileOutputStream outputStream = new FileOutputStream(filename);
            outputStream.write(fileContents);
            outputStream.close();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    //load bitmap from file
    //-------------------------------------------------------------------------------------------
    public static Bitmap loadBitmapFromLocalStorage(String filename){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(filename, options);
        return bitmap;
    }
    //get rounded bitmap
    //-------------------------------------------------------------------------------------------
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
    //read auth data from persistent storage
    //----------------------------------------------------------------------------------------
    public static AuthStructure readAuthPersistentData(Context context) {

        AuthStructure authStructure = new AuthStructure();

        authStructure.accessTocken = UtilityFunctions.readFromSharedPreferences(context,
                context.getString(R.string.shared_preference_name),
                context.getString(R.string.shared_preference_auth_access_token_key));


        authStructure.tokenType = UtilityFunctions.readFromSharedPreferences(context,
                context.getString(R.string.shared_preference_name),
                context.getString(R.string.shared_preference_auth_token_type_key));

        authStructure.expiresAt = UtilityFunctions.readFromSharedPreferences(context,
                context.getString(R.string.shared_preference_name),
                context.getString(R.string.shared_preference_auth_token_expires_at_key));

        return authStructure;
    }
    //check if internet is connected
    //-------------------------------------------------------------------------------------------
    public static boolean isInternetAvailable(String testLink) {
        try {
            InetAddress ipAddr = InetAddress.getByName(testLink);
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
