package com.example.network;


import com.example.baseprotocol.IBaseProtocol;

public interface INetworkProtocol extends IBaseProtocol {

    void post();
    void delete();

}
