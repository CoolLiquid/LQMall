package com.simplexx.wnp.baselib.repositories;

/**
 * Created by fan-gk on 2017/4/20.
 */

public class ClientRepositories {

    private static ClientRepositories clientRepositories;

    public static ClientRepositories getInstance() {
        return clientRepositories;
    }

    static {
        clientRepositories = new ClientRepositories();
    }

    private ClientRepositories() {
    }

    private ISharedPreferencesRepositoryProvider sharedPreferencesRepositoryProvider;

    public ISharedPreferencesRepositoryProvider getSharedPreferencesRepositoryProvider() {
        return sharedPreferencesRepositoryProvider;
    }

    public void setSharedPreferencesRepositoryProvider(ISharedPreferencesRepositoryProvider sharedPreferencesRepositoryProvider) {
        this.sharedPreferencesRepositoryProvider = sharedPreferencesRepositoryProvider;
    }

}
