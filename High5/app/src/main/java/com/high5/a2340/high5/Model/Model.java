package com.high5.a2340.high5.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by david on 2/19/2018.
 */

public class Model {

    public static final List<String> legalUserTypes = Arrays.asList(UserTypes.USER.getValue(),
                                                                            UserTypes.ADMIN.getValue(),
                                                                            UserTypes.EMPLOYEE.getValue());
    public List<Shelter> shelterList = new ArrayList<Shelter>();

    public void populateShelters() {
        shelterList.add(new Shelter("address", 99, 0, 0, "phone", "restrictions", "shelter name", "notes")); //for testing
        shelterList.add(new Shelter("address1", 99, 0, 0, "phone1", "restrictions1", "shelter name1", "notes1")); //for testing
        shelterList.add(new Shelter("address2", 99, 0, 0, "phone2", "restrictions2", "shelter name2", "notes2")); //for testing
    }
}
