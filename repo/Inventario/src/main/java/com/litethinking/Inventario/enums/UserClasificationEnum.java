package com.litethinking.Inventario.enums;

public enum UserClasificationEnum {
    ADMINISTRATOR(1L),
    EXTERNAL_USER(2L);
    private Long id;

    UserClasificationEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
