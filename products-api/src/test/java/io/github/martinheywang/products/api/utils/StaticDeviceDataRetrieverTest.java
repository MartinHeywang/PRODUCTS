/*
   Copyright 2020 Martin Heywang

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.martinheywang.products.api.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import io.github.martinheywang.products.api.model.device.Device;

public class StaticDeviceDataRetrieverTest {

    @Test
    public void getAccessibleName_returnsTheName_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final String name = StaticDeviceDataRetriever.getAccessibleName(clazz);

        assertThat(name).isEqualTo("Aucun nom.");
    }

    @Test
    public void getDescription_returnsTheDescription_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final String desc = StaticDeviceDataRetriever.getDescription(clazz);

        assertThat(desc).isEqualTo("Aucune description.");
    }

    @Test
    public void getBuildPrice_returnsZero_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final BigInteger price = StaticDeviceDataRetriever.getBuildPrice(clazz);

        assertThat(price).isEqualTo(new BigInteger("0"));
    }

    @Test
    public void getUpgradeTo2Price_returnsZero_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final BigInteger price = StaticDeviceDataRetriever.getUpgradeTo2Price(clazz);

        assertThat(price).isEqualTo(new BigInteger("0"));
    }

    @Test
    public void getUpgradeTo3Price_returnsZero_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final BigInteger price = StaticDeviceDataRetriever.getUpgradeTo3Price(clazz);

        assertThat(price).isEqualTo(new BigInteger("0"));
    }

    @Test
    public void getDestoryAt1Gain_returnsZero_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final BigInteger price = StaticDeviceDataRetriever.getDestoryAt1Gain(clazz);

        assertThat(price).isEqualTo(new BigInteger("0"));
    }

    @Test
    public void getDestoryAt2Gain_returnsZero_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final BigInteger price = StaticDeviceDataRetriever.getDestoryAt2Gain(clazz);

        assertThat(price).isEqualTo(new BigInteger("0"));
    }

    @Test
    public void getDestoryAt3Price_returnsZero_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final BigInteger price = StaticDeviceDataRetriever.getDestoryAt3Gain(clazz);

        assertThat(price).isEqualTo(new BigInteger("0"));
    }

    @Test
    public void getActionCost_returnsZero_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final BigInteger cost = StaticDeviceDataRetriever.getActionCost(clazz);

        assertThat(cost).isEqualTo(BigInteger.ZERO);
    }

    @Test
    public void getEntriesCount_returnsZero_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final Integer cost = StaticDeviceDataRetriever.getEntriesCount(clazz);

        assertThat(cost).isEqualTo(0);
    }

    @Test
    public void getExitsCount_returnsZero_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final Integer cost = StaticDeviceDataRetriever.getExitsCount(clazz);

        assertThat(cost).isEqualTo(0);
    }

    @Test
    public void isBuildable_retursFalse_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final boolean value = StaticDeviceDataRetriever.isBuildable(clazz);

        assertThat(value).isEqualTo(false);
    }

    @Test
    public void isIndependent_retursFalse_ofTheDeviceType() {
        Class<? extends Device> clazz = Device.class;

        final boolean value = StaticDeviceDataRetriever.isIndependent(clazz);

        assertThat(value).isEqualTo(false);
    }
}