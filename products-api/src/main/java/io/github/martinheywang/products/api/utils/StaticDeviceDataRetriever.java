package io.github.martinheywang.products.api.utils;

import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.net.URL;

import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.Description;
import io.github.martinheywang.products.api.model.device.annotation.Prices;

/**
 * Utility class that allows to get static data of a device type. Those
 * informations are given by the annotation on top of the class extending
 * {@link io.github.martinheywang.products.api.model.device.Device}.
 */
public final class StaticDeviceDataRetriever {

    private StaticDeviceDataRetriever() {
    }

    /**
     * Returns the String provided by the
     * {@link io.github.martinheywang.products.api.model.device.annotation.AccessibleName}
     * annotation. This String is the name of the device, shown in the GUI.
     * 
     * @param clazz the class to retreieve the data from
     * @return the name of the device type.
     */
    public static String getAccessibleName(Class<? extends Device> clazz) {
        if (clazz.isAnnotationPresent(AccessibleName.class)) {
            return clazz.getAnnotation(AccessibleName.class).value();
        }
        return "Aucun nom...";
    }

    /**
     * Returns the String provided by the
     * {@link io.github.martinheywang.products.api.model.device.annotation.Description}.
     * It is the description of the device type, what it does; what it allows.
     * 
     * @param clazz the class to retrieve the data from.
     * @return the description of the device type.
     */
    public static String getDescription(Class<? extends Device> clazz) {
        if (clazz.isAnnotationPresent(Description.class)) {
            return clazz.getAnnotation(Description.class).value();
        }
        return "Aucun description..";
    }

    /**
     * Returns the build price provided by the
     * {@link io.github.martinheywang.products.api.model.device.annotation.Prices}
     * annotation. This is the price of building a device of the given type.
     * 
     */
    public static BigInteger getBuildPrice(Class<? extends Device> clazz) {
        if (clazz.isAnnotationPresent(Prices.class)) {
            return new BigInteger(clazz.getAnnotation(Prices.class).build());
        }
        return BigInteger.ZERO;
    }

    /**
     * Returns the BigInteger corresponding to the upgrade price (to upgrade to
     * level 2) provided by the
     * {@link io.github.martinheywang.products.api.model.device.annotation.Prices}.
     * annotation. This is the price of upgrading this device type to the level 2.
     * 
     * @param clazz the class to retrieve the data from
     * @return the upgrade price
     */
    public static BigInteger getUpgradeTo2Price(Class<? extends Device> clazz) {
        if (clazz.isAnnotationPresent(Prices.class)) {
            return new BigInteger(clazz.getAnnotation(Prices.class).upgradeTo2());
        }
        return BigInteger.ZERO;
    }

    /**
     * Returns the BigInteger corresponding to the upgrade price (to upgrade to
     * level 3) provided by the
     * {@link io.github.martinheywang.products.api.model.device.annotation.Prices}.
     * annotation. This is the price of upgrading this device type to the level 3.
     * 
     * @param clazz the class to retrieve the data from
     * @return the upgrade price
     */
    public static BigInteger getUpgradeTo3Price(Class<? extends Device> clazz) {
        if (clazz.isAnnotationPresent(Prices.class)) {
            return new BigInteger(clazz.getAnnotation(Prices.class).upgradeTo3());
        }
        return BigInteger.ZERO;
    }

    /**
     * Returns the BigInteger corresponding to the destroy price of a device of this
     * type at the level 1. Data is provided by the
     * {@link io.github.martinheywang.products.api.model.device.annotation.Prices}
     * annotation.
     * 
     * @param clazz the class to retrieve the data from
     * @return the destory price
     */
    public static BigInteger getDestoryAt1Gain(Class<? extends Device> clazz) {
        if (clazz.isAnnotationPresent(Prices.class)) {
            return new BigInteger(clazz.getAnnotation(Prices.class).destroyAt1());
        }
        return BigInteger.ZERO;
    }

    /**
     * Returns the BigInteger corresponding to the destroy price of a device of this
     * type at the level 2. Data is provided by the
     * {@link io.github.martinheywang.products.api.model.device.annotation.Prices}
     * annotation.
     * 
     * @param clazz the class to retrieve the data from
     * @return the destory price
     */
    public static BigInteger getDestoryAt2Gain(Class<? extends Device> clazz) {
        if (clazz.isAnnotationPresent(Prices.class)) {
            return new BigInteger(clazz.getAnnotation(Prices.class).destroyAt2());
        }
        return BigInteger.ZERO;
    }

    /**
     * Returns the BigInteger corresponding to the destroy price of a device of this
     * type at the level 3. Data is provided by the
     * {@link io.github.martinheywang.products.api.model.device.annotation.Prices}
     * annotation.
     * 
     * @param clazz the class to retrieve the data from
     * @return the destory price
     */
    public static BigInteger getDestoryAt3Gain(Class<? extends Device> clazz) {
        if (clazz.isAnnotationPresent(Prices.class)) {
            return new BigInteger(clazz.getAnnotation(Prices.class).destroyAt3());
        }
        return BigInteger.ZERO;
    }

    /**
     * <p>
     * Returns an URL pointing to the image of the device type. The image is
     * searched in the device folder, itself in the resource folder of the class.
     * (call to {@link Class#getResource(String)}). The image must be an SVG, and
     * the view box should be square, no matter the size. The name of the SVG should
     * be 'SimpleNameOfTheClass.svg'. The simple name of the class is the name
     * without the package. (See {@link Class#getSimpleName()}).
     * </p>
     * <p>
     * For example, the associated image to the
     * {@link io.github.martinheywang.products.api.model.device.Buyer} class will be
     * found at '[resource_folder]/device/Buyer.svg'.
     * </p>
     */
    public static URL getView(Class<? extends Device> clazz) {
        if (Modifier.isAbstract(clazz.getModifiers())) {
            System.err.println("'" + clazz.getCanonicalName()
                    + "' is abstract, it should not have an image (and therefore the URL can't may not be resolved.");
            return null;
        }
        return clazz.getResource("/device/" + clazz.getSimpleName() + ".svg");
    }
}