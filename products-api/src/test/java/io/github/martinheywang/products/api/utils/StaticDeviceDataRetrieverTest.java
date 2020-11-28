package io.github.martinheywang.products.api.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import io.github.martinheywang.products.api.model.device.Device;

public class StaticDeviceDataRetrieverTest {
    
    @Test
    public void getAccessibleName_returnsTheName_ofTheDeviceType(){
        Class<? extends Device> clazz = Device.class;

        final String name = StaticDeviceDataRetriever.getAccessibleName(clazz);

        assertThat(name).isEqualTo("No name provided.");
    }
    
    @Test
    public void getDescription_returnsTheDescription_ofTheDeviceType(){
        Class<? extends Device> clazz = Device.class;

        final String desc = StaticDeviceDataRetriever.getDescription(clazz);

        assertThat(desc).isEqualTo("No description provided.");
    }

    @Test
    public void getBuildPrice_returnsZero_ofTheDeviceType(){
        Class<? extends Device> clazz = Device.class;

        final BigInteger price = StaticDeviceDataRetriever.getBuildPrice(clazz);

        assertThat(price).isEqualTo(new BigInteger("0"));
    }

    @Test
    public void getUpgradeTo2Price_returnsZero_ofTheDeviceType(){
        Class<? extends Device> clazz = Device.class;

        final BigInteger price = StaticDeviceDataRetriever.getUpgradeTo2Price(clazz);

        assertThat(price).isEqualTo(new BigInteger("0"));
    }

    @Test
    public void getUpgradeTo3Price_returnsZero_ofTheDeviceType(){
        Class<? extends Device> clazz = Device.class;

        final BigInteger price = StaticDeviceDataRetriever.getUpgradeTo3Price(clazz);

        assertThat(price).isEqualTo(new BigInteger("0"));
    }

    @Test
    public void getDestoryAt1Gain_returnsZero_ofTheDeviceType(){
        Class<? extends Device> clazz = Device.class;

        final BigInteger price = StaticDeviceDataRetriever.getDestoryAt1Gain(clazz);

        assertThat(price).isEqualTo(new BigInteger("0"));
    }

    @Test
    public void getDestoryAt2Gain_returnsZero_ofTheDeviceType(){
        Class<? extends Device> clazz = Device.class;

        final BigInteger price = StaticDeviceDataRetriever.getDestoryAt2Gain(clazz);

        assertThat(price).isEqualTo(new BigInteger("0"));
    }

    @Test
    public void getDestoryAt3Price_returnsZero_ofTheDeviceType(){
        Class<? extends Device> clazz = Device.class;

        final BigInteger price = StaticDeviceDataRetriever.getDestoryAt3Gain(clazz);

        assertThat(price).isEqualTo(new BigInteger("0"));
    }
}
