package com.tmsoft.runnerz.run.user;

public record Address(
    String street,
    String suite,
    String city,
    String zipcode,
    Geo geo) {
}
