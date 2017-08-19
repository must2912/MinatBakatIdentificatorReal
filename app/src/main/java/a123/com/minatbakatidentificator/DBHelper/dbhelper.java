package a123.com.minatbakatidentificator.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/*import dev.edmt.flagsquizapp.Common.Common;*/
import a123.com.minatbakatidentificator.Model.segment1;
import a123.com.minatbakatidentificator.Model.recent;

/**
 * Created by reale on 30/09/2016.
 */

public class dbhelper extends SQLiteOpenHelper {

    private static String DB_NAME = "dblomba.db";
    private static String DB_PATH = "";
    private SQLiteDatabase mDataBase;
    private Context mContext = null;

    public dbhelper(Context context) {
        super(context, DB_NAME, null, 1);

        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        File file = new File(DB_PATH+"dblomba.db");
        if(file.exists())
            openDataBase(); // Add this line to fix db.insert can't insert values
        this.mContext = context;
    }

    public void openDataBase() {
        String myPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void copyDataBase() throws IOException {
        try {
            InputStream myInput = mContext.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0)
                myOutput.write(buffer, 0, length);

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null ? true : false;
    }

    public void createDataBase() throws IOException {
        boolean isDBExists = checkDataBase();
        if (isDBExists) {

        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //CRUD For Table
    public List<segment1> getAllQuestion() {
        List<segment1> listQuestion = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM segmen1 ORDER BY Random()", null);
            if (c == null) return null;
            c.moveToFirst();
            do {
                int questionid = c.getInt(c.getColumnIndex("questionid"));
                String question = c.getString(c.getColumnIndex("question"));
                String AnswerA = c.getString(c.getColumnIndex("AnswerA"));
                String AnswerB = c.getString(c.getColumnIndex("AnswerB"));
                String AnswerC = c.getString(c.getColumnIndex("AnswerC"));

                segment1 pertanyaan = new segment1(questionid,question, AnswerA, AnswerB, AnswerC);
                listQuestion.add(pertanyaan);
            }
            while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return listQuestion;
    }

    //We need improve this function to optimize process from Playing
    public List<segment1> getQuestionMode(String mode) {
        List<segment1> listQuestion = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try {
            c = db.rawQuery(String.format("SELECT * FROM segment1 ORDER BY Random() "), null);
            if (c == null) return null;
            c.moveToFirst();
            do {
                int questionid = c.getInt(c.getColumnIndex("questionid"));
                String question = c.getString(c.getColumnIndex("question"));
                String AnswerA = c.getString(c.getColumnIndex("AnswerA"));
                String AnswerB = c.getString(c.getColumnIndex("AnswerB"));
                String AnswerC = c.getString(c.getColumnIndex("AnswerC"));;

                segment1 pertanyaan = new segment1(questionid,question, AnswerA, AnswerB, AnswerC);
                listQuestion.add(pertanyaan);
            }
            while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return listQuestion;
    }

    //Insert Score to Ranking table
    /*public void insertScore(double score) {
        String query = "INSERT INTO Ranking(Score) VALUES("+score+")";
        mDataBase.execSQL(query);
    }*/

    //Get Score and sort ranking
    /*public List<Ranking> getRanking() {
        List<Ranking> listRanking = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM Ranking Order By Score DESC;", null);
            if (c == null) return null;
            c.moveToNext();
            do {
                int Id = c.getInt(c.getColumnIndex("Id"));
                double Score = c.getDouble(c.getColumnIndex("Score"));

                Ranking ranking = new Ranking(Id, Score);
                listRanking.add(ranking);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return listRanking;

    }*/


    /*//Update version 2.0
    public int getPlayCount(int level)
    {
        int result = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        try{
            c = db.rawQuery("SELECT PlayCount FROM UserPlayCount WHERE Level="+level+";",null);
            if(c == null) return 0;
            c.moveToNext();
            do{
                result  = c.getInt(c.getColumnIndex("PlayCount"));
            }while(c.moveToNext());
            c.close();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

    public void updatePlayCount(int level,int playCount)
    {
        String query = String.format("UPDATE UserPlayCount Set PlayCount = %d WHERE Level = %d",playCount,level);
        mDataBase.execSQL(query);
    }*/
}
