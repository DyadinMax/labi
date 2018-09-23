package statkovit.com.mpdis.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import statkovit.com.mpdis.R;

public class SeventhActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_READ_CONTACTS = 1;
    private static final int REQUEST_WRITE_CONTACTS = 2;
    private static final int REQUEST_CAMERA = 3;

    private Button buttonPrev;
    private Button buttonNext;
    private Button buttonAdd;
    private TextView wifiEnabled;
    private EditText name;
    private EditText number;
    private TextView batteryValue;
    private Button cameraButton;
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryValue.setText(String.format("%s%%", String.valueOf(level)));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seventh);
        setTitle(R.string.page7_title);
        initButtons();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            getContacts();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS},
                    REQUEST_READ_CONTACTS);
        }
        checkWifiConnection();
        registerReceiver(batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private void initButtons() {
        buttonPrev = findViewById(R.id.prevActivity);
        buttonNext = findViewById(R.id.nextActivity);
        buttonAdd = findViewById(R.id.addContact);
        name = findViewById(R.id.editName);
        number = findViewById(R.id.editNumber);
        wifiEnabled = findViewById(R.id.wifiValue);
        batteryValue = findViewById(R.id.batteryValue);
        cameraButton = findViewById(R.id.camera);
        cameraButton.setOnClickListener(this);
        buttonPrev.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
    }

    private void checkWifiConnection() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String name = getResources().getString(R.string.no);
        if (wifiMgr != null) {
            if (wifiMgr.isWifiEnabled()) {
                name = getResources().getString(R.string.yes);
            }
        }
        wifiEnabled.setText(name);
    }

    private void onCameraAction() {
        if (ActivityCompat.checkSelfPermission(SeventhActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(SeventhActivity.this, new String[]{android.Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
    }

    private void reload() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void addContact() {
        if (ActivityCompat.checkSelfPermission(SeventhActivity.this, android.Manifest.permission.WRITE_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            writeContact(name.getText().toString(), number.getText().toString());
        } else {
            ActivityCompat.requestPermissions(SeventhActivity.this, new String[]{android.Manifest.permission.WRITE_CONTACTS},
                    REQUEST_WRITE_CONTACTS);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prevActivity: {
                Intent page = new Intent(SeventhActivity.this, FifthActivity.class);
                startActivity(page);
                break;
            }
            case R.id.nextActivity: {
                Intent page = new Intent(SeventhActivity.this, EighthActivity.class);
                startActivity(page);
                break;
            }
            case R.id.camera: {
                onCameraAction();
                break;
            }
            case R.id.addContact: {
                addContact();
                break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryInfoReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                }
                break;
            }
            case REQUEST_WRITE_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    writeContact(name.getText().toString(), number.getText().toString());
                }
                break;
            }
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivity(intent);
                }
                break;
            }
        }
    }

    private void writeContact(String displayName, String number) {
        Toast toast;
        String text = null;
        if (displayName.isEmpty()) {
            text = getResources().getString(R.string.validate_name);
        } else if (number.isEmpty()) {
            text = getResources().getString(R.string.validate_number);
        }
        if (text != null) {
            toast = Toast.makeText(getApplicationContext(),
                    text, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<>();
            //insert raw contact using RawContacts.CONTENT_URI
            contentProviderOperations
                    .add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                            .build());
            //insert contact display name using Data.CONTENT_URI
            contentProviderOperations
                    .add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.Contacts.DISPLAY_NAME, displayName)
                            .build());
            //insert mobile number using Data.CONTENT_URI
            contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
            try {
                getApplicationContext().getContentResolver().
                        applyBatch(ContactsContract.AUTHORITY, contentProviderOperations);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            reload();
        }
    }

    public void getContacts() {
        String phoneNumber;

        String[] columns = {"name", "number"};
        int[] resourceIds = {
                R.id.textview_list_row_name,
                R.id.textview_list_row_number
        };
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> m;

        ContentResolver contentResolver = getContentResolver();
        // Query and loop for every contact in the phone
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                m = new HashMap<>();
                String contact_id = cursor.getString(cursor.getColumnIndex(BaseColumns._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                StringBuilder numbers = new StringBuilder();
                int hasPhoneNumber = Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    // Query and loop for every phone number of the single contact
                    Cursor phoneCursor = contentResolver
                            .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{contact_id}, null);
                    if (phoneCursor != null) {
                        while (phoneCursor.moveToNext()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            numbers.append(phoneNumber).append("\n");
                        }
                        phoneCursor.close();
                    }
                }
                m.put(columns[0], name);
                m.put(columns[1], numbers);
                data.add(m);
            }

            SimpleAdapter sAdapter = new SimpleAdapter(SeventhActivity.this, data, R.layout.listview_contact_row,
                    columns, resourceIds);
            ListView listView = findViewById(R.id.contactList);
            listView.setAdapter(sAdapter);
            cursor.close();
        }


    }

}
