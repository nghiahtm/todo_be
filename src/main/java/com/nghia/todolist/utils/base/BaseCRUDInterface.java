package com.nghia.todolist.utils.base;

import com.nghia.todolist.dto.BaseResponseDto;

public interface BaseCRUDInterface<R,T> {
    T create(R request);
    T read(Long id);
    T update(R request);
    T remove(Long id);
}
