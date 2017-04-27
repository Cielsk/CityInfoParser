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
            String provinceNameEn = info.getProvinceEn();
            String provinceNameZh = info.getProvinceZh();
            mProvinceIdCached = provinceId;
            insertProvince(provinceId, provinceNameEn, provinceNameZh);
        }

        if (mCityIdCached != cityId && isValidCity(info)) {
            String cityNameEn = info.getCityEn();
            String cityNameZh = info.getCityZh();
            mCityIdCached = cityId;
            insertCity(cityId, cityNameEn, cityNameZh, weatherCode, provinceId);
        }

        if (isValidCounty(info)) {
            String countyNameEn = info.getCityEn();
            String countyNameZh = info.getCityZh();
            insertCounty(countyId, countyNameEn, countyNameZh, weatherCode, cityId);
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

    private void insertProvince(int id, @NonNull String nameEn, @NonNull String nameZh) {
        Province.Insert_row insertRow
            = new ProvinceModel.Insert_row(mDatabase.getReadableDatabase());
        insertRow.bind(id, nameEn, nameZh);
        mDatabase.executeInsert(insertRow.table, insertRow.program);
    }

    private void insertCity(int id, @NonNull String nameEn, @NonNull String nameZh, @NonNull String code, int province_id) {
        City.Insert_row insertRow = new CityModel.Insert_row(mDatabase.getReadableDatabase());
        insertRow.bind(id, nameEn, nameZh, code, province_id);
        mDatabase.executeInsert(insertRow.table, insertRow.program);
    }

    private void insertCounty(int id, @NonNull String nameEn, @NonNull String nameZh, @NonNull String code, int city_id) {
        County.Insert_row insertRow = new CountyModel.Insert_row(mDatabase.getReadableDatabase());
        insertRow.bind(id, nameEn, nameZh, code, city_id);
        mDatabase.executeInsert(insertRow.table, insertRow.program);
    }
}
