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
