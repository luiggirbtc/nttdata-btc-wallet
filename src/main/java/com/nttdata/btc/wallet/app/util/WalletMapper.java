package com.nttdata.btc.wallet.app.util;

import com.nttdata.btc.wallet.app.model.entity.Wallet;
import com.nttdata.btc.wallet.app.model.request.WalletRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Date;

@Mapper
public interface WalletMapper {
    @Mapping(target = "typeDocument", source = "typeDocument")
    @Mapping(target = "numberDocument", source = "numberDocument")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "registerDate", source = "request", qualifiedByName = "setDate")
    @Mapping(target = "status", source = "request", qualifiedByName = "setStatus")
    Wallet toWallet(WalletRequest request);

    @Named("setDate")
    static Date setDate(WalletRequest request) {
        return new Date();
    }

    @Named("setStatus")
    static Boolean setStatus(WalletRequest request) {
        return Boolean.TRUE;
    }
}