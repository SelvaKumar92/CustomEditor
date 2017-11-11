package app.customedt.com.customeditor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import app.customedt.com.customeditor.Activity.CERenderActivity;
import app.customedt.com.customeditor.Helper.CEHelper;
import app.customedt.com.customeditor.Pojo.MainInfo;
import app.customedt.com.customeditor.Session.CESession;
import app.customedt.com.customeditor.Webservice.NAAPIClient;
import app.customedt.com.customeditor.Webservice.NAAPIInterface;
import jp.wasabeef.richeditor.RichEditor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RichEditor mEditor;
    private TextView mPreview;
    private boolean isChanged;
    private int PICK_IMAGE_REQUEST = 12;
    private NAAPIInterface myWebservice;
    private CESession myCESession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditor = (RichEditor) findViewById(R.id.editor);
        myCESession = new CESession(this);
        myWebservice = NAAPIClient.getClient().create(NAAPIInterface.class);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorFontColor(Color.RED);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("Insert text here...");
        mPreview = (TextView) findViewById(R.id.preview);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                mPreview.setText(text);
            }
        });
        mEditor.setHtml("");
        clickListener();

    }

    private void clickListener() {

        findViewById(R.id.action_undo).setOnClickListener(this);
        findViewById(R.id.action_redo).setOnClickListener(this);
        findViewById(R.id.action_bold).setOnClickListener(this);
        findViewById(R.id.action_italic).setOnClickListener(this);
        findViewById(R.id.action_subscript).setOnClickListener(this);
        findViewById(R.id.action_superscript).setOnClickListener(this);
        findViewById(R.id.action_strikethrough).setOnClickListener(this);
        findViewById(R.id.action_underline).setOnClickListener(this);
        findViewById(R.id.action_heading1).setOnClickListener(this);
        findViewById(R.id.action_heading2).setOnClickListener(this);
        findViewById(R.id.action_heading3).setOnClickListener(this);
        findViewById(R.id.action_heading4).setOnClickListener(this);
        findViewById(R.id.action_heading5).setOnClickListener(this);
        findViewById(R.id.action_heading6).setOnClickListener(this);
        findViewById(R.id.action_txt_color).setOnClickListener(this);
        findViewById(R.id.action_bg_color).setOnClickListener(this);
        findViewById(R.id.action_indent).setOnClickListener(this);
        findViewById(R.id.action_outdent).setOnClickListener(this);
        findViewById(R.id.action_align_left).setOnClickListener(this);
        findViewById(R.id.action_align_center).setOnClickListener(this);
        findViewById(R.id.action_align_right).setOnClickListener(this);
        findViewById(R.id.action_blockquote).setOnClickListener(this);
        findViewById(R.id.action_insert_bullets).setOnClickListener(this);
        findViewById(R.id.action_insert_numbers).setOnClickListener(this);
        findViewById(R.id.action_insert_image).setOnClickListener(this);
        findViewById(R.id.action_insert_checkbox).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_preview).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.action_undo:
                mEditor.undo();
                break;


            case R.id.action_redo:
                mEditor.redo();
                break;

            case R.id.action_bold:
                mEditor.setBold();
                break;
            case R.id.action_italic:
                mEditor.setItalic();
                break;

            case R.id.action_subscript:
                mEditor.setSubscript();
                break;

            case R.id.action_superscript:
                mEditor.setSuperscript();
                break;
            case R.id.action_strikethrough:
                mEditor.setStrikeThrough();
                break;

            case R.id.action_underline:
                mEditor.setUnderline();
                break;
            case R.id.action_heading1:
                mEditor.setHeading(1);
                break;

            case R.id.action_heading2:
                mEditor.setHeading(2);
                break;
            case R.id.action_heading3:
                mEditor.setHeading(3);
                break;

            case R.id.action_heading4:
                mEditor.setHeading(4);
                break;

            case R.id.action_heading5:
                mEditor.setHeading(5);
                break;

            case R.id.action_heading6:
                mEditor.setHeading(6);
                break;

            case R.id.action_txt_color:
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
                break;
            case R.id.action_bg_color:
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
                break;

            case R.id.action_indent:
                mEditor.setIndent();
                break;

            case R.id.action_outdent:
                mEditor.setOutdent();
                break;
            case R.id.action_align_left:
                mEditor.setAlignLeft();
                break;

            case R.id.action_align_center:
                mEditor.setAlignCenter();
                break;
            case R.id.action_align_right:
                mEditor.setAlignRight();
                break;
            case R.id.action_blockquote:
                mEditor.setBlockquote();
                break;
            case R.id.action_insert_bullets:
                mEditor.setBullets();
                break;
            case R.id.action_insert_numbers:
                mEditor.setNumbers();
                break;
            case R.id.action_insert_image:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
            case R.id.action_insert_checkbox:
                mEditor.insertTodo();
                break;

            case R.id.btn_save:
                myCESession.putTextContent(mEditor.getHtml());
                if (checkInternet()) {
                    myCESession.putUpdateAvailable(false);
                    updateDataToServer();
                } else {
                    myCESession.putUpdateAvailable(true);
                    Toast.makeText(this, "Please get online to preview", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.btn_preview:
                if (checkInternet()) {
                    String aData = mEditor.getHtml();
                    Intent aIntent1 = new Intent(getApplicationContext(), CERenderActivity.class);
                    aIntent1.putExtra("content", aData);
                    startActivity(aIntent1);
                } else {
                    myCESession.putUpdateAvailable(true);
                    Toast.makeText(this, "Please get online to preview", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

    /**
     * check internet
     *
     * @return
     */
    private boolean checkInternet() {
        return CEHelper.checkInternet(this);
    }

    private void updateDataToServer() {
        final ProgressDialog aDialog = new ProgressDialog(MainActivity.this);
        aDialog.show();
        Call<MainInfo> aCall = myWebservice.updateData(myCESession.getTextContent());
        aCall.enqueue(new Callback<MainInfo>() {
            @Override
            public void onResponse(Call<MainInfo> call, Response<MainInfo> response) {
                Toast.makeText(MainActivity.this, "Data updated sucessfully", Toast.LENGTH_SHORT).show();
                aDialog.hide();
            }

            @Override
            public void onFailure(Call<MainInfo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server Not Reachable, Please try again later", Toast.LENGTH_SHORT).show();
                aDialog.hide();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //Get image
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, false);
                String aData = encodeImage(bitmap);
                mEditor.insertImage("data:image/jpeg;base64," + aData, "test");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.NO_WRAP);

        return encImage;
    }


}

