package app.customedt.com.customeditor.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainInfo {

    @SerializedName("content_info")
    @Expose
    private List<ContentInfo> contentInfo = null;
    @SerializedName("Response")
    @Expose
    private Response response;

    public List<ContentInfo> getContentInfo() {
        return contentInfo;
    }

    public void setContentInfo(List<ContentInfo> contentInfo) {
        this.contentInfo = contentInfo;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}