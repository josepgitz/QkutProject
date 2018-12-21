package ke.co.qkut.qkut.views.fragments;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import ke.co.qkut.qkut.R;
public class FragmentApprove extends Fragment {
    LinearLayout rangeLinearLayout;
    CheckBox approveAll;
    EditText end,start;
    int count;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        count=bundle.getInt("count");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
View view= inflater.inflate(R.layout.actions_to_request_header,container,false);
        approveAll=view.findViewById(R.id.approveAll);
        rangeLinearLayout=view.findViewById(R.id.rangeLinearLayout);
        approveAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rangeLinearLayout.setVisibility(View.GONE);
                }else {
                    rangeLinearLayout.setVisibility(View.VISIBLE);

                }
            }
        });

        end=view.findViewById(R.id.end);
        start=view.findViewById(R.id.start);
        start.addTextChangedListener(new WatchInput(1));
end.setHint("max :"+count);
end.addTextChangedListener( new WatchInput());

        return view;
    }
   class WatchInput implements TextWatcher{
        int value=0;
public  WatchInput(int input){
    this.value=input;
}
        public  WatchInput(){

        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                Integer integer=Integer.parseInt(String.valueOf(s));

                if (value==0){
                    if (integer>(count-value)) {
                        end.setText(count-value + "");

                    }
                }else{
                    if (integer>(count-value)) {
                        start.setText(count-value + "");
                    }
                }

            }catch (NumberFormatException integer){

            }


        }
    }
}
