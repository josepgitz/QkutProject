package ke.co.qkut.qkut.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.util.factory.QrEngine;
import pl.droidsonroids.gif.GifImageView;

public class QRDialog extends DialogFragment implements View.OnClickListener {
    Button button;
    GifImageView imageView;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.activity_scan_barcode,null,false);
        ImageView close_icon=view.findViewById(R.id.close);
        close_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        imageView=view.findViewById(R.id.qrcode_view);
        builder.setView(view);
        QrEngine.init(imageView,(AppCompatActivity)getActivity());
        return builder.create();
    }

    @Override
    public void onClick(View v) {

    }

}
