package com.harxsh.springboot.mongodb.collection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    private String country;
    private String state;
    private String zip;
    private String city;
    private String street;
}
