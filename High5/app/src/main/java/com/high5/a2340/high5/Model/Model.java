package com.high5.a2340.high5.Model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by david on 2/19/2018.
 */

public class Model {

    public static final List<String> legalUserTypes = Arrays.asList(UserTypes.USER.getValue(),
                                                                            UserTypes.ADMIN.getValue(),
                                                                            UserTypes.EMPLOYEE.getValue());
}
