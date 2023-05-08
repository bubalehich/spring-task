package ru.clevertec.ecl.dao.impl;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.dao.AbstractDAO;
import ru.clevertec.ecl.dao.DAO;
import ru.clevertec.ecl.entity.base.BaseEntity;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericDAO<T extends BaseEntity> extends AbstractDAO<T> implements DAO<T> {
}
