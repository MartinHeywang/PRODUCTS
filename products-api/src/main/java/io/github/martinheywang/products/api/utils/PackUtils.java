/*
   Copyright 2021 Martin Heywang

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

import java.math.BigInteger;

import io.github.martinheywang.products.api.model.Pack;
import io.github.martinheywang.products.api.model.Recipe;
import io.github.martinheywang.products.api.model.resource.ResourceManager;
import io.github.martinheywang.products.api.model.resource.annotation.AnnotationPack;
import io.github.martinheywang.products.api.model.resource.annotation.AnnotationPackGroup;

/**
 * Utility class that allows treatment about packs.
 * 
 * @author Martin Heywang
 */
public final class PackUtils {

    private PackUtils() {
    }

    /**
     * Transform a
     * {@link io.github.martinheywang.products.api.model.resource.annotation.AnnotationPack}
     * in a regular pack.
     * 
     * @param pack the remote pack to transform.
     * @return the pack from the remote
     */
    public static Pack toPack(AnnotationPack pack) {
        return new Pack(ResourceManager.valueOf(pack.clazz(), pack.field()), new BigInteger(pack.quantity()));
    }

    /**
     * Transforms an array of remote packs into an array of regular packs.
     * 
     * @param packs the remote packs
     * @return the regular packs
     */
    public static Pack[] toPack(AnnotationPack... packs) {
        final Pack[] realPacks = new Pack[packs.length];

        for (int i = 0; i < packs.length; i++)
            realPacks[i] = toPack(packs[i]);

        return realPacks;
    }

    /**
     * Transforms the
     * {@link io.github.martinheywang.products.api.model.resource.annotation.AnnotationPack}
     * array to a recipe.
     * 
     * @param group the group of annotation packs
     * @return a new recipe.
     */
    public static Recipe toRecipe(AnnotationPackGroup group) {
        Pack[] packs = toPack(group.value());
        return new Recipe(packs);
    }
}
