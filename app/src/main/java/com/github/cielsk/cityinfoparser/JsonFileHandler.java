package com.github.cielsk.cityinfoparser;

import android.content.Context;
import android.content.res.AssetManager;
import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 *
 */
public class JsonFileHandler {

    private final AssetManager mAssetManager;

    private static final Gson GSON = new Gson();

    private static final String FILE_NAME = "china-city-list.json";

    private static final String ENCODING = "UTF-8";

    public JsonFileHandler(Context context) {
        mAssetManager = context.getAssets();
    }

    @RxLogObservable
    public Observable<List<AddressInfo>> handle() {
        Observable<String> localTask = readLocalJsonFile();

        return localTask
            .map(JsonFileHandler::parse);
    }

    @RxLogObservable
    private Observable<String> readLocalJsonFile() {
        return Observable.just(FILE_NAME).map(file -> {
            InputStream inputStream = null;
            try {
                inputStream = mAssetManager.open(file);
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                return new String(buffer, ENCODING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).subscribeOn(Schedulers.io());
    }

    private static List<AddressInfo> parse(String data) {
        List<AddressInfo> re = GSON.fromJson(data, new TypeToken<List<AddressInfo>>() {
        }.getType());
        return re;
    }
}
