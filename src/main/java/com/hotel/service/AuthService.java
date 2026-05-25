package com.hotel.service;

import com.hotel.dao.UsuarioDAO;
import com.hotel.model.UsuarioSistema;
import com.hotel.util.SHA256Util;
/**
 * Servicio de autenticacion.
 * Usa SHA256Util para validar credenciales contra la BD.
 */
public class AuthService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    /**
     * Valida usuario y contraseña contra la base de datos.
     * @param usuario Nombre de usuario ingresado
     * @param password Contraseña en texto plano
     * @return UsuarioSistema si las credenciales son correctas, null si no
     */
    public UsuarioSistema login(String usuario, String password) {
        UsuarioSistema u = usuarioDAO.buscarPorUsuario(usuario);
        if (u == null) return null;
        String hashIngresado = SHA256Util.encriptar(password);
        return hashIngresado.equals(u.getPasswordHash()) ? u : null;
    }
    /**
     * Verifica si el usuario tiene rol de administrador.
     * @param usuario UsuarioSistema autenticado
     * @return true si es administrador
     */
    public boolean esAdministrador(UsuarioSistema usuario) {
        return usuario.getRol().getNombreRol().equalsIgnoreCase("administrador");
    }
    /**
     * Verifica si el usuario tiene rol de recepcionista.
     * @param usuario UsuarioSistema autenticado
     * @return true si es recepcionista
     */
    public boolean esRecepcionista(UsuarioSistema usuario) {
        return usuario.getRol().getNombreRol().equalsIgnoreCase("recepcionista");
    }
}