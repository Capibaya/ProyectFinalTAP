package com.hotel.dao;


import java.util.List;
/**
 * Interfaz generica DAO con operaciones CRUD.
 * @param <T> Tipo de entidad
 */
public interface GenericDAO<T> {
    void guardar(T entidad);
    T buscarPorId(int id);
    List<T> obtenerTodos();
    void actualizar(T entidad);
    void eliminar(int id);
}
