/*
 * Copyright 2017 mihy,org.
 * All rights reserved.
 */
package org.mihy.gowma.constants;


public  enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static Gender lookup(String name){
        if(name == null){
            return null;
        }
        return Gender.valueOf(name);
    }
}