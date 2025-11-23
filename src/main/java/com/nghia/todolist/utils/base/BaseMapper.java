package com.nghia.todolist.utils.base;

public abstract  class BaseMapper<E,D> {
    public abstract D toDto(E entity);
    public abstract E toEntity(D dto);
}
