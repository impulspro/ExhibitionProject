package com.exhibit.dao;

import com.exhibit.dao.mappers.Mapper;
import com.exhibit.dao.mappers.MapperFactory;
import com.exhibit.exeptions.DaoException;
import com.exhibit.model.Hall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.exhibit.util.ExhibitionConstants.FIND_ALL_HALLS_SQL;

public class HallDao {
    public List<Hall> findAll() {
        List<Hall> halls = new CopyOnWriteArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement prepSt = conn.prepareStatement(FIND_ALL_HALLS_SQL)) {
            ResultSet rs = prepSt.executeQuery();
            Mapper<Hall> mapperHall = MapperFactory.getInstance().getHallMapper();
            while (rs.next()) {
                halls.add(mapperHall.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot find all halls", e);
        }
        return halls;
    }
}
