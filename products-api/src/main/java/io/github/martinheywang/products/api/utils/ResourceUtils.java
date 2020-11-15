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

import java.lang.reflect.Field;

import io.github.martinheywang.products.api.model.resource.Resource;
import io.github.martinheywang.products.api.model.resource.annotation.AnnotationPackGroup;
import io.github.martinheywang.products.api.model.resource.annotation.Tag;

/**
 * <p>
 * Utility class that manages that allows treatment about resources.
 * </p>
 * <p>
 * It provides an easy way to get additionnal informations about resources or
 * the annotations on top of the declaration of the resource.
 * </p>
 * 
 * @author Martin Heywang
 */
public final class ResourceUtils {

    /**
     * Can't create a ResourceUtils.
     */
    private ResourceUtils() {
    }

    /**
     * Returns the declaring {@link java.lang.reflect.Field} of this resource.
     * Shortcut to <code>resource.getClass().getField(resource.toString())</code>.
     * May return null if an error occurs during the process.
     * 
     * @return the declaring field of the resource.
     */
    public static Field getField(Resource resource) {
        try {
            return resource.getClass().getField(resource.toString());
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns all the
     * {@link io.github.martinheywang.products.api.model.resource.annotation.Tag}
     * defined or not in the declaration of the resource.
     * 
     * @param resource the resource to extract the tag from
     * @return the tags, if none is found, an empty array
     */
    public static Tag[] getTags(Resource resource) {
        Tag[] tags = getField(resource).getAnnotationsByType(Tag.class);
        return tags;
    }

    /**
     * Returns the groups associated to the given resource. Returns an empty array
     * if none is found.
     * 
     * @param resource the resource to extract the group from
     * @return the groups associated to the resource.
     */
    public static AnnotationPackGroup[] getGroups(Resource resource) {
        AnnotationPackGroup[] groups = getField(resource).getAnnotationsByType(AnnotationPackGroup.class);
        return groups;
    }

    /**
     * Checks if, for the given resource, a tag exists matching the given clazz,
     * property, and value.
     * 
     * @param resource the resource to check
     * @param clazz    the class value that may match
     * @param property the property that may match
     * @param value    the value that may match
     * @return if a tag exists, on the given resource, matching the class, the
     *         property and the value.
     */
    public static boolean hasProperty(Resource resource, String clazz, String property, String value) {
        for (Tag tag : getTags(resource)) {
            if (tag.property().equals(property) && tag.value().equals(value)
                    && (tag.clazz().equals(clazz) || tag.clazz().equals("all"))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the value for the given property, if it exists, as a String.
     * 
     * @param resource the resource to get the property from
     * @param property the property to search for
     * @return the value of the property, if it exists, else an empty String.
     */
    public static String getPropertyValue(Resource resource, String property) {
        for (Tag tag : getTags(resource)) {
            if (tag.property().equals(property)) {
                return tag.value();
            }
        }
        return "";
    }

    /**
     * Returns the first group associated to the given target. If none is found,
     * return <code>null</code>
     * 
     * @param resource the resource to get the group from
     * @param target   the target class of the group
     * @return the group associated to the resource and the target,
     *         <code>null</code> if none is found.
     */
    public static AnnotationPackGroup getGroup(Resource resource, String target) {
        for (AnnotationPackGroup group : getGroups(resource)) {
            if (group.target().equals(target)){
                return group;
            }
        }
        return null;
    }

    /**
     * Returns true if a group containing the given target can be found on the given
     * resource declaration.
     * 
     * @param resource the resource to get the group from
     * @param target   the target that the group should have
     * @return true if a group was found, else false
     */
    public static boolean hasGroup(Resource resource, String target) {
        return getGroup(resource, target) == null ? false : true;
    }

}
