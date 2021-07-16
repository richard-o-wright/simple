package net.wrightnz.data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * This Class represents a Xtra Users MDS Directory entry for the purposes of the
 * MDS reports.
 * 
 * @author T466225
 */
public class UserEntry extends ValueObjectBase implements Serializable{
    /**
     * String matching the contant portion at the begining of all user name dn's
     * in the directory
     */
    public static final String USER_DN_START = "dn: uid=";
    /**
     * String matching the contant portion at the end of all user name dn's
     * in the directory
     */
    public static final String USER_DN_END = ",ou=people,dc=xtra,dc=co,dc=nz,o=internet";
    /**
     * attribute name under which the password value is stored.
     */
    public static final String USER_PASSWORD_ATTRIBUTE = "userPassword:";
    public static final String VRN_ATTRIBUTE = "xtraVirtualRouterName:";
    public static final String XTRA_PRIMARY_EMAIL_ADDRESS = "xtraPrimaryEmailAddress:";
    public static final String UID_ATTRIBUTE = "uid:";
    public static final String MODIFY_TIMESTAMP = "modifyTimestamp:";
    public static final String CREATE_TIMESTAMP = "createTimestamp:";
    public static final String YAHOO_ACCOUNT_STATE = "yahooAccountState:";
    public static final String YAHOO_REG_COMPLETE =  "yahooRegistrationCompleteFlag:";
    public static final String YAHOO_ACCOUNT_TYPE = "yahooAccountType:";
    public static final String MARKETING_CODE = "xtraMarketingCode:";
    public static final String SERVICE_PROFILE = "xtraServiceProfile:";
    public static final String YAHOO_PRIMARY_ACCOUNT_ID = "yahooPrimaryAccountID:";
    public static final String XTRA_OWNER_OF_ACCOUNT = "xtraOwnerOfAccount:";
    public static final String XTRA_USER_OF_ACCOUNT = "xtraUserOfAccount:";
    public static final String IDENTITY_ACTIVE_TO = "identityActiveTo:";
    public static final String XTRA_TIME_ACTIVE_TO = "xtraTimeActiveTo:";
    public static final String COMMON_NAME = "cn:";
    
    /*
     Here are the object classes that are most likely to be of interest to this
     class.
    
     yahooPerson MUST {yahooAccountToken $ yahooAccountType $ yahooAccountState )
     MAY (yahooService $ yahooAccountStateReason $ yahooPrimaryAccountID $
     yahooPrimaryAccountToken $ yahooRegistrationCompleteFlag $ yahooAlias )

     xtraPerson MUST ( xtraCreateUserPermission $ xtraMarketingCode $ xtraPrimaryEmailAddress )
     MAY ( identityActiveFrom $ identityActiveTo $ xtraRadiusProfile $ xtraRole $
     xtraTimeActiveFrom $ xtraTimeActiveTo $ xtraUserPassword $ xtraVirtualRouterName $
     xtraServiceProfile $ xtraAccountToken $ xtraMiddleName $ xtraSecretQuestion $
     xtraSecretAnswer $ xtraDateOfBirth $ xtraGender $ xtraMobileNumber )

     broadbandaccessprofile
     MUST ( cn $ profile ) X-ORIGIN 'user defined' )

     xtraAccessProfile
     MUST ( cn $ profile $ enableProfile ) X-ORIGIN 'user defined' )
     */

    private String dn;
    private String username;
    private String accountOwner;
    private long telecomAccountNumber;
    private String password;
    private Date dateCreated;
    private Date dateModified;
    private String yahooAccountState;
    private String yahooRegistrationCompleteFlag;
    private String yahooAccountType;
    private String yahooPrimaryAccountID;
    private String xtraMarketingCode;
    private String xtraServiceProfile;
    private String xtraPrimaryEmailAddress;
    private Date identityActiveTo;
    private Date xtraTimeActiveTo;
    private String commonName;

    private final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private final SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm z");

    /**
     * Create a new empty UserEntry
     */
    public UserEntry(){
        // Uncomment the below to output time stamps in UTC rather then the default time zone.
        // outputFormat.setCalendar(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        inputFormat.setCalendar(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
    }

    /**
     *
     * @return the user name stored in this UserEntry
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return the password stored in this UserEntry. the value will be either
     * Unix Crypt of MD5 encrypted.
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the yahooAccountState
     */
    public String getYahooAccountState() {
        return yahooAccountState;
    }

    /**
     * @param yahooAccountState the yahooAccountState to set
     */
    public void setYahooAccountState(String yahooAccountState) {
        this.yahooAccountState = yahooAccountState;
    }

    /**
     * @return the yahooRegistrationCompleteFlag
     */
    public String getYahooRegistrationCompleteFlag() {
        return yahooRegistrationCompleteFlag;
    }

