package com.github.cielsk.cityinfoparser;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;

/**
 *
 */
@AutoValue
public abstract class County implements CountyModel {

    public static final Factory<County> FACTORY = new Factory<>(AutoValue_County::new);

    public static final RowMapper<County> SELECT_ALL_MAPPER = FACTORY.select_allMapper();

    public static final RowMapper<String> SELECT_BY_ID_MAPPER = FACTORY.select_by_idMapper();

    public static final RowMapper<String> SELECT_BY_CITY_ID_MAPPER
        = FACTORY.select_by_city_idMapper();
}
