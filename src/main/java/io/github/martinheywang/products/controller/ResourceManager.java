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

/**
 * <p>
 * This class manages the resources in the game, loads the resources when
 * launching the game.
 * </p>
 * <p>
 * It also has a lot of utility methods about resources and packs, such as
 * {@link #toPack(RemotePack)}.
 * </p>
 * <p>
 * You can easily load a resource enum by calling {@link #register(Class)}, and
 * manages references individually using {@link #addReferences(Resource...)} and
 * {@link #removeReferences(Resource...)}.
 */
public final class ResourceManager {

    /**
     * All the resources in the game.
     */
    private static final List<Resource> references = new ArrayList<>();

    /**
     * Can't create a ResourceManager.
     */
    private ResourceManager() {
    }

    /**
     * Registers an enum type, extending
     * {@link io.github.martinheywang.products.api.model.resource.Resource}.
     * 
     * @param clazz an enum type implementing
     *              {@link io.github.martinheywang.products.api.model.resource.Resource}.
     */
    public static void register(Class<? extends Resource> clazz) {
        if (!clazz.isEnum()) {
            System.out.println("[WARNING] Class + " + clazz.getCanonicalName() + "isn't an enum.");
            System.out.println("[WARNING] To register a resource type in the references, those must be an enum.");
            System.out.println("-------------------------------------------------");
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
        System.out.println("| - - - " + clazz.getCanonicalName());
    }

    /**
     * Performs a 'valueOf()' on the given class using the given field name.
     * 
     * @param clazz the class where to search.
     * @param field the name of the requested field.
     * @return the founded resource, if one was found.
     */
    public static Resource valueOf(Class<? extends Resource> clazz, String field) {
        try {
            final Resource res = (Resource) clazz.getField(field).get(null);
            return res;
        } catch (final IllegalArgumentException e) {
            System.out.println("[WARNING] The requested field couldn't not be reached. (name: '" + field + "')");
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            System.out.println("[WARNING] The requested field couldn't not be reached. (name: '" + field
                    + "'). It may be private or protected.");
            e.printStackTrace();
        } catch (final NoSuchFieldException e) {
            System.out.println("[WARNING] Field " + field + " in the existing class " + clazz.getCanonicalName()
                    + " has not been found.");
            e.printStackTrace();
        } catch (final Exception e) {
            System.out.println("[WARNING] The requested field threw :");
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
     * @return the resource from the parsed string.
     */
    public static Resource valueOf(String str) {
        for (final Resource resource : references)
            if (resource.toString().equals(str))
                return resource;
        System.err.println("No Resource was found for input string : '" + str + "'\nReturning default resource NONE");
        return null;
    }

    /**
     * Transform a
     * {@link io.github.martinheywang.products.api.model.resource.Craftable.RemotePack}
     * in a regular pack.
     * 
     * @param pack the remote pack to transform.
     * @return the pack from the remote
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
