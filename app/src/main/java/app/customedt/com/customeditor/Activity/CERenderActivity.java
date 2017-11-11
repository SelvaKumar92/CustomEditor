package app.customedt.com.customeditor.Activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import app.customedt.com.customeditor.Helper.CEHelper;
import app.customedt.com.customeditor.Pojo.MainInfo;
import app.customedt.com.customeditor.R;
import app.customedt.com.customeditor.Webservice.NAAPIClient;
import app.customedt.com.customeditor.Webservice.NAAPIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CERenderActivity extends AppCompatActivity {

    private NAAPIInterface myWebservice;
    private TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render_test);
        myTextView = (TextView) findViewById(R.id.activity_render_test_TXT);
        myWebservice = NAAPIClient.getClient().create(NAAPIInterface.class);
        if (checkInternet()) {
            loadDataFromServer();
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * check internet
     *
     * @return
     */
    private boolean checkInternet() {
        return CEHelper.checkInternet(CERenderActivity.this);
    }

    private void loadDataFromServer() {
        final ProgressDialog aProgressDialog = new ProgressDialog(this);
        aProgressDialog.show();
        Call<MainInfo> aCall = myWebservice.getHtmlData();
        aCall.enqueue(new Callback<MainInfo>() {
            @Override
            public void onResponse(Call<MainInfo> call, Response<MainInfo> response) {
                aProgressDialog.dismiss();
                Spanned spanned = Html.fromHtml(response.body().getContentInfo().get(0).getContent(), new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        if (source.matches("data:image.*base64.*")) {
                            String base_64_source = source.replaceAll("data:image.*base64", "");
                            byte[] data = Base64.decode(base_64_source, Base64.NO_WRAP | Base64.URL_SAFE);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            Drawable image = new BitmapDrawable(CERenderActivity.this.getResources(), bitmap);
                            image.setBounds(0, 0, 0 + image.getIntrinsicWidth(), 0 + image.getIntrinsicHeight());
                            return image;
                        } else {
                            return null;
                        }
                    }

                }, null);
                myTextView.setText(spanned);
                myTextView.setTextSize(16);
            }

            @Override
            public void onFailure(Call<MainInfo> call, Throwable t) {
                aProgressDialog.dismiss();
            }
        });
    }


}
