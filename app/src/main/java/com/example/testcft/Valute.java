package com.example.testcft;

import android.os.Parcel;
import android.os.Parcelable;

public class Valute implements Parcelable {
    private String id;
    private String numCode;
    private String charCode;
    private Integer nominal;
    private String name;
    private Double value;
    private Double previous;

    public Valute() {
    }

    public Valute(String id, String numCode, String charCode, Integer nominal, String name, Double value, Double previous) {
        this.id = id;
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.previous = previous;
    }

    protected Valute(Parcel in) {
        id = in.readString();
        numCode = in.readString();
        charCode = in.readString();
        if (in.readByte() == 0) {
            nominal = null;
        } else {
            nominal = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            value = null;
        } else {
            value = in.readDouble();
        }
        if (in.readByte() == 0) {
            previous = null;
        } else {
            previous = in.readDouble();
        }
    }

    public static final Creator<Valute> CREATOR = new Creator<Valute>() {
        @Override
        public Valute createFromParcel(Parcel in) {
            return new Valute(in);
        }

        @Override
        public Valute[] newArray(int size) {
            return new Valute[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public Integer getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    public Double getPrevious() {
        return previous;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(numCode);
        parcel.writeString(charCode);
        if (nominal == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(nominal);
        }
        parcel.writeString(name);
        if (value == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(value);
        }
        if (previous == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(previous);
        }
    }
}
