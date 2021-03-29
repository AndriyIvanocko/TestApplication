package com.example.testapplication.data.network.transformers;


import com.example.testapplication.data.network.model.response.user.AppInitData;
import com.example.testapplication.data.network.model.response.user.LoginData;
import com.example.testapplication.data.network.model.response.user.UserData;
import com.example.testapplication.domain.model.user.AppInitModel;
import com.example.testapplication.domain.model.user.LoginModel;
import com.example.testapplication.domain.model.user.UserModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class UserTransformer {

    @Inject
    public UserTransformer() {
    }

    private LoginModel provideLoginModel(LoginData data) {
        LoginModel model = new LoginModel();
        if (data != null) {
            model.setToken(data.getToken());
            model.setAppInit(provideAppInitModel(data.getAppInit()));
        }

        return model;
    }

    private AppInitModel provideAppInitModel(AppInitData data) {
        AppInitModel model = new AppInitModel();
        if (data != null) {
            model.setUser(provideUserModel(data.getUser()));
        }
        return model;
    }

    private UserModel provideUserModel(UserData data) {
        UserModel model = new UserModel();
        if (data != null) {
            model.setAvatarUrl(data.getAvatarUrl());
            model.setTimezoneOffset(data.getTimezoneOffset());
            model.setTelegramConnectUrl(data.getTelegramConnectUrl());
            model.setRole(data.getRole());
            model.setProjects(data.getProjects());
            model.setNick(data.getNick());
            model.setName(data.getName());
            model.setIsTimerOnline(data.getIsTimerOnline());
            model.setIsTelegramConnected(data.getIsTelegramConnected());
            model.setIsSharedFromMe(data.getIsSharedFromMe());
            model.setIsOnline(data.getIsOnline());
            model.setIsActive(data.getIsActive());
            model.setIdCompany(data.getIdCompany());
            model.setEmail(data.getEmail());
            model.setDuePenalties(data.getDuePenalties());
            model.setDtaCreate(data.getDtaCreate());
            model.setDtaActivity(data.getDtaActivity());
        }

        return model;
    }

    public List<UserModel> provideUserModelList(List<UserData> dataList){
        List<UserModel> list = new ArrayList<>();
        if(dataList != null){
            for (UserData data : dataList){
                list.add(provideUserModel(data));
            }
        }

        return list;
    }
    public ObservableTransformer<LoginData, LoginModel> tranformation() {
        return upstream -> upstream.map(data -> provideLoginModel(data));
    }
}
