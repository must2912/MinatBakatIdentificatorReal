package a123.com.minatbakatidentificator;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import a123.com.minatbakatidentificator.DBHelper.dbhelper;

import static android.R.attr.mode;
/*import dev.edmt.flagsquizapp.Model.Ranking;*/

public class Done extends AppCompatActivity {

    private static final String TAG = "PdfCreatorActivity";
    private EditText mContentEditText;
    private File pdfFile;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    Button btnTryAgain,btnCreatePDF;
    TextView txtResultScoreA,txtResultScoreB,txtResultScoreC,Result;
    ProgressBar progressBarResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        dbhelper db = new dbhelper(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        txtResultScoreA = (TextView) findViewById(R.id.txtTotalScoreA);
        txtResultScoreB = (TextView) findViewById(R.id.txtTotalScoreB);
        txtResultScoreC = (TextView) findViewById(R.id.txtTotalScoreC);
        /*txtResultQuestion = (TextView) findViewById(R.id.txtTotalQuestion);*/
        Result = (TextView) findViewById(R.id.Result);
        progressBarResult = (ProgressBar) findViewById(R.id.doneProgressBar);
        btnCreatePDF = (Button) findViewById(R.id.PrintToPDF);
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recent = Result.getText().toString();
                mEditor.putString(getString(R.string.recent), recent);
                mEditor.commit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnCreatePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (mContentEditText.getText().toString().isEmpty()){
                    mContentEditText.setError("Body is empty");
                    mContentEditText.requestFocus();
                    return;
                }*/

                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

            }
        });


        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            int scorea = extra.getInt("A");
            int scoreb = extra.getInt("B");
            int scorec = extra.getInt("C");
            /*int totalQuestion = extra.getInt("TOTAL");
            int correctAnswer = extra.getInt("CORRECT");*/

            //Update 2.0
            /*int playCount = 0;
            if(totalQuestion == 30) // EASY MODE
            {
                playCount = db.getPlayCount(0);
                playCount++;
                db.updatePlayCount(0,playCount); // Set PlayCount ++
            }
            else if(totalQuestion == 50) // MEDIUM MODE
            {
                playCount = db.getPlayCount(1);
                playCount++;
                db.updatePlayCount(1,playCount); // Set PlayCount ++
            }
            else if(totalQuestion == 100) // HARD MODE
            {
                playCount = db.getPlayCount(2);
                playCount++;
                db.updatePlayCount(2,playCount); // Set PlayCount ++
            }
            else if(totalQuestion == 200) // HARDEST MODE
            {
                playCount = db.getPlayCount(3);
                playCount++;
                db.updatePlayCount(3,playCount); // Set PlayCount ++
            }
*/
            /*double subtract = ((5.0/(float)score)*100)*(playCount-1); //-1 because we playCount++ before we calculate result
            double finalScore = score - subtract;*/

            txtResultScoreA.setText(String.format("SCORE A : %d ", scorea));
            txtResultScoreB.setText(String.format("SCORE B : %d ", scoreb));
            txtResultScoreC.setText(String.format("SCORE C : %d ", scorec));
            /*txtResultQuestion.setText(String.format("PASSED : %d/%d", correctAnswer, totalQuestion));*/

            if (scorea > scoreb && scorea > scorec) {
                Result.setText("Anda Memiliki Gaya Belajar Auditori/ Senang Mendengarkan");
            }
            else if (scoreb >scorea && scoreb > scorec) {
                Result.setText("Anda Memiliki Gaya Belajar Visual/ Senang Melihat");
            }
            else if (scorec > scoreb && scorec > scorea) {
                Result.setText("Anda Memiliki Gaya Belajar Kinestesia/ Senang Melakukan");
            }

            /*progressBarResult.setMax(totalQuestion);
            progressBarResult.setProgress(correctAnswer);*/

            /*//save score
            db.insertScore(finalScore);*/
        }
    }



    private void createPdfWrapper() throws FileNotFoundException,DocumentException{

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }


                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            createPdf();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(),"MantapJiwa.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);
        document.open();
        document.add(new Paragraph(txtResultScoreA.getText().toString()));
        document.add(new Paragraph(txtResultScoreB.getText().toString()));
        document.add(new Paragraph(txtResultScoreC.getText().toString()));
        document.add(new Paragraph(Result.getText().toString()));

        document.close();
        previewPdf();

    }

    private void previewPdf() {

        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");

            startActivity(intent);
        }else{
            Toast.makeText(this,"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
        }
    }
}