package ke.co.qkut.qkut.controller.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ke.co.qkut.qkut.R;
import ke.co.qkut.qkut.datastore.LocalDatabase;
import ke.co.qkut.qkut.models.BusinessInfo;
import ke.co.qkut.qkut.models.Department;
import ke.co.qkut.qkut.models.Queue;
import ke.co.qkut.qkut.models.QueueHandler;
import ke.co.qkut.qkut.models.Service;
import ke.co.qkut.qkut.models.Visit;
import ke.co.qkut.qkut.models.VisitHandler;

public class BusinessInfoAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List <BusinessInfo> listData;
    private AppCompatActivity context;

     View view;
    ViewPager viewPager;

    public BusinessInfoAdapter(AppCompatActivity context, List<BusinessInfo> listData,ViewPager pager,View view) {
        this.context = context;
           if (this.context!=null){
               layoutInflater =LayoutInflater.from(this.context );

           }
           this.viewPager=pager;
        this.view=view;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return (BusinessInfo)this.listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder rowHolder;
        final BusinessInfo b = listData.get(position);

            rowHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.place_row, parent, false);
            final View convertViewF = convertView;
            rowHolder.businessName =  convertView.findViewById(R.id.business_name);
            rowHolder.placeDistance=convertView.findViewById(R.id.placeDistance);
            rowHolder.businessIndustry = convertView.findViewById(R.id.business_industry);
            rowHolder.businessDescription = convertView.findViewById(R.id.business_description);
            rowHolder.businessAddress = convertView.findViewById(R.id.business_address);
            rowHolder.sector = convertView.findViewById(R.id.business_sector);
            rowHolder.industryGroup = convertView.findViewById(R.id.business_industry_group);
            rowHolder.industry = convertView.findViewById(R.id.business_industry2);
            rowHolder.subIndustry = convertView.findViewById(R.id.business_sub_industry);
            rowHolder.country = convertView.findViewById(R.id.business_country);
            rowHolder.town = convertView.findViewById(R.id.business_town);
            rowHolder.building = convertView.findViewById(R.id.business_building);
            rowHolder.btnDetails = convertView.findViewById(R.id.button_business_details);
            rowHolder.btnVisit = convertView.findViewById(R.id.button_visit);
            rowHolder.btnQueue = convertView.findViewById(R.id.button_queue);
            final View detailsView = convertView.findViewById(R.id.place_details_container);
            final LinearLayout servicesView = convertView.findViewById(R.id.place_service_container);
            final LinearLayout visitsView = convertView.findViewById(R.id.place_visit_container);

            rowHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(detailsView.getVisibility() == View.VISIBLE) {
                        detailsView.setVisibility(View.GONE);
                        ((Button)v).setText("more details");
                        showPointer(convertViewF, R.id.button_business_details_ptr, false);
                    }
                    else{
                        detailsView.setVisibility(View.VISIBLE);
                        servicesView.setVisibility(View.GONE);
                        visitsView.setVisibility(View.GONE);
                        ((Button)v).setText("less details");
                        showPointer(convertViewF, R.id.button_business_details_ptr, true);
                    }
                }
            });

            rowHolder.btnVisit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(visitsView.getVisibility() == View.VISIBLE) {
                        visitsView.setVisibility(View.GONE);
                        showPointer(convertViewF, R.id.button_visit_ptr, false);
                    }
                    else{
                        detailsView.setVisibility(View.GONE);
                        servicesView.setVisibility(View.GONE);
                        visitsView.setVisibility(View.VISIBLE);
                        rowHolder.btnDetails.setText("more details");
                        showPointer(convertViewF, R.id.button_visit_ptr, true);
                    }
                }
            });

            rowHolder.btnQueue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(servicesView.getVisibility() == View.VISIBLE) {
                        servicesView.setVisibility(View.GONE);
                        showPointer(convertViewF, R.id.button_queue_ptr, false);
                    }
                    else{
                        detailsView.setVisibility(View.GONE);
                        visitsView.setVisibility(View.GONE);
                        servicesView.setVisibility(View.VISIBLE);
                        rowHolder.btnDetails.setText("more details");
                        showPointer(convertViewF, R.id.button_queue_ptr, true);
                    }
                }
            });

            ArrayList<Department> dList = listData.get(position).getDepartmentList().getDepartmentList();

            listData.get(position).serviceList = new ArrayList<Service>();

            for(int i =0; i < dList.size(); i++){
                final Department d = dList.get(i);
                ArrayList<Service> sList = d.getServiceList().getServiceList();
                for(int j = 0; j < sList.size(); j++){
                    final Service s = sList.get(j);
                    s.setDepartmentName(dList.get(i).getName());
                    ServiceViewHolder serviceViewHolder = new ServiceViewHolder();
                    ServiceViewHolder visitViewHolder = new ServiceViewHolder();

                    View serviceView = layoutInflater.inflate(R.layout.place_service, servicesView, false);
                    View visitView = layoutInflater.inflate(R.layout.place_visit, visitsView, false);

                    serviceViewHolder.serviceName =  serviceView.findViewById(R.id.service_name);
                    serviceViewHolder.serviceDescription = serviceView.findViewById(R.id.service_description);
                    serviceViewHolder.departmentName = serviceView.findViewById(R.id.service_department);
                    serviceViewHolder.queueSize = serviceView.findViewById(R.id.service_queue_size);
                    serviceViewHolder.queueSpeed = serviceView.findViewById(R.id.service_queue_speed);
                    serviceViewHolder.btnQueueHere = serviceView.findViewById(R.id.button_queue_okay);
                    visitViewHolder.serviceName =  visitView.findViewById(R.id.service_name);
                    visitViewHolder.serviceDescription = visitView.findViewById(R.id.service_description);
                    visitViewHolder.departmentName = visitView.findViewById(R.id.service_department);
                    visitViewHolder.btnVisitHere = visitView.findViewById(R.id.button_visit_okay);
                    //visitViewHolder.btnVisitHere.setBackgroundColor(Color.RED);
                    visitViewHolder.btnVisitHere.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (LocalDatabase.getToken(context)==LocalDatabase.NOT_LOGGED_IN){
                                Toast.makeText(context, "To Visit You Need an Account with us", Toast.LENGTH_SHORT).show();
                                viewPager.setCurrentItem(1,true);
                            }else{
                                VisitHandler visitHandler = new VisitHandler(context, view);
                                visitHandler.ShowVisit(view);
                                visitHandler.initialize(new Visit(b, s));
                            }
                        }
                    });
                    serviceViewHolder.btnQueueHere.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                              if (LocalDatabase.getToken(context) == LocalDatabase.NOT_LOGGED_IN) {
                                  Toast.makeText(context, "To Queue You Need an Account with us", Toast.LENGTH_SHORT).show();
                                  viewPager.setCurrentItem(1, true);
                              } else {
                                  QueueHandler queueHandler = new QueueHandler(context, view);
                                  queueHandler.initialize(new Queue(b, s));
                                  QueueHandler.ShowQueue(view);
                              }

                        }
                    });
                    serviceViewHolder.serviceName.setText(s.getName());
                    serviceViewHolder.serviceDescription.setText(s.getDescription());
                    serviceViewHolder.departmentName.setText(s.getDepartmentName());
                    serviceViewHolder.queueSize.setText("Queue Size: " + s.getQueueSize());
                    serviceViewHolder.queueSpeed.setText("Queue Speed: " + s.getQueueSpeed() + "/hr");

                    visitViewHolder.serviceName.setText(s.getName());
                    visitViewHolder.serviceDescription.setText(s.getDescription());
                    visitViewHolder.departmentName.setText(s.getDepartmentName());

                    if(s.getIsQueued() == 1) {
                        serviceViewHolder.btnQueueHere.setText("View Queue ");
                        serviceViewHolder.btnQueueHere.setBackgroundColor(
                                context.getResources().getColor(R.color.colorDanger));

                        serviceViewHolder.btnQueueHere.setPressed(true);
                    }
                    else if(s.getIsQueued() == 0 && s.isOpen()) {
                        serviceViewHolder.btnQueueHere.setText("Queue Here");
                        serviceViewHolder.btnQueueHere.setBackgroundColor(
                                context.getResources().getColor(R.color.colorPrimaryDark));
                    }
                    else if(s.isInviteOnly()) {
                        serviceViewHolder.btnQueueHere.setText("Restricted");
                        serviceViewHolder.btnQueueHere.setBackgroundColor(
                                context.getResources().getColor(R.color.colorGrey));

                    }

                    if(s.getIsVisited() == 1) {
                        visitViewHolder.btnVisitHere.setText("View");
                        visitViewHolder.btnVisitHere.setBackgroundColor(
                                context.getResources().getColor(R.color.colorDanger));

                    }
                    else if(s.getIsVisited() == 0 && s.isOpen()) {
                        visitViewHolder.btnVisitHere.setText("Schedule");
                        visitViewHolder.btnVisitHere.setBackgroundColor(
                                context.getResources().getColor(R.color.colorAccent));
                    }

                    else if(s.isInviteOnly()) {
                        visitViewHolder.btnVisitHere.setText("Restricted");
                        visitViewHolder.btnVisitHere.setBackgroundColor(
                                context.getResources().getColor(R.color.colorGrey));
                        visitViewHolder.btnVisitHere.setEnabled(false);
                    }

                    visitViewHolder.serviceName.setText(s.getName());
                    visitViewHolder.serviceDescription.setText(s.getDescription());
                    visitViewHolder.departmentName.setText(s.getDepartmentName());

                    listData.get(position).serviceList.add(s);



                   /* visitViewHolder.btnVisitHere.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "Visit", Toast.LENGTH_SHORT).show();
                           // ViewSwapper.ShowVisitForm(v.getRootView(), b, s);
                        }
                    });
*/
                    serviceView.setTag(serviceViewHolder);
                    visitView.setTag(serviceViewHolder);

                    servicesView.addView(serviceView);
                    visitsView.addView(visitView);

                }
            }

            convertView.setTag(rowHolder);

        rowHolder.businessName.setText(listData.get(position).getName());
        rowHolder.businessIndustry.setText(listData.get(position).getIndustryDescription());
        rowHolder.businessDescription.setText(listData.get(position).getDescription());
        rowHolder.businessAddress.setText(listData.get(position).getAddress());
        rowHolder.placeDistance.setText(Html.fromHtml(String.format("%.2f",listData.get(position).getDistance())+"<b>KM Away</b>"));

        //details
        rowHolder.sector.setText(Html.fromHtml("<b>Sector: </b>" + listData.get(position).getSector()));
        rowHolder.industryGroup.setText(Html.fromHtml("<b>Industry Group: </b>" + listData.get(position).getIndustryGroup()));
        rowHolder.industry.setText(Html.fromHtml("<b>Industry: </b>" + listData.get(position).getIndustry()));
        rowHolder.subIndustry.setText(Html.fromHtml("<b>Sub Industry: </b>" + listData.get(position).getSubIndustry()));
        rowHolder.country.setText(Html.fromHtml("<b>Country: </b>" + listData.get(position).getCountry()));
        rowHolder.town.setText(Html.fromHtml("<b>Town / City: </b>" + listData.get(position).getTown().getName()));
        rowHolder.building.setText(listData.get(position).getNeighbourhood().getName());
        return convertView;
    }



    public void setViewPager(ViewPager viewPager) {
        this.viewPager=viewPager;
    }

    class ViewHolder{
        TextView businessName;
        TextView businessIndustry;
        TextView businessDescription;
        TextView businessAddress;
        Button btnDetails;
        Button btnVisit;
        Button btnQueue;
        TextView sector;
        TextView industryGroup;
        TextView industry;
        TextView subIndustry;
        TextView country;
        TextView town;
        TextView building;
        TextView placeDistance;
    }

    class ServiceViewHolder{
        TextView serviceName;
        TextView serviceDescription;
        TextView departmentName;
        TextView queueSize;
        TextView queueSpeed;
        Button btnQueueHere;
        Button btnVisitHere;

    }


    public void ShowPlaces(View View){

        ListView business_list_view=view.findViewById(R.id.business_list_view) ;
        View QueueActivity= view.findViewById(R.id.activity_queue);
        View VisitActivity= view.findViewById(R.id.activity_visit);
        business_list_view.setVisibility(View.GONE);
        QueueActivity.setVisibility(View.VISIBLE);
        VisitActivity.setVisibility(View.GONE);

    }


    //hide pointers
    //--------------------------------------------------------------------------------------------
    public void showPointer(View convertView, int id, boolean show){
        View btnDetailsPtr = convertView.findViewById(R.id.button_business_details_ptr);
        View btnVisitPtr = convertView.findViewById(R.id.button_visit_ptr);
        View btnQueuePtr = convertView.findViewById(R.id.button_queue_ptr);
        btnDetailsPtr.setVisibility(View.INVISIBLE);
        btnVisitPtr.setVisibility(View.INVISIBLE);
        btnQueuePtr.setVisibility(View.INVISIBLE);
        convertView.findViewById(id).setVisibility(show?View.VISIBLE:View.INVISIBLE);

    }
    }



