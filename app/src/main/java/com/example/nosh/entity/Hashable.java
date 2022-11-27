package com.example.nosh.entity;


import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Date;


public abstract class Hashable implements Serializable {

    /**
     * This field is used for Id of a document in the database
     * The hashcode generate by taking the time of which this object creates,
     * and then hash using sha256.
     */
    protected String hashcode;

    private static String dayHashing() {
        return Hashing
                .sha256()
                .hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
    }

    public static String doubleHashing(String dayHash) {
        return Hashing
                .sha256()
                .hashString(dayHash, Charset.defaultCharset())
                .toString();
    }

    protected Hashable() {
        hashcode = dayHashing();
    }


    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        assert hashcode != null;

        this.hashcode = hashcode;
    }
}
