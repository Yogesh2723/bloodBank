package com.jeenya.yfb.accessContacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.jeenya.yfb.db.DatabaseHandler;

import java.util.ArrayList;


public class ContactsProvider {
    private Context context;

    public ContactsProvider(Context context) {
        this.context = context;
    }

    public ArrayList<IndividualContactInfo> getContactInfo(ContentResolver cr) {

        ArrayList<IndividualContactInfo> contactList = new ArrayList<IndividualContactInfo>();


        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        // use the cursor to access the contacts
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            // get display name
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            // get phone number

            // remove all non-digit characters
            String nonParenthesesNumber = phoneNumber.replaceAll("[^0-9]", "");
            System.out.println(nonParenthesesNumber);

            boolean b = checkIfExistInDataBase(nonParenthesesNumber);
            if (!b) {
                IndividualContactInfo ic = new IndividualContactInfo();
                ic.setContactName(name);
                ic.setPhoneNumber(nonParenthesesNumber);
                contactList.add(ic);

                Log.d("name", name);
                Log.d("number", nonParenthesesNumber);
            }

        }
        phones.close();

        return contactList;
    }

    private boolean checkIfExistInDataBase(String ph) {
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        boolean yesOrNo = databaseHandler.CheckIsDataAlreadyInDBorNot(ph);
        Log.d("booleen", String.valueOf(yesOrNo));
        return yesOrNo;
    }
}
