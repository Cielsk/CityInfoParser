package com.github.cielsk.cityinfoparser;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;

/**
 *
 */
@AutoValue
public abstract class City implements CityModel {

    public static final Factory<City> FACTORY = new Factory<>(AutoValue_City::new);

    public static final RowMapper<City> SELECT_ALL_MAPPER = FACTORY.select_allMapper();

    public static final RowMapper<City> SELECT_BY_ID_MAPPER = FACTORY.select_by_idMapper();

    public static final RowMapper<City> SELECT_BY_PROVINCE_ID_MAPPER
        = FACTORY.select_by_province_idMapper();
}
