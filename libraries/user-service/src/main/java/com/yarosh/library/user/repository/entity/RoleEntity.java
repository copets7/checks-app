package com.yarosh.library.user.repository.entity;

import com.yarosh.library.repository.api.entity.BaseEntity;

public class RoleEntity implements BaseEntity {

    private long id;
    private String name;

    public RoleEntity() {
    }

    public RoleEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
