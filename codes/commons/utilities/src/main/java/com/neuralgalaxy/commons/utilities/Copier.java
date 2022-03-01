package com.neuralgalaxy.commons.utilities;

import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.Type;
import java.util.function.Supplier;

/**
 * 类拷贝器
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220226
 */
public class Copier<M, E> {

    private BeanCopier entityCopier;
    private Supplier<E> newEntity;

    private BeanCopier modelCopier;
    private Supplier<M> newModel;

    private Copier(Supplier<M> newModel, Supplier<E> newEntity) {
        Class<M> modelClass = (Class<M>) newEntity.get().getClass();
        Class<E> entityClass = (Class<E>) newModel.get().getClass();
        entityCopier = BeanCopier.create(entityClass, modelClass, false);
        modelCopier = BeanCopier.create(modelClass, entityClass, false);
        this.newEntity = newEntity;
        this.newModel = newModel;
    }

    public static <M, E> Copier<M, E> create(Supplier<M> modelMaker, Supplier<E> entityMaker) {
        return new Copier<M, E>(modelMaker, entityMaker);
    }

    public E toEntity(M model) {
        E entity = this.newEntity.get();
        entityCopier.copy(model, entity, null);
        return entity;
    }

    public M toModel(E entity) {
        M model = this.newModel.get();
        modelCopier.copy(entity, model, null);
        return model;
    }
}
