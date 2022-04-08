package com.example.barbercornerproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.barbercornerproj.Database.OrderHelper;
import com.example.barbercornerproj.model.BookingModel;
import com.example.barbercornerproj.model.CustomerModel;
import com.example.barbercornerproj.model.DataModel;
import com.example.barbercornerproj.model.MessageModel;
import com.example.barbercornerproj.model.NotifyModel;
import com.example.barbercornerproj.model.StaffModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // User table
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_NAME = "userName";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERID = "USER" + COLUMN_ID;
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_TITLE = "TITLE";

    // Staff table
    public static final String STAFF_TABLE = "STAFF_TABLE";
    public static final String COL_ID = "ID";
    public static final String COL_uID = "staffId";
    public static final String COL_NAME = "name";
    public static final String COL_TITLE = "title";
    public static final String COL_BIO = "biography";
    public static final String COL_SHIFT = "SHIFT";
    public static final String COL_LOCATION = "LOCATION";

    //Customer Table
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COL_USERID = "ID";
    public static final String COL_ADDRESS = "address";
    public static final String COL_AGE = "age";

    // messages
    public static final String MESSAGES_TABLE = "messages";
    public static final String COL_MESSAGE_ID = "messageId";
    public static final String COL_SENDER_ID = "senderId";
    public static final String COL_RECEIVE_ID = "receiveId";
    public static final String COL_MESSAGE = "message";

    //  Notify
    public static final String NOTIFY_TABLE = "notify";
    public static final String COL_NOTIFY_ID = "notifyId";
    public static final String COL_NOTIFY_TITLE = "notifyTitle";
    public static final String COL_NOTIFY_DESCRIPTION = "notifyDescription";
    public static final String COL_RECEIVE_USER_ID = "receiveUserId";
    public static final String COL_RECEIVE_USER_NAME = "receiveUserName";
    public static final String COL_NOTIFY_STATUS = "notifyStatus";
    public static final String STATUS_NOT_SEND = "Not send";
    public static final String STATUS_SENT = "Sent";

    public static final int ADMIN_USER_ID = 1;

    //Booking Table
    static class BOOKING_TABLE {
        public static final String NAME = "BOOKING_TABLE";
        public static final String COLUMN_ID = "bookingId";
        public static final String COLUMN_CUSTOMER_ID = "customerId";
        public static final String COLUMN_BARBER_ID = "barberId";
        public static final String COLUMN_DATETIME = "bookingDateTime";
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, "barbershopData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create user table user
        String userTable = "CREATE TABLE " + USER_TABLE + "(" + COLUMN_USER_NAME + " VARCHAR(500), " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_TITLE + " TEXT,"+ COLUMN_USERID + " TEXT, " + COLUMN_PASSWORD + " TEXT )";
        db.execSQL(userTable);
        String insertAdmin = "INSERT INTO " + USER_TABLE + "(" + COLUMN_ID + ", " + COLUMN_USER_NAME + ", " + COLUMN_PASSWORD + ", " + COLUMN_TITLE + ") " +
                            "VALUES(" + ADMIN_USER_ID + ", 'admin', 'admin','admin')";
        db.execSQL(insertAdmin);

        //Create staff table
        String staffTable = "CREATE TABLE " + STAFF_TABLE + "(" + COL_ID + "" +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_uID + " TEXT, " + COL_TITLE + " TEXT, " + COL_NAME + " TEXT, "  + " TEXT, " + COL_BIO + " TEXT, " + COL_SHIFT + " TEXT," + COL_LOCATION + " TEXT)" ;
        db.execSQL(staffTable);

        //create customer table
        String customerTable = "CREATE TABLE " + CUSTOMER_TABLE + "(" + COL_USERID +
                " TEXT, " + COL_ADDRESS + " TEXT, " + COL_AGE + " TEXT )";
        db.execSQL(customerTable);

        String messageTable =
                "CREATE TABLE " + MESSAGES_TABLE + "(" +
                        COL_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_SENDER_ID + " INTEGER, " +
                        COL_RECEIVE_ID + " TEXT, " +
                        COL_MESSAGE + " TEXT" +
                        ")";
        db.execSQL(messageTable);

        //Create booking table
        String bookingTable =
                "CREATE TABLE " + BOOKING_TABLE.NAME + "(" +
                        BOOKING_TABLE.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        BOOKING_TABLE.COLUMN_CUSTOMER_ID + " INTEGER, " +
                        BOOKING_TABLE.COLUMN_BARBER_ID + " INTEGER, " +
                        BOOKING_TABLE.COLUMN_DATETIME + " DATETIME " +
                        ")";
        Log.i("DATABASE", bookingTable);
        db.execSQL(bookingTable);

        //  Create notify table
        String notifyTable =
                "CREATE TABLE " + NOTIFY_TABLE + "(" +
                        COL_NOTIFY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NOTIFY_TITLE + " TEXT," +
                        COL_NOTIFY_DESCRIPTION + " TEXT," +
                        COL_RECEIVE_USER_ID + " INTEGER," +
                        COL_NOTIFY_STATUS + " TEXT)";
        db.execSQL(notifyTable);

        String SQL_TABLE=" CREATE TABLE " + OrderHelper.CART_TABLE + " ("
                + OrderHelper.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OrderHelper.COLUMN_NAME +" TEXT NOT NULL,"
                + OrderHelper.COLUMN_QUANTITY +" TEXT NOT NULL,"
                + OrderHelper.COLUMN_PRICE +" TEXT NOT NULL);";
        db.execSQL(SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP table if exists " + USER_TABLE);
        db.execSQL("DROP TABLE if exists " + CUSTOMER_TABLE);
        db.execSQL("DROP TABLE if exists " + STAFF_TABLE);

        onCreate(db);
    }

    public boolean addProduct(String name, String quantity, String price) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OrderHelper.COLUMN_NAME,name);
        values.put(OrderHelper.COLUMN_PRICE,price);
        values.put(OrderHelper.COLUMN_QUANTITY,quantity);

        long insert = database.insert(OrderHelper.CART_TABLE, null, values);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addUser(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME, dataModel.getUserName());
        cv.put(COLUMN_NAME, dataModel.getName());
        cv.put(COLUMN_TITLE, dataModel.getTitle());
        cv.put(COLUMN_USERID, dataModel.getUserId());
        cv.put(COLUMN_PASSWORD, dataModel.getPassword());

        long insert = db.insert(USER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        return true;
    }

    public boolean addCustomerInfo(CustomerModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, model.getUserId());
        cv.put(COL_ADDRESS, model.getAddress());
        cv.put(COL_AGE, model.getAge());


        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addStaff(StaffModel staffModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_uID, staffModel.getUserID());
        values.put(COL_TITLE, staffModel.getTitle());
        values.put(COL_LOCATION, staffModel.getLocation());
        values.put(COL_SHIFT, staffModel.getShift());
        values.put(COL_BIO, staffModel.getBio());

        long r = sqLiteDatabase.insert(STAFF_TABLE, null, values);
        if (r > 0)
            return true;
        else
            return false;

    }

    public boolean addMessage(MessageModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_SENDER_ID, dataModel.getSenderId());
        cv.put(COL_RECEIVE_ID, dataModel.getReceiveId());
        cv.put(COL_MESSAGE, dataModel.getMessage());

        long insert = db.insert(MESSAGES_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<StaffModel> allStaffs() {
        ArrayList<StaffModel> doctorModels = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + STAFF_TABLE;
        Cursor c = sqLiteDatabase.rawQuery(query, null);

        while (c.moveToNext()) {
            String userid = c.getString(1);
            String name = c.getString(3);
            String title = c.getString(2);
            String location = c.getString(4);
            String shift = c.getString(5);
            String bio = c.getString(6);

            StaffModel model = new StaffModel(userid, name, title, location, shift, bio);
            doctorModels.add(model);
        }
        return doctorModels;
    }

    public ArrayList<DataModel> retrieveAllUsers() {
        ArrayList<DataModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            // loop through the cursor and put them into the return list
            do {
                String userName = cursor.getString(0);
                int id = cursor.getInt(1);
                String name = cursor.getString(2);
                String title = cursor.getString(3);
                String userId = cursor.getString(4);
                String password = cursor.getString(5);

                DataModel newUser = new DataModel(id, name, title, userId, userName, password);
                returnList.add(newUser);
            }
            while (cursor.moveToNext());
        } else {
            // error -> don't add anything to the list
        }
        // close the cursor and db after using them
        cursor.close();
        db.close();
        return returnList;
    }

    public ArrayList<DataModel> retrieveAllBarber() {
        ArrayList<DataModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_TITLE + " = 'barber'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            // loop through the cursor and put them into the return list
            do {
                String userName = cursor.getString(0);
                int id = cursor.getInt(1);
                String name = cursor.getString(2);
                String title = cursor.getString(3);
                String userId = cursor.getString(4);
                String password = cursor.getString(5);

                DataModel newUser = new DataModel(id, name, title, userId, userName, password);
                returnList.add(newUser);
            }
            while (cursor.moveToNext());
        } else {
            // error -> don't add anything to the list
        }
        // close the cursor and db after using them
        cursor.close();
        db.close();
        return returnList;
    }

    public Cursor getUserByUserName(String userName) {
        userName = "\"" + userName + "\"";
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_USER_NAME + " = " + userName;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        return cursor;
    }

    public DataModel getUserById(int userId) {
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_ID + " = " + userId;
        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        if(!cursor.moveToNext()) {
            return null;
        }

        String userName = cursor.getString(0);
        int ID = cursor.getInt(1);
        String Name = cursor.getString(2);
        String Title = cursor.getString(3);
        String UserID = cursor.getString(4);
        String Password = cursor.getString(5);

        DataModel newUser = new DataModel(ID, Name, Title, UserID, userName, Password);

        cursor.close();
        return newUser;
    }

    public ArrayList<MessageModel> cursorToMessageModel(Cursor cursor) {
        ArrayList<MessageModel> messageList = new ArrayList<>();

        while (cursor.moveToNext()) {
            //  Get column index
            int columnMessageIdIndex = cursor.getColumnIndex(COL_MESSAGE_ID);
            int columnSenderIdIndex = cursor.getColumnIndex(COL_SENDER_ID);
            int columnReceiveIdIndex = cursor.getColumnIndex(COL_RECEIVE_ID);
            int columnMessageIndex = cursor.getColumnIndex(COL_MESSAGE);

            MessageModel messageModel = new MessageModel(
                    cursor.getInt(columnMessageIdIndex),
                    cursor.getInt(columnSenderIdIndex),
                    cursor.getInt(columnReceiveIdIndex),
                    cursor.getString(columnMessageIndex));

            messageList.add(messageModel);
        }
        return messageList;
    }

    public ArrayList<MessageModel> retrieveAllMessages() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + MESSAGES_TABLE;
        Cursor c = sqLiteDatabase.rawQuery(query, null);

        return cursorToMessageModel(c);
    }

    public ArrayList<MessageModel> retrieveAllMessagesByUserId(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + MESSAGES_TABLE +
                " WHERE " + COL_SENDER_ID + " = " + id +
                " OR " + COL_RECEIVE_ID + " = " + id;
        Cursor c = sqLiteDatabase.rawQuery(query, null);

        return cursorToMessageModel(c);
    }

    public ArrayList<MessageModel> retrieveAllSentMessageByUserId(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + MESSAGES_TABLE +
                        " WHERE " + COL_SENDER_ID + " = " + id +
                        " ORDER BY " + COL_MESSAGE_ID + " DESC";
        Cursor c = sqLiteDatabase.rawQuery(query, null);

        return cursorToMessageModel(c);
    }

    public ArrayList<MessageModel> retrieveAllReceivedMessageByUserId(int id) {
        ArrayList<MessageModel> receivedMessageList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query =
                "SELECT * FROM " + MESSAGES_TABLE +
                        " WHERE " + COL_RECEIVE_ID + " = " + id +
                        " ORDER BY " + COL_MESSAGE_ID + " DESC";
        Cursor c = sqLiteDatabase.rawQuery(query, null);

        return cursorToMessageModel(c);
    }

    /*BOOKING TABLE*/
    public boolean addBooking(int userId, int barberId, int day, int month, int year, int hour, int minute) {
        String sDay = String.format("%02d", day);
        String sMonth = String.format("%02d", month);
        String sYear = String.format("%04d", year);
        String sHour = String.format("%02d", hour);
        String sMinute = String.format("%02d", minute);
        String dateTime = sYear + "-" + sMonth + "-" + sDay + " " + sHour + ":" + sMinute + ":00";
        dateTime = "'" + dateTime + "'";
        String query =
                "INSERT INTO " + BOOKING_TABLE.NAME + "(" +
                        BOOKING_TABLE.COLUMN_CUSTOMER_ID + ", " +
                        BOOKING_TABLE.COLUMN_BARBER_ID + ", " +
                        BOOKING_TABLE.COLUMN_DATETIME + ")" +
                        " VALUES(" + userId + ", " + barberId + ", " + dateTime + ")";
        System.out.println(query);
        getWritableDatabase().execSQL(query);
        return true;
    }

    public void deleteBooking(int bookingId) {
        String query = "DELETE FROM " + BOOKING_TABLE.NAME + " WHERE " + BOOKING_TABLE.COLUMN_ID + " = " + bookingId;
        getWritableDatabase().execSQL(query);
    }

    public ArrayList<BookingModel> retrieveAllBookingByBarberId(int barberId) {
        ArrayList<BookingModel> bookingList = new ArrayList<>();

        String query = "SELECT * FROM " + BOOKING_TABLE.NAME + " WHERE " + BOOKING_TABLE.COLUMN_BARBER_ID + " = " + barberId;
        Cursor c = getReadableDatabase().rawQuery(query, null);

        while (c.moveToNext()) {
            int bookingId = c.getInt(c.getColumnIndex(BOOKING_TABLE.COLUMN_ID) + 0);
            int customerId = c.getInt(c.getColumnIndex(BOOKING_TABLE.COLUMN_CUSTOMER_ID) + 0);
            String dateTime = c.getString(c.getColumnIndex(BOOKING_TABLE.COLUMN_DATETIME) + 0);
            System.out.println(dateTime);

            BookingModel bookingModel = new BookingModel(bookingId, customerId, barberId, dateTime);
            bookingList.add(bookingModel);
        }
        return bookingList;
    }

    public boolean addNotify(NotifyModel notify) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOTIFY_TITLE, notify.getTitle());
        values.put(COL_NOTIFY_DESCRIPTION, notify.getDescription());
        values.put(COL_RECEIVE_USER_ID, notify.getReceiveUserId());
        values.put(COL_NOTIFY_STATUS, STATUS_NOT_SEND);

        long r = sqLiteDatabase.insert(NOTIFY_TABLE, null, values);
        if (r > 0)
            return true;
        else
            return false;
    }

    public ArrayList<NotifyModel> retrieveAllNotifyByUserReceiveId(int userReceiveId) {
        ArrayList<NotifyModel> notifyList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + NOTIFY_TABLE + " WHERE " + COL_RECEIVE_USER_ID + " = " + userReceiveId + " AND " + COL_NOTIFY_STATUS + " = '" + STATUS_NOT_SEND + "'";
        Cursor c = sqLiteDatabase.rawQuery(query, null);

        while (c.moveToNext()) {
            int notifyId = c.getInt(0);
            String notifyTitle = c.getString(1);
            String notifyDescription = c.getString(2);
            int receiveUserId = c.getInt(3);
            NotifyModel notifyModel = new NotifyModel(notifyId, notifyTitle, notifyDescription, receiveUserId);
            notifyList.add(notifyModel);
        }
        return notifyList;
    }

    public void deleteNotify(int notifyId) {
        String query = "DELETE FROM " + NOTIFY_TABLE + " WHERE " + COL_NOTIFY_ID + " = " + notifyId;
        getWritableDatabase().execSQL(query);
    }

    //  For testing
    public void insertTestBarber() {
        String query = "INSERT OR IGNORE INTO " + STAFF_TABLE +
                "(" + COL_ID + ", " + COL_NAME + ")" +
                "VALUES(0, \"Chris Evans\"), (1, 'Chris Hemsworth') , (2, 'Paul Rudd '), (3, 'Benedict Cumberbatch')";
        getWritableDatabase().execSQL(query);
    }
}