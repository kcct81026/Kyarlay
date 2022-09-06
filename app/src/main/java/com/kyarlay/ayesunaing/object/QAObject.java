package com.kyarlay.ayesunaing.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class QAObject extends UniversalPost{

    @SerializedName("id")
    private int id;

    @SerializedName("question")
    private String question;

    @SerializedName("answer")
    private String answer;

    @SerializedName("status")
    private String status;

    @SerializedName("product_id")
    private int product_id;

    public QAObject(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public static final Parcelable.Creator<QAObject> CREATOR = new Parcelable.Creator<QAObject>() {
        public QAObject createFromParcel(Parcel in) {
            return new QAObject(in);
        }

        public QAObject[] newArray(int size) {

            return new QAObject[size];
        }

    };

    public QAObject(Parcel in) {
        super();
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        id              = in.readInt();
        question        = in.readString();
        answer          = in.readString();
        status          = in.readString();
        product_id      = in.readInt();

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(status);
        dest.writeInt(product_id);

    }
    @Override
    public int describeContents() {
        return 0;
    }

}
