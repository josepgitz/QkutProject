package ke.co.qkut.qkut.controller.adapters;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import ke.co.qkut.qkut.Activity_Approve_Schedule;
import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.models.ConfirmSchedule;

public class AlertAdapter extends BaseAdapter {
    ProgressDialog progressDialog;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    private View progressContainer;
    private TextView progressText;
    private ImageView progressWarning;
    private LayoutInflater layoutInflater;
    private List<ConfirmSchedule> listData;
    private AppCompatActivity context;
    public AlertAdapter(AppCompatActivity context, List<ConfirmSchedule> listData) {
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
        final AlertAdapter.viewHolder rowHolder;
        final ConfirmSchedule c= listData.get(position);
        rowHolder = new AlertAdapter.viewHolder();
        convertView = layoutInflater.inflate(R.layout.activity_alert_row, parent, false);
          rowHolder.name=convertView.findViewById(R.id.company_alert_name);
          rowHolder.alert_message=convertView.findViewById(R.id.alert_message);
          rowHolder.alert_date=convertView.findViewById(R.id.alert_date);
          rowHolder.name=convertView.findViewById(R.id.company_alert_name);
          rowHolder.more_button=convertView.findViewById(R.id.more_button);
          rowHolder.name.setText(c.getUsername());
          rowHolder.alert_message.setText(c.getUsername()+" has scheduled to visit \n"+c.getBusiness()+" for "+c.getService()+" on "+c.getScheduled_time()+" \n please review");
          rowHolder.alert_date.setText(c.getScheduled_time());
        return convertView;
    }

    class viewHolder{

        TextView name, Service, time,alert_message,alert_date;
        Button more_button;
    }


}