    /**
     * @param yahooRegistrationCompleteFlag the yahooRegistrationCompleteFlag to set
     */
    public void setYahooRegistrationCompleteFlag(String yahooRegistrationCompleteFlag) {
        this.yahooRegistrationCompleteFlag = yahooRegistrationCompleteFlag;
    }

    /**
     * @return the yahooAccountType
     */
    public String getYahooAccountType() {
        return yahooAccountType;
    }

    /**
     * @param yahooAccountType the yahooAccountType to set
     */
    public void setYahooAccountType(String yahooAccountType) {
        this.yahooAccountType = yahooAccountType;
    }

    /**
     * @return the xtraMarketingCode
     */
    public String getXtraMarketingCode() {
        return xtraMarketingCode;
    }

    /**
     * @param xtraMarketingCode the xtraMarketingCode to set
     */
    public void setXtraMarketingCode(String xtraMarketingCode) {
        this.xtraMarketingCode = xtraMarketingCode;
    }

    /**
     * @return the xtraServiceProfile
     */
    public String getXtraServiceProfile() {
        return xtraServiceProfile;
    }

    /**
     * @param xtraServiceProfile the xtraServiceProfile to set
     */
    public void setXtraServiceProfile(String xtraServiceProfile) {
        this.xtraServiceProfile = xtraServiceProfile;
    }

    /**
     *
     * @return The DN of this User Entry in the directory.
     */
    public String getDN() {
        return dn;
    }

