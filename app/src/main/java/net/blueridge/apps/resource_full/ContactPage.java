package net.blueridge.apps.resource_full;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aborsay on 10/1/2018.
 */

public class ContactPage extends Master_Parent implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private String currentSearch="";
    private int currentStart = 0;
    private ArrayList<ContactInfo> mCurrentContacts;
    private ArrayList<HashMap<String,String>> mCurrentTypes;

    private int sectionOfSubs = 1;
    private int helpType2 = 0;
    private int helpType3 = 0;
    private int helpType4 = 0;
    private int helpType5 = 0;

    private boolean userIsInteracting =false;
    private Spinner subCatSpinner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        setContentView(R.layout.activity_contacts);
        setBackground();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        appData.setCurrentSection(2);
        appData.setClassesForLaunch();
        clearContacts();
        placeTopInfo();

        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);



if(appData.getCategoryID()== 0){
    ((TextView) findViewById(R.id.searchData)).setText("'"+appData.getNameSearch()+"'");
    findViewById(R.id.searchData).setVisibility(TextView.VISIBLE);
    findViewById(R.id.helpTypes).setVisibility(TextView.INVISIBLE);

    findViewById(R.id.spinnerRelative).setVisibility(RelativeLayout.INVISIBLE);
    getLoaderManager().initLoader(Constants.INFO_SEARCH, null, this);
}else{
    findViewById(R.id.spinnerRelative).setVisibility(RelativeLayout.VISIBLE);
    findViewById(R.id.searchData).setVisibility(TextView.INVISIBLE);
    findViewById(R.id.helpTypes).setVisibility(TextView.VISIBLE);
    getLoaderManager().initLoader(Constants.INFO_START, null, this);
}



        getLoaderManager().initLoader(Constants.INFO_TYPES, null, this);





    }





    public void resetCats(View view){

        if(((TextView) findViewById(R.id.helpTypes)).getText().toString().isEmpty()){
            moveBackWords();
        }else {
            findViewById(R.id.noRecordsNotice).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.helpTypes)).setText("");
            sectionOfSubs = 1;
            helpType2 = 0;
            helpType3 = 0;
            helpType4 = 0;
            helpType5 = 0;
            getLoaderManager().restartLoader(Constants.INFO_TYPES, null, this);
            getLoaderManager().restartLoader(Constants.INFO_START, null, this);
        }
    }
    public void submitCat(View view){
        Spinner mSpinner = (Spinner) findViewById(R.id.subCatSpinner);
        changeHelp( mSpinner.getSelectedItemPosition());
    }
    private void clearContacts(){

    }

    public void changeHelp(int position){
        String helpTypes= ((TextView) findViewById(R.id.helpTypes)).getText().toString();
        switch(sectionOfSubs){
            default:
            case 1:{
                ((TextView) findViewById(R.id.helpTypes))
                        .setText(helpTypes + mCurrentTypes.get(position).get("helpTypeName") + " -> ");
                helpType2 = position;
                break;
            }
            case 2:{
                ((TextView) findViewById(R.id.helpTypes))
                        .setText(helpTypes + mCurrentTypes.get(position).get("helpTypeName") + " -> ");
                helpType3 = position;
                break;
            }
            case 3:{
                ((TextView) findViewById(R.id.helpTypes))
                        .setText(helpTypes + mCurrentTypes.get(position).get("helpTypeName") + " -> ");
                helpType4 = position;
                break;
            }
            case 4:{
                ((TextView) findViewById(R.id.helpTypes))
                        .setText(helpTypes + mCurrentTypes.get(position).get("helpTypeName") + " -> ");
                helpType5 = position;
                break;
            }
        }

        sectionOfSubs++;
        getLoaderManager().restartLoader(Constants.INFO_TYPES, null, this);
        getLoaderManager().restartLoader(Constants.INFO_START, null, this);
    }
    public void makePhoneCall(View view){
        String phone = ((TextView) view).getText().toString().replace("-","");
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone));

            if (ActivityCompat.checkSelfPermission(ContactPage.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ContactPage.this,
                        new String[]{Manifest.permission.CALL_PHONE},1);
                return;
            }
            if(callIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(callIntent);
            }

    }

    public void moveUp(View view){
        currentStart -=3;
        getLoaderManager().restartLoader(Constants.INFO_MOVE, null, this);
    }

    public void moveDown(View view){
        currentStart +=3;
        getLoaderManager().restartLoader(Constants.INFO_MOVE, null, this);
    }

    public void goBackType(View view){

    }

    public void sendEmail(View view){
        if(view.getTag() != null && view.getTag().toString() != "") {
            int arraySpot = Integer.valueOf(view.getTag().toString());

            if (arraySpot < mCurrentContacts.size()) {
                Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,
                        "To: " + mCurrentContacts.get(arraySpot).getContactName() + ", " +
                                mCurrentContacts.get(arraySpot).getContactOrganization());
                intent.putExtra(Intent.EXTRA_TEXT, "Dear " +
                        mCurrentContacts.get(arraySpot).getContactName() + " at " +
                        mCurrentContacts.get(arraySpot).getContactOrganization() + ", ");
                intent.setData(Uri.parse("mailto:" +
                        mCurrentContacts.get(arraySpot).getContactEmail()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            }
        }
    }
    public void mapAddress(View view){
        if(view.getTag() != null && view.getTag().toString() != "") {
            String address = view.getTag().toString();
            //view.
            // Create a Uri from an intent string. Use the result to create an Intent.
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);

            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                // Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);
            }
        }
    }

    private void placeTopInfo(){
        switch (appData.getCategoryID()){
            case 0:{
                ((TextView) findViewById(R.id.mainCat)).setText("Name or Organization search:");

                ((ImageView) findViewById(R.id.currentCat)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.icons_search));
                break;
            }
            default:
            case 1:{
                ((TextView) findViewById(R.id.mainCat)).setText("Family Services");
                ((ImageView) findViewById(R.id.currentCat)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.family));
                break;
            }
            case 2:{
                ((TextView) findViewById(R.id.mainCat)).setText("Legal");
                ((ImageView) findViewById(R.id.currentCat)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.legal));
                break;
            }
            case 3:{
                ((TextView) findViewById(R.id.mainCat)).setText("Education");
                ((ImageView) findViewById(R.id.currentCat)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.education));
                break;
            }
            case 4:{
                ((TextView) findViewById(R.id.mainCat)).setText("Housing");
                ((ImageView) findViewById(R.id.currentCat)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.house));
                break;
            }
            case 5:{
                ((TextView) findViewById(R.id.mainCat)).setText("Work");
                ((ImageView) findViewById(R.id.currentCat)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.worker));
                break;
            }
            case 6:{
                ((TextView) findViewById(R.id.mainCat)).setText("Food");
                ((ImageView) findViewById(R.id.currentCat)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.dinner));
                break;
            }
            case 7:{
                ((TextView) findViewById(R.id.mainCat)).setText("Health");
                ((ImageView) findViewById(R.id.currentCat)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.health));
                break;
            }
            case 8:{
                ((TextView) findViewById(R.id.mainCat)).setText("Transportation");
                ((ImageView) findViewById(R.id.currentCat)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.transportation));
                break;
            }
            case 9:{
                ((TextView) findViewById(R.id.mainCat)).setText("Finance");
                ((ImageView) findViewById(R.id.currentCat)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.money_bag));
                break;
            }
            case 10:{
                ((TextView) findViewById(R.id.mainCat)).setText("Clothing");
                ((ImageView) findViewById(R.id.currentCat)).setImageDrawable(
                        ContextCompat.getDrawable(this,R.drawable.shirt));
                break;
            }
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////


    public class IndividualContacts extends RecyclerView.Adapter<IndividualContacts.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            public AppCompatTextView contactName;
            public AppCompatTextView contactAddress ;
            public  AppCompatTextView contactEmail ;
            public AppCompatTextView contactOrganization ;
            public AppCompatTextView contactPhone;
            public AppCompatTextView contactSecond;
            public ConstraintLayout bothPhones;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);
                contactName =  itemView.findViewById(R.id.contactName);
                contactAddress =  itemView.findViewById(R.id.contactAddress);
                contactEmail =  itemView.findViewById(R.id.contactEmail);
                contactOrganization =  itemView.findViewById(R.id.contactOrganization);
                contactPhone =  itemView.findViewById(R.id.contactPhone);
                contactSecond =  itemView.findViewById(R.id.contactSecond);
                bothPhones = itemView.findViewById(R.id.contactBothPhones);
            }
        }


        private ArrayList<ContactInfo> mContactList;

        public IndividualContacts(ArrayList<ContactInfo> contacts) {
            mContactList = contacts;
        }


        @Override
        public IndividualContacts.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View contactView = inflater.inflate(R.layout.individual_contacts, parent, false);

            // Return a new holder instance
            //ViewHolder viewHolder =;
            return  new ViewHolder(contactView);
        }


        @Override
        public void onBindViewHolder(IndividualContacts.ViewHolder viewHolder, int mPosition) {
            // Get the data model based on position
            ContactInfo contact = mContactList.get(mPosition);

            viewHolder.contactName.setText(contact.getContactName());
            String organization = contact.getContactOrganization().isEmpty()
                    ? "" : contact.getContactOrganization();
            if(organization.isEmpty()) {
                viewHolder.contactName.setBackgroundResource(R.drawable.bottom_border);
                viewHolder.contactOrganization.setVisibility(AppCompatTextView.GONE);
            }else{
                viewHolder.contactOrganization.setVisibility(AppCompatTextView.VISIBLE);
            }
            viewHolder.contactOrganization.setText(organization);



            String phone = contact.getContactPhone().isEmpty()
                    ? "" : contact.getContactPhone();
            viewHolder.contactPhone.setText(phone);

            String secondPhone = contact.getContactSecond().isEmpty()
                    ? "" : contact.getContactSecond();
            viewHolder.contactSecond.setText(secondPhone);

            if(phone.isEmpty() && secondPhone.isEmpty()) {
                viewHolder.bothPhones.setVisibility(AppCompatTextView.GONE);
            }else {
                viewHolder.bothPhones.setVisibility(AppCompatTextView.VISIBLE);
            }
            String email = contact.getContactEmail().isEmpty()
                    ? "" : contact.getContactEmail();

            if(email.isEmpty()) {
                viewHolder.contactEmail.setVisibility(AppCompatTextView.GONE);
            }else{
                viewHolder.contactEmail.setVisibility(AppCompatTextView.VISIBLE);
                viewHolder.contactEmail.setTag(mPosition);
                viewHolder.contactEmail.setText(email);
            }

            String mAddress = contact.getContactAddress().isEmpty()
                    ? "" : contact.getContactAddress() + "\r\n" +
                    contact.getCity() + ", " +
                    contact.getState()+ " " +
                    contact.getPostalCode();
            if(mAddress.trim().equals(",")){
                mAddress = "";
            }
            if(mAddress.isEmpty()) {
                viewHolder.contactAddress.setVisibility(AppCompatTextView.GONE);
            }else{
                viewHolder.contactAddress.setVisibility(AppCompatTextView.VISIBLE);

                viewHolder.contactAddress.setText(mAddress);
                mAddress = mAddress.replace("\r\n", " ").replace(" ", "+");
                viewHolder.contactAddress.setTag(mAddress);
            }
        }




        @Override
        public int getItemCount() {
            return mContactList.size();
        }


        @Override
        public long getItemId(int mPosition) {
            return 0;
        }


    }

    private void placeContacts(){
        clearContacts();
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.contactList);

        if(mCurrentContacts.size() > 0) {

            findViewById(R.id.noRecordsNotice).setVisibility(View.GONE);
            IndividualContacts contactsAdapter = new IndividualContacts(mCurrentContacts);
            // Attach the adapter to the recyclerview to populate items
            rvContacts.setAdapter(contactsAdapter);
            // Set layout manager to position the items
            rvContacts.setLayoutManager(new LinearLayoutManager(this));


            rvContacts.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            Drawable mDivider = ContextCompat.getDrawable(this, R.drawable.divider_list);
            // Create a DividerItemDecoration whose orientation is Horizontal
            DividerItemDecoration hItemDecoration = new DividerItemDecoration(this,
                    DividerItemDecoration.VERTICAL);
            // Set the drawable on it
            hItemDecoration.setDrawable(mDivider);
        }else{

            findViewById(R.id.noRecordsNotice).setVisibility(View.VISIBLE);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection;
        CursorLoader cursorLoader;
        switch(id) {
            default:
            case Constants.INFO_TYPES:{
                projection = new String[]{
                        String.valueOf(sectionOfSubs),
                        String.valueOf(appData.getCategoryID()),
                        String.valueOf(helpType2),
                        String.valueOf(helpType3),
                        String.valueOf(helpType4),
                        String.valueOf(helpType5)};
                cursorLoader = new CursorLoader(this, DBProvider.CONTENT_URI_HELP_TYPES,
                        projection, null, null, null);
                break;
            }
            case Constants.INFO_START: {

                projection = new String[]{String.valueOf(currentStart)};
                String categoryID = String.valueOf(appData.getCategoryID());
                if (appData.getCategoryID() < 10) {
                    categoryID = "0" + String.valueOf(appData.getCategoryID());
                }
                if(helpType2==0) {

                    currentSearch = categoryID + "|%";
                }else if(helpType2 > 0 && helpType3 ==0){
                    currentSearch = categoryID + "|0" + helpType2 + "|%";
                }else if (helpType2 >0 && helpType3 > 0 && helpType4 == 0){
                    currentSearch = categoryID + "|0" + helpType2 + "|0" + helpType3 + "|%";
                }else if(helpType2 > 0 && helpType3 > 0 && helpType4 > 0 && helpType5 ==0){
                    currentSearch = categoryID + "|0" + helpType2 + "|0" + helpType3 + "|0" +
                            helpType4 + "|%";
                }else {
                    currentSearch = categoryID + "|0" + helpType2 + "|0" + helpType3 + "|0" +
                            helpType4 + "|0" + helpType5;
                }
                cursorLoader = new CursorLoader(this, DBProvider.CONTENT_URI_CONTACTS,
                        projection, currentSearch, null, null);
                break;
            }
            case Constants.INFO_MOVE:{
                projection = new String[]{String.valueOf(currentStart)};

                cursorLoader = new CursorLoader(this, DBProvider.CONTENT_URI_CONTACTS,
                        projection, currentSearch, null, null);
                break;
            }
            case Constants.INFO_SEARCH:{
                projection = new String[]{String.valueOf(currentStart)};
                currentSearch = appData.getNameSearch();
                cursorLoader = new CursorLoader(this, DBProvider.CONTENT_URI_CONTACTS_SEARCH,
                        projection, currentSearch, null, null);
                break;
            }
        }

        return cursorLoader;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor mCursor) {
        switch(loader.getId()) {
            default:
            case Constants.INFO_TYPES:{
                Spinner mSpinner;
                ArrayAdapter<String> mAdapter;
                ArrayList<String> mHelpArray = new ArrayList<>();
                mCurrentTypes = new ArrayList<>();
                if(mCursor.moveToFirst()) {
                    mCurrentTypes.add(new HashMap<String, String>());
                    mCurrentTypes.get(0).put("position", "0");
                    mCurrentTypes.get(0).put("helpTypeName","choose sub-category");
                    mHelpArray.add("̶ choose sub-category ̶");
                    while (!mCursor.isAfterLast()) {

                        mCurrentTypes.add(new HashMap<String, String>());
                        int count = mCurrentTypes.size() - 1;
                        mCurrentTypes.get(count).put("position", String.valueOf(count));
                        mCurrentTypes.get(count).put("helpTypeName",
                                mCursor.getString(mCursor.getColumnIndex("helpTypeName")));
                        mHelpArray.add(mCursor.getString(mCursor.getColumnIndex("helpTypeName")));
                        mCursor.moveToNext();
                    }
                }
                mSpinner = (Spinner) findViewById(R.id.subCatSpinner);


                if(mCurrentTypes.size()!=0) {
                    mSpinner.setVisibility(Spinner.VISIBLE);
                    findViewById(R.id.spinnerRelative).setVisibility(RelativeLayout.VISIBLE);
                    mAdapter = new ArrayAdapter<>(this,
                            R.layout.spinner_text, mHelpArray);
                    mAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
                    mSpinner.setAdapter(mAdapter);

                    mSpinner.setSelection(0,false);
                    mSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                        {
                            String selectedItem = parent.getItemAtPosition(position).toString(); //this is your selected item
                            Log.d(Constants.LOGCAT, selectedItem);
                            if(position>0) {
                                changeHelp(position);
                            }
                            userIsInteracting = false;

                        }
                        public void onNothingSelected(AdapterView<?> parent)
                        {

                        }
                    });
                }else{
                    mSpinner.setVisibility(Spinner.INVISIBLE);
                    findViewById(R.id.spinnerRelative).setVisibility(RelativeLayout.INVISIBLE);
                }



                break;
            }
            case Constants.INFO_START:
            case Constants.INFO_MOVE:
                case Constants.INFO_SEARCH:{
                mCurrentContacts=new ArrayList<>();
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast()) {
                    mCurrentContacts.add(new ContactInfo());
                    int mCount = mCurrentContacts.size()-1;
                    mCurrentContacts.get(mCount).setContactID(mCursor.getString(
                            mCursor.getColumnIndex("contact_id")));
                    mCurrentContacts.get(mCount).setContactCount(mCursor.getString(
                            mCursor.getColumnIndex("count")));
                    mCurrentContacts.get(mCount).setContactName(
                            mCursor.getString(mCursor.getColumnIndex("contact_name_prefix")) + " "
                                    + mCursor.getString(
                                            mCursor.getColumnIndex("contact_name_first")) + " "
                                    + mCursor.getString(
                                            mCursor.getColumnIndex("contact_name_middle")) + " "
                                    + mCursor.getString(
                                            mCursor.getColumnIndex("contact_name_last")) + " "
                                    + mCursor.getString(
                                            mCursor.getColumnIndex("contact_name_suffix")));
                    mCurrentContacts.get(mCount).setContactOrganization(mCursor.getString(
                            mCursor.getColumnIndex("contact_organization")));

                    mCurrentContacts.get(mCount).setPosition(mCursor.getString(
                            mCursor.getColumnIndex("contact_position")));

                    mCurrentContacts.get(mCount).setContactPhone(mCursor.getString(
                            mCursor.getColumnIndex("contact_phone")));
                    mCurrentContacts.get(mCount).setContactSecond(mCursor.getString(
                            mCursor.getColumnIndex("contact_2nd_phone")));
                    mCurrentContacts.get(mCount).setContactEmail(mCursor.getString(
                            mCursor.getColumnIndex("contact_email")));
                    mCurrentContacts.get(mCount).setWebURL(mCursor.getString(
                            mCursor.getColumnIndex("contact_web_url")));
                    mCurrentContacts.get(mCount).setContactAddress(mCursor.getString(
                            mCursor.getColumnIndex("contact_address")));
                    mCurrentContacts.get(mCount).setContactSubAddress(mCursor.getString(
                            mCursor.getColumnIndex("contact_subaddress")));
                    mCurrentContacts.get(mCount).setCity(mCursor.getString(
                            mCursor.getColumnIndex("contact_city")));
                    mCurrentContacts.get(mCount).setState(mCursor.getString(
                            mCursor.getColumnIndex("contact_state")));
                    mCurrentContacts.get(mCount).setPostalCode(mCursor.getString(
                            mCursor.getColumnIndex("contact_postalcode")));

                    mCursor.moveToNext();
                }
                placeContacts();
                break;
            }
        }
    }

}
