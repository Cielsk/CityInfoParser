package com.github.cielsk.cityinfoparser;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;

/**
 *
 */
@AutoValue
public abstract class Province implements ProvinceModel {

    public static final Factory<Province> FACTORY = new Factory<>(AutoValue_Province::new);

    public static final RowMapper<Province> SELECT_ALL_MAPPER = FACTORY.select_allMapper();

    public static final RowMapper<Province> SELECT_BY_ID_MAPPER = FACTORY.select_by_idMapper();
}
