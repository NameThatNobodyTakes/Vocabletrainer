package com.rubengees.vocables.core.test.logic;

import android.os.Parcel;
import android.os.Parcelable;

import com.rubengees.vocables.pojo.Meaning;
import com.rubengees.vocables.pojo.Vocable;

/**
 * Created by Ruben on 04.04.2015.
 */
public class MeaningCell implements Parcelable {

    public static final Creator<MeaningCell> CREATOR = new Creator<MeaningCell>() {

        public MeaningCell createFromParcel(Parcel in) {
            return new MeaningCell(in);
        }

        public MeaningCell[] newArray(int size) {
            return new MeaningCell[size];
        }
    };

    private Vocable vocable;
    private Meaning meaning;

    private MeaningCell(Parcel in) {
        readFromParcel(in);
    }

    public MeaningCell(Vocable vocable, Meaning meaning) {
        this.vocable = vocable;
        this.meaning = meaning;
    }

    private void readFromParcel(Parcel in) {
        vocable = in.readParcelable(Vocable.class.getClassLoader());
        meaning = in.readParcelable(Meaning.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(vocable, flags);
        out.writeParcelable(meaning, flags);
    }

    public Vocable getVocable() {
        return vocable;
    }

    public Meaning getMeaning() {
        return meaning;
    }
}
