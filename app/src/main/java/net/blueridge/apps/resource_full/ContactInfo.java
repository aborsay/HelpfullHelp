package net.blueridge.apps.resource_full;


public class ContactInfo {
    private  String mContactName;
    private  String mContactAddress ;
    private  String mContactSubAddress ;
    private String mCity;
    private String mState;
    private String mPostalCode;
    private String mWebURL;
    private  String mContactEmail ;
    private String mContactID;
    private String mContactCount;

    private  String mContactOrganization ;
    private String mPosition;
    private  String mContactPhone;
    private  String mContactSecond;

    public ContactInfo(){

    }

    public ContactInfo(String mContactName, String mContactAddress, String mContactSubAddress, 
                       String mCity, String mState, String mPostalCode, String mWebURL, 
                       String mContactEmail, String mContactID, String mContactCount, 
                       String mContactOrganization, String mPosition, String mContactPhone,
                       String mContactSecond) {
        this.mContactName = mContactName;
        this.mContactAddress = mContactAddress;
        this.mContactSubAddress = mContactSubAddress;
        this.mCity = mCity;
        this.mState = mState;
        this.mPostalCode = mPostalCode;
        this.mWebURL = mWebURL;
        this.mContactEmail = mContactEmail;
        this.mContactID = mContactID;
        this.mContactCount = mContactCount;
        this.mContactOrganization = mContactOrganization;
        this.mPosition = mPosition;
        this.mContactPhone = mContactPhone;
        this.mContactSecond = mContactSecond;
    }

    public ContactInfo(String contactName, String contactAddress, String contactEmail,
                       String contactOrganization, String contactPhone, String contactSecond) {
        mContactName = contactName;
        mContactAddress = contactAddress;
        mContactEmail =contactEmail;
        mContactOrganization =contactOrganization;
        mContactPhone =contactPhone;
        mContactSecond =contactSecond;
    }




    public String getContactName() {
        return mContactName;
    }

    public String getContactAddress() {
        return mContactAddress;
    }

    public String getContactSubAddress() {
        return mContactSubAddress;
    }

    public String getCity() {
        return mCity;
    }

    public String getState() {
        return mState;
    }

    public String getPostalCode() {
        return mPostalCode;
    }

    public String getWebURL() {
        return mWebURL;
    }

    public String getContactEmail() {
        return mContactEmail;
    }

    public String getContactID() {
        return mContactID;
    }

    public String getContactCount() {
        return mContactCount;
    }

    public String getContactOrganization() {
        return mContactOrganization;
    }

    public String getPosition() {
        return mPosition;
    }

    public String getContactPhone() {
        return mContactPhone;
    }

    public String getContactSecond() {
        return mContactSecond;
    }

    public void setContactSubAddress(String mContactSubAddress) {
        this.mContactSubAddress = mContactSubAddress;
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
    }

    public void setState(String mState) {
        this.mState = mState;
    }

    public void setPostalCode(String mPostalCode) {
        this.mPostalCode = mPostalCode;
    }

    public void setWebURL(String mWebURL) {
        this.mWebURL = mWebURL;
    }

    public void setContactID(String mContactID) {
        this.mContactID = mContactID;
    }

    public void setContactCount(String mContactCount) {
        this.mContactCount = mContactCount;
    }

    public void setPosition(String mPosition) {
        this.mPosition = mPosition;
    }

    public void setContactName(String mContactName) {
        this.mContactName = mContactName;
    }

    public void setContactAddress(String mContactAddress) {
        this.mContactAddress = mContactAddress;
    }

    public void setContactEmail(String mContactEmail) {
        this.mContactEmail = mContactEmail;
    }

    public void setContactOrganization(String mContactOrganization) {
        this.mContactOrganization = mContactOrganization;
    }

    public void setContactPhone(String mContactPhone) {
        this.mContactPhone = mContactPhone;
    }

    public void setContactSecond(String mContactSecond) {
        this.mContactSecond = mContactSecond;
    }


}
