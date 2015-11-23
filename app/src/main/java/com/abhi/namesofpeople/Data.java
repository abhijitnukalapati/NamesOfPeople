package com.abhi.namesofpeople;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A class representing the top level json data
 *
 * Created by abhijitnukalapati on 11/22/15.
 */
public class Data {

    public List<Name> people;

    /**
     * A class representing the first and last name
     * of a person
     */
    public static class Name {
        @SerializedName("first_name")
        public String firstName;

        @SerializedName("last_name")
        public String lastName;
    }
}
