package io.github.martinheywang.products.controller;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.resource.Buyable;
import io.github.martinheywang.products.api.model.resource.Craftable;
import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.Craftable.RemotePack;
import io.github.martinheywang.products.model.device.Buyer;
import io.github.martinheywang.products.model.device.Constructor;

public final class ResourceManager {

    /**
     * All the resources in the game.
     */
    private static final List<Resource> references = new ArrayList<>();

    private ResourceManager() {
    }

    public static void register(Class<? extends Resource> clazz) {
	if (!clazz.isEnum()) {
	    System.out.println("WARNING: Implementations of Resource must be an enum. Skipping.");
	    return;
	}

	for (final Field field : clazz.getFields())
	    try {
		if (Resource.class.isAssignableFrom(field.getType())) {
		    addReferences((Resource) field.get(null));

		    if (field.isAnnotationPresent(Buyable.class))
			Buyer.addAcceptedResource((Resource) field.get(null));
		    if (field.isAnnotationPresent(Craftable.class))
			Constructor.addAcceptedResource((Resource) field.get(null));
		}
	    } catch (IllegalArgumentException | IllegalAccessException e) {
		e.printStackTrace();
	    }

    }

    /**
     * Returns the value of the
     * 
     * @param clazz
     * @param field
     * @return
     */
    public static Resource valueOf(Class<? extends Resource> clazz, String field) {
	try {
	    final Resource res = (Resource) clazz.getField(field).get(null);
	    return res;
	} catch (final IllegalArgumentException e) {
	    e.printStackTrace();
	} catch (final IllegalAccessException e) {
	    e.printStackTrace();
	} catch (final NoSuchFieldException e) {
	    System.err.println("The requested field has not been found (requested: " + field + " in "
		    + clazz.getCanonicalName() + ")");
	    e.printStackTrace();
	} catch (final SecurityException e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * <p>
     * Search through all registered resources one matches the given parsable
     * string.
     * </p>
     * <p>
     * This method may be the sources of problems if two resources are named
     * similarly, in this case, the first one found will be returned.
     * </p>
     * <p>
     * If none is found, this method returns the default resource NONE.
     * </p>
     * 
     * @param str a parsable string, containing only the name of the resource.
     */
    public static Resource valueOf(String str) {
	for (final Resource resource : references)
	    if (resource.toString().equals(str))
		return resource;
	System.err.println("No Resource was found for input string : '" + str + "'\nReturning default resource NONE");
	return null;
    }

    /**
     * Transform a RemotePack in a regular pack.
     * 
     * @param pack
     * @return
     */
    public static Pack toPack(RemotePack pack) {
	return new Pack(valueOf(pack.clazz(), pack.field()), new BigInteger(pack.quantity()));
    }

    /**
     * Transforms an array of remote packs into an array of regular packs.
     * 
     * @param packs the remote packs
     * @return the regular packs
     */
    public static Pack[] toPack(RemotePack... packs) {
	final Pack[] realPacks = new Pack[packs.length];

	for (int i = 0; i < packs.length; i++)
	    realPacks[i] = toPack(packs[i]);

	return realPacks;
    }

    /**
     * Gets all of the resources loaded in the game.
     * 
     * @return a list of resources
     */
    public static List<Resource> getReferences() {
	return references;
    }

    /**
     * Register a new Resource in the list of references. This method is protected
     * against adding twice the same type.
     * 
     * @param resource the resource to add
     */
    public static void addReferences(Resource... resource) {
	for (final Resource res : resource)
	    references.add(res);
    }

    /**
     * Unregister the given resource in the list of references.
     * 
     * @param resource the resource to remove
     */
    public static void removeReferences(Resource... resource) {
	for (final Resource res : resource)
	    references.remove(res);
    }

}
