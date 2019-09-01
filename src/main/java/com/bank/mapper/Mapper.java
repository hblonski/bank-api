package com.bank.mapper;

import javax.validation.constraints.NotNull;

@FunctionalInterface
public interface Mapper<F, T> {
    T map(@NotNull F from);
}
