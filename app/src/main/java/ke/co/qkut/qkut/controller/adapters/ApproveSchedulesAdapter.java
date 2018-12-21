package ke.co.qkut.qkut.controller.adapters;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.constants.GLobalHeaders;
import ke.co.qkut.qkut.constants.URL;
import ke.co.qkut.qkut.models.BusinessInfo;
import ke.co.qkut.qkut.models.ConfirmSchedule;
import ke.co.qkut.qkut.models.ScheduledService;
import ke.co.qkut.qkut.schedule_models.ScheduleManager;
import ke.co.qkut.qkut.util.newtork.local.NetworkConnection;
import ke.co.qkut.qkut.util.newtork.local.OnReceivingResult;
import ke.co.qkut.qkut.util.newtork.local.RemoteResponse;
import ke.co.qkut.qkut.views.activities.ApproveRequest;
import ke.co.qkut.qkut.views.dialogs.GeneralDialogBuilder;

public class ApproveSchedulesAdapter extends BaseAdapter {


    ProgressDialog progressDialog;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    private View progressContainer;
    private TextView progressText;
    private ImageView progressWarning;
    private LayoutInflater layoutInflater;
    private List<ConfirmSchedule> listData;
    private AppCompatActivity context;
    View view;
    ViewPager viewPager;
    public ApproveSchedulesAdapter(AppCompatActivity context, List<ConfirmSchedule> listData) {
        this.context = context;
        if (this.context!=null){
            layoutInflater =LayoutInflater.from(this.context );
        }

        this.listData = listData;
    }
    @Override
    public int getCount() {
        return (listData.size());
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ApproveSchedulesAdapter.ViewHolder rowHolder;
        final ConfirmSchedule c= listData.get(position);
        rowHolder = new ApproveSchedulesAdapter.ViewHolder();
        convertView = layoutInflater.inflate(R.layout.approve_row, parent, false);
        rowHolder.name=convertView.findViewById(R.id.name);
        rowHolder.serviceTextView=convertView.findViewById(R.id.serviceTextView);
        rowHolder.time=convertView.findViewById(R.id.time);
        rowHolder.number=convertView.findViewById(R.id.number);
        rowHolder.approved=convertView.findViewById(R.id.approved);
        rowHolder.reject=convertView.findViewById(R.id.reject);
        rowHolder.name.setText(c.getUsername());
        rowHolder.serviceTextView.setText(c.getService());
        rowHolder.time.setText(c.getScheduled_time());
        rowHolder.number.setText(c.getUsermobile());
        rowHolder.Company=convertView.findViewById(R.id.Company);
        rowHolder.Company.setText(c.getBusiness());
        rowHolder.approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rowHolder.approved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView name;
        TextView Company;
        TextView serviceTextView;
        TextView time;
        TextView number;
        Button approved;
        Button reject;

    }
    private void ShowProgressBar(boolean show, boolean hasWarning, String message) {
        if (progressContainer == null) {

            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Processing...");
            progressDialog.show();
        } else {
            progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
            progressWarning.setVisibility(hasWarning ? View.VISIBLE : View.GONE);
            progressText.setText(message);
        }
    }



}
