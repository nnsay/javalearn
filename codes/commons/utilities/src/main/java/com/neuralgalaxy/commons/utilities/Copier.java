package com.neuralgalaxy.commons.utilities;

import org.springframework.cglib.beans.BeanCopier;

import java.util.function.Supplier;

/**
 * 类拷贝器
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220226
 */
public class Copier<Model, Entity> {

    private final BeanCopier entityCopier;
    private Supplier<Entity> newEntity;

    private final BeanCopier modelCopier;
    private Supplier<Model> newModel;

    public Copier(Class<Model> module, Supplier<Model> newModel, Class<Entity> entity, Supplier<Entity> newEntity) {
        this.entityCopier = BeanCopier.create(module, entity, false);
        this.modelCopier = BeanCopier.create(entity, module, false);
        this.newEntity = newEntity;
        this.newModel = newModel;
    }

    public Entity toEntity(Model model) {
        Entity entity = this.newEntity.get();
        entityCopier.copy(model, entity, null);
        return entity;
    }

    public Model toModel(Entity entity) {
        Model model = this.newModel.get();
        modelCopier.copy(entity, model, null);
        return model;
    }
}
