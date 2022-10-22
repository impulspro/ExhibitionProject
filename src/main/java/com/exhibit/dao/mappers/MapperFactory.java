package com.exhibit.dao.mappers;

public final class MapperFactory {
    private static final MapperFactory instance = new MapperFactory();

    public static MapperFactory getInstance() {
        return instance;
    }

    public UserMapper getUserMapper() {
        return new UserMapper();
    }

    public ExhibitionMapper getExhibitionMapper() {
        return new ExhibitionMapper();
    }

    public HallMapper getHallMapper() {
        return new HallMapper();
    }

    public TicketMapper getTicketMapper() {
        return new TicketMapper();
    }
}
