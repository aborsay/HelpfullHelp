package net.blueridge.apps.resource_full;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class IndividualContactsOld extends BaseAdapter {

    private Context context;
    //	private final String[] mobileValues;
    private ArrayList<HashMap<String,String>> mSectionData;

    static class ViewHolder {
        AppCompatTextView contactOrganization;
        TextView contactName;
        TextView contactPhone;
        TextView contactSecond;
        TextView contact1Email;
        TextView contact1Address;
    }




    public IndividualContactsOld(Context context, ArrayList<HashMap<String,String>> mSectionData) {
        this.context = context;
        //this.mobileValues = mobileValues;
        this.mSectionData=mSectionData;

    }

    public View getView(int mPosition, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.d(Constants.LOGCAT, "position: " + mPosition);
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.individual_contacts,parent, false);
            holder = new ViewHolder();
            holder.contactName =  convertView.findViewById(R.id.contactName);
            holder.contact1Address =  convertView.findViewById(R.id.contactAddress);
            holder.contact1Email =  convertView.findViewById(R.id.contactEmail);
            holder.contactOrganization =  convertView.findViewById(R.id.contactOrganization);
            holder.contactPhone =  convertView.findViewById(R.id.contactPhone);
            holder.contactSecond =  convertView.findViewById(R.id.contactSecond);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.contactName.setText(mSectionData.get(mPosition).get("contact_name"));
        String organization = mSectionData.get(mPosition).get("contact_organization").isEmpty()
                ? "" : mSectionData.get(mPosition).get("contact_organization");
        holder.contactOrganization.setText(organization);
        String address = mSectionData.get(mPosition).get("contact_organization").isEmpty()
                ? "" : mSectionData.get(mPosition).get("contact_organization");
        holder.contact1Address.setText(address);
        String phone = mSectionData.get(mPosition).get("contact_organization").isEmpty()
                ? "" : mSectionData.get(mPosition).get("contact_organization");
        holder.contactPhone.setText(phone);
        String email = mSectionData.get(mPosition).get("contact_organization").isEmpty()
                ? "" : mSectionData.get(mPosition).get("contact_organization");
        holder.contact1Email.setText(email);

       // holder.contactName.setText("here");
        return convertView;
    }


    @Override
    public int getCount() {
        return mSectionData.size();
    }

    @Override
    public Object getItem(int mPosition) {
        return null;
    }

    @Override
    public long getItemId(int mPosition) {
        return 0;
    }


}
