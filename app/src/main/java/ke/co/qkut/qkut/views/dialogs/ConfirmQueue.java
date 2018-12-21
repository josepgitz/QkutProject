package ke.co.qkut.qkut.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ke.co.qkut.qkut.R;

public class ConfirmQueue extends DialogFragment {
    Button button;
    @Nullable
 /*   @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.activity_queue,container,false);
        return view;
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.activity_queue,null,false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      builder.setView(view);

        return builder.create();
    }
}
