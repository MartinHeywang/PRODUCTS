package io.github.martinheywang.products.api.utils;

import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.net.URL;

import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.device.PricesModule;
import io.github.martinheywang.products.api.model.device.annotation.AccessibleName;
import io.github.martinheywang.products.api.model.device.annotation.ActionCost;
import io.github.martinheywang.products.api.model.device.annotation.Buildable;
import io.github.martinheywang.products.api.model.device.annotation.DefaultTemplate;
import io.github.martinheywang.products.api.model.device.annotation.Description;
import io.github.martinheywang.products.api.model.device.annotation.Independent;
import io.github.martinheywang.products.api.model.device.annotation.Prices;
import io.github.martinheywang.products.api.model.template.Template.PointerType;
import io.github.martinheywang.products.api.model.template.TemplateCreator;
import io.github.martinheywang.products.api.model.template.TemplateModel;

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
        return "Aucun nom.";
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
        return "Aucune description.";
    }

    /**
     * Returns the prices modules containing all the prices provided byt the
     * {@link io.github.martinheywang.products.api.model.device.annotation.Prices}
     * annotation.
     * 
     * @param clazz the class to retrieve the data from
     * @return the prices
     */
    public static PricesModule getPrices(Class<? extends Device> clazz) {
        if (clazz.isAnnotationPresent(Prices.class)) {
            final Prices annotation = clazz.getAnnotation(Prices.class);
            return new PricesModule(annotation.build(), annotation.upgradeTo2(), annotation.upgradeTo3(),
                    annotation.destroyAt1(), annotation.destroyAt2(), annotation.destroyAt3());
        }
        return new PricesModule("0", "0", "0", "0", "0", "0");
    }

    /**
     * Returns the build price provided by the
     * {@link io.github.martinheywang.products.api.model.device.annotation.Prices}
     * annotation. This is the price of building a device of the given type.
     * 
     * @param clazz the class to retrieve the data from.
     * @return the build price of the device type
     */
    public static BigInteger getBuildPrice(Class<? extends Device> clazz) {
        return getPrices(clazz).getLevel1Build();
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
        return getPrices(clazz).getLevel2Build();
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
        return getPrices(clazz).getLevel3Build();
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
        return getPrices(clazz).getLevel1Delete();
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
        return getPrices(clazz).getLevel2Delete();
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
       return getPrices(clazz).getLevel3Delete();
    }

    /**
     * <p>
     * Returns an URL pointing to the image of the device type. The image is
     * searched in the device folder, itself in the resource folder of the class.
     * (call to {@link java.lang.Class#getResource(String)}). The image must be an SVG, and
     * the view box should be square, no matter the size. The name of the SVG should
     * be 'SimpleNameOfTheClass.svg'. The simple name of the class is the name
     * without the package. (See {@link java.lang.Class#getSimpleName()}).
     * </p>
     * 
     * @param clazz the class to retrieve the data from
     * @return the path to the image of the device type
     */
    public static URL getView(Class<? extends Device> clazz) {
        if(clazz == null){
            return null;
        }
        if (Modifier.isAbstract(clazz.getModifiers())) {
            System.err.println("'" + clazz.getCanonicalName()
                    + "' is abstract, it should not have an image (and therefore the URL may not be resolved.");
            return null;
        }
        return clazz.getResource("/device/" + clazz.getSimpleName() + ".svg");
    }

    /**
     * Returns the static action cost of the device type. Here 'static' means it
     * doesn't depend on the level of a certain device.
     * 
     * @param clazz the clazz to extract the data from
     * @return the cost per action of the device type
     */
    public static BigInteger getActionCost(Class<? extends Device> clazz) {
        if (clazz.isAnnotationPresent(ActionCost.class)) {
            return new BigInteger(clazz.getAnnotation(ActionCost.class).value());
        }
        return BigInteger.ZERO;
    }

    /**
     * Returns the
     * {@link io.github.martinheywang.products.api.model.template.TemplateModel} of
     * the device type.
     * 
     * @param clazz the device type to retrieve the data from
     * @return the template model (or default template)
     */
    public static TemplateModel getDefaultTemplate(Class<? extends Device> clazz) {
        if (clazz.isAnnotationPresent(DefaultTemplate.class)) {
            final DefaultTemplate annotation = clazz.getAnnotation(DefaultTemplate.class);
            final TemplateModel template = TemplateCreator.withAnnotation(annotation);
            return template;
        }

        // Default default template (no entry, no exit)
        return TemplateCreator.getSingleton().setAll(PointerType.NONE).getModel();
    }

    /**
     * Returns the number of entries of the given device type.
     * 
     * @param clazz the device type to retrieve the data from
     * @return how many entries the device type has
     */
    public static Integer getEntriesCount(Class<? extends Device> clazz) {
        final TemplateModel template = getDefaultTemplate(clazz);
        return template.countOf(PointerType.ENTRY);
    }

    /**
     * Returns the number of exits of the given device type.
     * 
     * @param clazz the device type to retrieve the data from
     * @return how many exits the device type has
     */
    public static Integer getExitsCount(Class<? extends Device> clazz) {
        final TemplateModel template = getDefaultTemplate(clazz);
        return template.countOf(PointerType.EXIT);
    }

    /**
     * Returns whether the device type is buildable (by the user).
     * 
     * @param clazz the class to retrieve the data from.
     * @return whether the type is buildable or not.
     */
    public static Boolean isBuildable(Class<? extends Device> clazz){
        if(clazz.isAnnotationPresent(Buildable.class)){
            return true;
        }
        return false;
    }
    
    /**
     * Returns whether the device type is independent.
     * 
     * @param clazz the class to retrieve the data from.
     * @return whether the type is independent or not.
     */
    public static Boolean isIndependent(Class<? extends Device> clazz){
        if(clazz.isAnnotationPresent(Independent.class)){
            return true;
        }
        return false;
    }
}