package com.example.samyenzo.ahmedsamy_merah_a1;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by samyenzo on 2017-09-17.
 */

public class Guest
{
    //field variable for the number
    private String num;

    //constructor of class
    public Guest(String num)
    {
        this.num = num;
    }

    //creatin the full array
    public static ArrayList<Guest> getGuests(Context context)
    {
        String[] peopleNum = context.getResources().getStringArray(R.array.people);
        ArrayList<Guest> persons = new ArrayList<>();

        //for loop to iterate through the list contained in ressources
        for(String numberGuest: peopleNum)
        {
            persons.add(new Guest(numberGuest));
        }

        return persons;
    }

    //converting the objects to string for users to be able to read
    public  String toString()
    {
        return num;
    }

}