    /**
     * Set the dn of this entry. The location in which it can be found in the
     * directory.
     * @param dn
     */
    public void setDN(String dn) {
        this.dn = dn;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     *
     * @param createdString
     * @throws java.text.ParseException
     */
    public void setDateCreated(String createdString) throws ParseException {
        if(createdString != null){
            this.dateCreated = createDateFromStringV1(createdString);
        }
    }
    /**
     * @return the dateModified
     */
    public Date getDateModified() {
        return dateModified;
    }

    /**
     * @param dateModified the dateModified to set
     */
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    /**
     * 
     * @param dateString
     * @throws java.text.ParseException
     */
    public void setDateModified(String dateString)throws ParseException {
        if(dateString != null){
            this.dateModified = createDateFromStringV1(dateString);
        }
    }

    /**
     * @return the yahooPrimaryAccountID
     */
    public String getYahooPrimaryAccountID() {
        return yahooPrimaryAccountID;
    }

    /**
     * @param yahooPrimaryAccountID the yahooPrimaryAccountID to set
     */
    public void setYahooPrimaryAccountID(String yahooPrimaryAccountID) {
        this.yahooPrimaryAccountID = yahooPrimaryAccountID;
    }

    /**
     * @return the accountOwner
     */
    public String getAccountOwner() {
        return accountOwner;
    }

    /**
     * @param accountOwner the accountOwner to set
     */
    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    /**
     * @return the telecomAccountNumber
     */
    public long getTelecomAccountNumber() {
        return telecomAccountNumber;
    }

    /**
     * @param tan the telecomAccountNumber to set
     */
    public void setTelecomAccountNumber(String tan) {
        if (tan == null) {
            this.telecomAccountNumber = 0L;
        } else if ("".equals(tan)) {
            this.telecomAccountNumber = 0L;
        } else {
            int indexOfDash = tan.indexOf("-");
            if (indexOfDash > 0) {
                tan = tan.substring(0, indexOfDash);
            }
            this.telecomAccountNumber = Long.parseLong(tan);
        }
    }

    /**
     * @return the xtraPrimaryEmailAddress
     */
    public String getXtraPrimaryEmailAddress() {
        return xtraPrimaryEmailAddress;
    }

    /**
     * @param xtraPrimaryEmailAddress the xtraPrimaryEmailAddress to set
     */
    public void setXtraPrimaryEmailAddress(String xtraPrimaryEmailAddress) {
        this.xtraPrimaryEmailAddress = xtraPrimaryEmailAddress;
    }

    /**
     * @return all the instance variable values of this UserEntry instance
     *         in csv format. For the column headings of each variable (in the
     *         same order as they are returned by this method call getCSVHeadings().
     */
    public String toCSV(){
        StringBuffer sb = new StringBuffer();
        sb.append(this.getUsername());
        sb.append(", ");
        sb.append(this.getAccountOwner());
        sb.append(", ");
        sb.append(this.getTelecomAccountNumber());
        sb.append(", ");
        if("Y".equals(yahooRegistrationCompleteFlag)){
            sb.append("Y, ");
        }
        else{
            sb.append("N, ");
        }
        sb.append(this.getXtraMarketingCode());
        sb.append(", ");
        if("port25filter".equals(xtraServiceProfile)){
            sb.append("Y, ");
        }
        else{
            sb.append("N, ");
        }
        sb.append(this.getYahooAccountState());
        sb.append(", ");
        sb.append(this.getYahooAccountType());
        sb.append(", ");
        sb.append(this.getYahooPrimaryAccountID());
        sb.append(", ");
        if(dateModified != null){
            sb.append(outputFormat.format(dateModified));
        }
        else{
            sb.append("Missing Date Modified");
        }
        sb.append(", ");
        if(dateCreated != null){
            sb.append(outputFormat.format(dateCreated));
        }
        else{
           sb.append("Missing Date Created");
        }
        return sb.toString();
    }


    /**
     * @return the column names (in CSV format) for the values returned by calling
     *         toCSV().
     */
    public static String getCSVHeadings(){
        return "User_name" +
               ", Account_owner" +
               ", Telecom_account_number" +
               ", Yahoo_registration_complete" +
               ", Marketing_code" +
               ", Port_25" +
               ", Yahoo_account_state" +
               ", Yahoo_account_type" +
               ", Primary_account" +
               ", Date_modified" +
               ", Date_created";
    }

 
    /**
     * Try to convert the time stamps stored in the MDS into instances of
     * java.util.Date. Two formats are known.
     *
     * modifyTimestamp:20071112010316Z
     * createTimestamp:990831120618Z
     *
     * @param dateString
     * @return
     */
    private Date createDateFromString(String dateString) throws ParseException{
        if(dateString != null && (dateString.startsWith("2") || dateString.startsWith("19")) && dateString.length() == 15){
            return inputFormat.parse(dateString);
        }
        else if(dateString != null && dateString.length() >= 13 && dateString.length() < 15){
            dateString = dateString.substring(0, dateString.length() - 1);
            long dateInMilliseconds = Long.parseLong(dateString);
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(dateInMilliseconds);
            return calendar.getTime();
        }
        else{
            throw new ParseException("Could not determine date format used in string: " + dateString, 0);
        }
    }


    /**
     * Try to convert the time stamps stored in the MDS into instances of
     * java.util.Date. Two formats are known.
     *
     * modifyTimestamp:20071112010316Z
     * createTimestamp:990831120618Z
     *
     * @param dateString
     * @return
     */
    private Date createDateFromStringV1(String dateString) throws ParseException{
        if(dateString != null && (dateString.startsWith("2") || dateString.startsWith("19")) && dateString.length() == 15){
            return inputFormat.parse(dateString);
        }
        else if(dateString != null && dateString.length() >= 13 && dateString.length() < 15){
            dateString = dateString.substring(0, dateString.length() - 1);
            long dateInMilliseconds = Long.parseLong(dateString);
            Date timestamp = new Date(dateInMilliseconds);
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTime(timestamp);
            return calendar.getTime();
        }
        else{
            throw new ParseException("Could not determine date format used in string: " + dateString, 0);
        }
    }

    /**
     * main method for Unit Testing only
     * 
     * @param args not used
     * @throws java.lang.Exception
     */
    public static void main(String[] args)throws Exception{
        String[] exampleTimestamps = {"990831120618Z", "20071112010316Z",
                                       "20071103101013Z", "990831115215Z", "20030812122121Z", "990831115215Z"};

        for(int i = 0; i < exampleTimestamps.length; i++){
            UserEntry entry = new UserEntry();
            entry.setDateCreated(exampleTimestamps[i]);
            System.out.println(exampleTimestamps[i] + " =? " + entry.getDateCreated() + " | ");

        }
    }

    /**
     * @return the identityActiveTo
     */
    public Date getIdentityActiveTo() {
        return identityActiveTo;
    }

    /**
     * @param identityActiveTo the identityActiveTo to set
     */
    public void setIdentityActiveTo(String str) {
        this.identityActiveTo = convertXtraTimeStamp(str);
    }

    /**
     * @return the xtraTimeActiveTo
     */
    public Date getXtraTimeActiveTo() {
        return xtraTimeActiveTo;
    }

    /**
     * @param xtraTimeActiveTo the xtraTimeActiveTo to set
     */
    public void setXtraTimeActiveTo(String str) {
        this.xtraTimeActiveTo = convertXtraTimeStamp(str);
    }

    private Date convertXtraTimeStamp(String str){
        if(str == null){
            return null;
        }
        if(str.length() < 4){
            return null;
        }
        long time = Long.parseLong(str);
        Date date = new Date(time * 1000L);
        // System.out.println(str + " = " + date);
        return date;
    }

    /**
     * @return the CommonName
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     * @param commonName the Common Name to set
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

}
