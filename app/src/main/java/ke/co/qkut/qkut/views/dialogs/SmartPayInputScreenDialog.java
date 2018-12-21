package ke.co.qkut.qkut.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import ke.co.qkut.qkut.R;

public class SmartPayInputScreenDialog extends SmartPayDialogScreen {
    private EditText confirmSessionCodeeditText;
    private  Button buttoncomfirm;
    private  List<DialogListener> listenerList= new ArrayList<>() ;

    public  List<DialogListener> getListenerList() {
        return listenerList;
    }

    public void setListenerList(DialogListener listenerList) {
       this.listenerList.add(listenerList);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(getActivity());
        layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.smart_pay_input_screen_dialog_layout, null);
        confirmSessionCodeeditText=view.findViewById(R.id.confirmSessionCode);
        buttoncomfirm = view.findViewById(R.id.ComfirmButton);
        buttoncomfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=confirmSessionCodeeditText.getText().toString();
                if(code.isEmpty()){
                    confirmSessionCodeeditText.setError("Code required to proceed");
                }else {
                    for (DialogListener dialog: listenerList) {
                        dialog.codeReceived(code);
                    }
                    dismiss();
                }

            }
        });
        iconView = view.findViewById(R.id.cormfirmImage);
        messageStatusView = view.findViewById(R.id.successMessage);
        if (getIconId() != 0) {

            iconView.setImageResource(getIconId());
        }
        if (getMessageStatus() != null) {
            messageStatusView.setText(getMessageStatus());
        }
        dialogbuilder.setView(view);
Dialog dialog=dialogbuilder.create();
dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;

    }

}
