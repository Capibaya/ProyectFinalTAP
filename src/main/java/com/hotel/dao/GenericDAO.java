package com.hotel.dao;

import java.util.List;

/**
 * Interfaz genérica que define el patrón DAO (Data Access Object) con las operaciones CRUD básicas.
 * <p>
 * Patrón de diseño: <b>DAO (Data Access Object)</b> — abstrae el acceso a la capa de persistencia
 * permitiendo intercambiar la implementación de base de datos sin afectar la lógica de negocio.
 * </p>
 *
 * @param <T> Tipo de la entidad que administra este DAO.
 */
public interface GenericDAO<T> {

    /**
     * Persiste una nueva entidad en la base de datos.
     *
     * @param entidad La entidad a guardar.
     */
    void guardar(T entidad);

    /**
     * Busca y retorna una entidad por su identificador único.
     *
     * @param id Identificador único de la entidad.
     * @return La entidad encontrada, o {@code null} si no existe.
     */
    T buscarPorId(int id);

    /**
     * Obtiene la lista completa de entidades almacenadas en la base de datos.
     *
     * @return Lista de todas las entidades; lista vacía si no hay registros.
     */
    List<T> obtenerTodos();

    /**
     * Actualiza los datos de una entidad existente en la base de datos.
     *
     * @param entidad La entidad con los datos actualizados.
     */
    void actualizar(T entidad);

    /**
     * Elimina una entidad de la base de datos por su identificador único.
     *
     * @param id Identificador único de la entidad a eliminar.
     */
    void eliminar(int id);
}
