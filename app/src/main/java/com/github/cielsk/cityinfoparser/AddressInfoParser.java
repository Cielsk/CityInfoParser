package com.github.cielsk.cityinfoparser;

import android.content.Context;
import android.support.annotation.NonNull;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import rx.schedulers.Schedulers;

/**
 *
 */
public class AddressInfoParser {

    private final BriteDatabase mDatabase;

    private int mProvinceIdCached;
    private int mCityIdCached;

    public AddressInfoParser(Context context) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDatabase = sqlBrite.wrapDatabaseHelper(new DbOpenHelper(context), Schedulers.io());

        mProvinceIdCached = 0;
        mCityIdCached = 0;
    }

    public void parse(AddressInfo info) {
        String weatherCode = info.getId();

        int provinceId = Integer.parseInt(weatherCode.substring(5, 7));
        int cityId = Integer.parseInt(weatherCode.substring(5, 9));
        int countyId = Integer.parseInt(weatherCode.substring(5));

        if (mProvinceIdCached != provinceId) {
            String provinceName = info.getProvinceZh();
            mProvinceIdCached = provinceId;
            insertProvince(provinceId, provinceName);
        }

        if (mCityIdCached != cityId && isValidCity(info)) {
            String cityName = info.getCityZh();
            mCityIdCached = cityId;
            insertCity(cityId, cityName, weatherCode, provinceId);
        }

        if (isValidCounty(info)) {
            String countyName = info.getCityZh();
            insertCounty(countyId, countyName, weatherCode, cityId);
        }
    }

    private boolean isValidCounty(AddressInfo info) {
        return !isValidCity(info);
    }

    private static boolean isValidCity(AddressInfo info) {
        return info
                   .getCityZh()
                   .equals(info.getLeaderZh()) || info
                   .getLeaderZh()
                   .equals(info.getProvinceZh());
    }

    private void insertProvince(int id, @NonNull String name) {
        Province.Insert_row insertRow
            = new ProvinceModel.Insert_row(mDatabase.getReadableDatabase());
        insertRow.bind(id, name);
        mDatabase.executeInsert(insertRow.table, insertRow.program);
    }

    private void insertCity(int id, @NonNull String name, @NonNull String code, int province_id) {
        City.Insert_row insertRow = new CityModel.Insert_row(mDatabase.getReadableDatabase());
        insertRow.bind(id, name, code, province_id);
        mDatabase.executeInsert(insertRow.table, insertRow.program);
    }

    private void insertCounty(int id, @NonNull String name, @NonNull String code, int city_id) {
        County.Insert_row insertRow = new CountyModel.Insert_row(mDatabase.getReadableDatabase());
        insertRow.bind(id, name, code, city_id);
        mDatabase.executeInsert(insertRow.table, insertRow.program);
    }
}
